package io.tvelu77.cloutmetrics.domain;

import io.tvelu77.cloutmetrics.domain.utils.LanguageType;
import io.tvelu77.cloutmetrics.domain.utils.Utils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.StreamSupport;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * A class to define all the operations for a git.<br>
 * For example, this class can compute trivial informations like 
 * the number of tags or branches, 
 * or more complex ones like the language ratio.
 */
public class MetricsOperations {
  
  private final Git git;
  
  private final Path localPath;
  
  private org.eclipse.jgit.api.Git jgit;
  
  /**
   * Constructor of the class.
   *
   * @param git {@link Git}
   *        A model to represent a git repository in the database.
   * @param localPath {@link Path}
   *        The path to the git directory.
   */
  public MetricsOperations(Git git, Path localPath) {
    this.git = Objects.requireNonNull(git, "Git cannot be null");
    this.localPath = Objects.requireNonNull(localPath, "localPath cannot be null");
  }
  
  /**
   * Clones the git repository if it doesn't exist or tries to open 
   * the git if the fikes are present.
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
          builder.setGitDir(Path.of(localPath + "\\.git").toFile())
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
   * Simply closes the repository.<br>
   * See {@link org.eclipse.jgit.api.Git#close()}.
   */
  public void closeRepository() {
    jgit.getRepository().close();
    jgit.close();
  }
  
  /**
   * Counts the commits done in a git repository.
   *
   * @return {@link Long}
   *          The number of commits done.
   * @throws GitAPIException If the git api expected a HEAD reference but doesn't exist.
   */
  public Long countCommits() throws GitAPIException {
    var commits = jgit.log().call();
    return StreamSupport.stream(commits.spliterator(), false).count();
  }
  
  /**
   * Counts the number of 
   * <a href="https://git-scm.com/book/en/v2/Git-Basics-Tagging">tags</a> 
   * in a git repository.
   *
   * @return {@link Long}
   *          The number of tags.
   * @throws GitAPIException If the git api expected a HEAD reference but doesn't exist.
   */
  public Long countTags() throws GitAPIException {
    var tags = jgit.tagList().call();
    return (long) tags.size();
  }
  
  /**
   * Counts the number of 
   * <a href="https://git-scm.com/docs/git-branch">branches</a> 
   * in a git repository.
   *
   * @return Long, the number of tags.
   * @throws GitAPIException If the git couldn't be opened.
   */
  public Long countBranches() throws GitAPIException {
    var branches = jgit.branchList().call();
    return (long) branches.size();
  }
  
  /**
   * Fetches the owner information of the git repository.
   * If the user couldn't be found, the string is "Unknown".
   *
   * @return {@link String}
   *          The name and email of the owner, Unknown otherwise.
   */
  public String getGitOwner() {
    var repository = jgit.getRepository();
    var config = repository.getConfig();
    var name = config.getString("user", null, "name");
    if (name == null) {
      return "Unknown";
    }
    return name;  
  }
  
  /**
   * Counts the number of files for each extension.
   *
   * @return {@link Map}<{@link String}, {@link Long}>
   *          The language name and its number of files in a git.
   * @throws IOException If the file couldn't be opened.
   */
  public Map<String, Long> countFilesForEachExtension() throws IOException {
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
  
  /**
   * Computes the languages ratio in a git project.
   *
   * @return {@link Map}<{@link String}, {@link Double}>
   *          The language and the average in percent.
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
      return countLines(treeWalk);
    }
  }
  
  /**
   * Counts the extension for each file in a git repository.<br>
   * It will count every known extension, so the extension used can be:<br>
   * - From a programming language ;<br>
   * - From a data language ;<br>
   * - From a prose language ;<br>
   * - Or, from a markup language.
   *
   * @param treeWalk {@link TreeWalk} 
   *        Used for iterate through each file.
   * @return {@link Map}<{@link String}, {@link Long}>
   *          Containing a String (the language) key and a Long (number of files) value.
   * @throws IOException If the file couldn't be opened.
   */
  private Map<String, Long> countExtension(TreeWalk treeWalk) throws IOException {
    var map = new HashMap<String, Long>();
    treeWalk.setRecursive(true);
    while (treeWalk.next()) {
      var path = treeWalk.getPathString();
      var fileName = path.substring(path.lastIndexOf("/") + 1);
      var index = fileName.lastIndexOf(".");
      if (index == -1) {
        continue;
      }
      var extension = fileName.substring(index);
      var language = Utils.EXTENSION.get(extension);
      if (language != null) {
        map.merge(language.getName(), 1L, Long::sum);
      }
    }
    return map;
  }
  
  /**
   * Counts the number of non-empty lines in each file in a git repository.<br>
   * However, for the counting, we use files with a programming language extension.
   *
   * @param treeWalk {@link org.eclipse.jgit.treewalk.TreeWalk}
   *        Used for iterate through each file. 
   * @return {@link java.util.Map} 
   *          Contaning a String (the language) key and a Long (number of non-empty lines) value.
   * @throws IOException If the file couldn't be opened.
   */
  private Map<String, Double> countLines(TreeWalk treeWalk) throws IOException {
    var map = new HashMap<String, Long>();
    treeWalk.setRecursive(true);
    while (treeWalk.next()) {
      var path = treeWalk.getPathString();
      var fileName = path.substring(path.lastIndexOf("/") + 1);
      var index = fileName.lastIndexOf(".");
      if (index == -1) {
        continue;
      }
      var extension = fileName.substring(index);
      var language = Utils.EXTENSION.get(extension);
      if (language != null && language.getType() == LanguageType.PROGRAMMING) {
        var lineCount = 0L;
        try (var stream = Files.lines(Path.of(localPath + "/" + path), StandardCharsets.UTF_8)) {
          lineCount = stream.filter(s -> !s.trim().isEmpty()).count();
        }
        map.merge(language.getName(), lineCount, Long::sum);
      }
    }
    return averageOfMap(map);
  }
  
  /**
   * Compute the average of each key in a map.<br>
   * Here, the average is computed with the sum of all values.
   *
   * @param map {@link java.util.Map} 
   *        Containing a String (the language) key and a Long value.
   * @return {@link java.util.Map} 
   *          Contaning a String (the language) key and a Long (its average) value.
   */
  private Map<String, Double> averageOfMap(Map<String, Long> map) {
    var finalMap = new HashMap<String, Double>();
    var totalLines = map.values().stream().mapToDouble(Double::valueOf).sum();
    for (var key : map.keySet()) {
      var percentage = (map.get(key) / totalLines) * 100;
      finalMap.putIfAbsent(key, percentage);
    }
    return finalMap;
  }

}
