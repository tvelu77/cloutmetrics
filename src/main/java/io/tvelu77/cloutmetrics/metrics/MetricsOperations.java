package io.tvelu77.cloutmetrics.metrics;

import io.tvelu77.cloutmetrics.Utils;
import io.tvelu77.cloutmetrics.git.Git;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * A class to define all the operations for a git.
 */
public class MetricsOperations {
  
  /**
   * Computes :<br>
   * - The number of commits done.
   *
   * @param git Git, the git repository.
   * @return Map of String, String, for a key (metric name) and value.
   */
  public static Map<String, String> metricComputer(Git git) {
    var hashMap = new HashMap<String, String>();
    try {
      hashMap.put("numberCommits", "" + countCommits(git));
    } catch (IOException | GitAPIException e) {
      hashMap.put("numberCommits", "ERROR WHILE COMPUTING");
    }
    return hashMap;
  }
  
  /**
   * Counts all the commits done in a repository.
   *
   * @param git Git, the git we want to
   * @return Long, the number of commits done in a git.
   * @throws IOException If the local file couldn't be opened.
   * @throws GitAPIException If the JGit API had a problem (Bad read for example).
   */
  private static Long countCommits(Git git) throws IOException, GitAPIException {
    var repository = openRepository(git.getName());
    var count = 0L;
    try (var gitRepository = new org.eclipse.jgit.api.Git(repository)) {
      var commits = gitRepository.log().call();
      count = StreamSupport.stream(commits.spliterator(), false).count();
    }
    return count;
  }
  
  private static Repository openRepository(String gitName) throws IOException {
    var builder = new FileRepositoryBuilder();
    return builder.findGitDir(
        new File(Utils.LOCAL_REPOSITORY_PATH + gitName)
        ).readEnvironment().findGitDir().build();
  }

}
