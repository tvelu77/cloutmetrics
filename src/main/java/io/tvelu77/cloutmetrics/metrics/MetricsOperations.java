package io.tvelu77.cloutmetrics.metrics;

import io.tvelu77.cloutmetrics.Utils;
import io.tvelu77.cloutmetrics.git.Git;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * A class to define all the operations for a git.
 */
public class MetricsOperations {
  
  private Git git;
  
  private Path localPath;
  
  private org.eclipse.jgit.api.Git jgit;
  
  public MetricsOperations(Git git, Path localPath) {
    this.git = Objects.requireNonNull(git);
    this.localPath = Objects.requireNonNull(localPath);
  }
  
  /**
   * Clones the git repository.
   *
   * @throws IOException If the directory couldn't be created.
   * @throws InvalidRemoteException If the git URI wasn't correct.
   * @throws TransportException If the SSH/HTTP transport wasn't successful.
   * @throws GitAPIException One of the above (InvalidRemoteException, TransportException).
   */
  public void openRepository() throws IOException,
      InvalidRemoteException,
      TransportException,
      GitAPIException {
    if (Files.exists(localPath)) {
      var builder = new FileRepositoryBuilder();
      jgit =  org.eclipse.jgit.api.Git.wrap(
          builder.setGitDir(localPath.toFile())
          .readEnvironment()
          .findGitDir()
          .build());
    } else {
      jgit = org.eclipse.jgit.api.Git.cloneRepository()
          .setURI(git.getUrl())
          .setDirectory(localPath.toFile())
          .call();
    }
  }
  
  /**
   * Simply closes the repository.
   */
  public void closeRepository() {
    jgit.close();
  }
  
  /**
   * Counts the commits done in a git repository.
   *
   * @return Long, the number of commits done.
   * @throws NoHeadException If the HEAD reference is not present.
   * @throws GitAPIException See NoHeadException.
   */
  public Long countCommits() throws NoHeadException, GitAPIException {
    var count = 0L;
    var commits = jgit.log().call();
    count = StreamSupport.stream(commits.spliterator(), false).count();
    return count;
  }
  
  /**
   * Computes the languages ratio in a git project.
   *
   * @return A String:Float Map, where the string is the language and the float, the percentage.
   * @throws IOException If the file couldn't be opened.
   */
  public Map<String, Double> languageRatio() throws IOException {
    var repository = jgit.getRepository();
    var head = repository.exactRef("HEAD");
    try (var walk = new RevWalk(repository)) {
      var commit = walk.parseCommit(head.getObjectId());
      var tree = commit.getTree();
      var treeWalk = new TreeWalk(repository);
      treeWalk.addTree(tree);
      return countExtension(treeWalk);
    }
  }
  
  private Map<String, Double> countExtension(TreeWalk treeWalk) throws IOException {
    var map = new HashMap<String, Integer>();
    treeWalk.setRecursive(true);
    while (treeWalk.next()) {
      var path = treeWalk.getPathString();
      var fileName = path.substring(path.lastIndexOf("/") + 1);
      var index = fileName.lastIndexOf(".");
      if (index == -1) {
        continue;
      }
      var extension = fileName.substring(index);
      var language = "";
      if ((language = Utils.EXTENSION.get(extension)) != null) {
        map.merge(language, 1, Integer::sum);
      }
    }
    return averageOfMap(map);
  }
  
  private Map<String, Double> averageOfMap(Map<String, Integer> map) {
    var finalMap = new HashMap<String, Double>();
    var totalFiles = map.values().stream().mapToDouble(Double::valueOf).sum();
    for (var key : map.keySet()) {
      var percentage = (map.get(key) / totalFiles) * 100;
      finalMap.putIfAbsent(key, percentage);
    }
    return finalMap;
  }

}
