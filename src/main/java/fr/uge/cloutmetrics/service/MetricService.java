package fr.uge.cloutmetrics.service;

import static fr.uge.cloutmetrics.utils.Utils.EXTENSION;
import static fr.uge.cloutmetrics.utils.Utils.LOCAL_REPOSITORY_PATH;

import fr.uge.cloutmetrics.model.Git;
import fr.uge.cloutmetrics.model.Metric;
import fr.uge.cloutmetrics.repository.MetricRepository;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.jdbi.v3.core.Jdbi;

/**
 * This class is used to modify a given metric with the correct information.
 */
public class MetricService {
  private final MetricRepository metricRepository;

  /**
   * Metric service constructor.
   *
   * @param jdbi Jdbi plugin.
   */
  public MetricService(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.metricRepository = new MetricRepository(jdbi);
  }

  /**
   * Create a git with all the correct information and download the repository.
   *
   * @param git The given git to be used to create the metric.
   * @return A new metric.
   */
  public Metric newMetric(Git git) {
    Objects.requireNonNull(git);
    var metrics = metricComputer(git.name());
    return metricRepository.save(new Metric(0L,
            0L,
            metrics.get("languagesRatio"),
            metrics.get("numberCommits")));
  }

  // Improvement needed !
  private Map<String, String> metricComputer(String gitName) {
    var hashMap = new HashMap<String, String>();
    try {
      hashMap.put("languagesRatio", languageRatio(gitName));
      hashMap.put("numberCommits", "" + countCommits(gitName));
    } catch (IOException | GitAPIException e) {
      hashMap.put("languagesRatio", "ERROR WHILE COMPUTING");
      hashMap.put("numberCommits", "ERROR WHILE COMPUTING");
    }
    return hashMap;
  }

  private Repository openRepository(String gitName) throws IOException {
    var builder = new FileRepositoryBuilder();
    return builder.findGitDir(new File(LOCAL_REPOSITORY_PATH + gitName))
      .readEnvironment()
      .findGitDir()
      .build();
  }

  private String languageRatio(String gitName) throws IOException {
    var repository = openRepository(gitName);
    var head = repository.exactRef("HEAD");
    var walk = new RevWalk(repository);
    var commit = walk.parseCommit(head.getObjectId());
    var tree = commit.getTree();
    var treeWalk = new TreeWalk(repository);
    treeWalk.addTree(tree);
    return countExtension(treeWalk).toString();
  }

  private String countExtension(TreeWalk treeWalk) throws IOException {
    var map = new HashMap<String, Integer>();
    treeWalk.setRecursive(true);
    while (treeWalk.next()) {
      var path = treeWalk.getPathString();
      var fileName = path.substring(path.lastIndexOf("/") + 1);
      var extension = fileName.substring(fileName.lastIndexOf(".") + 1);
      String language;
      if ((language = EXTENSION.get(extension)) != null) {
        map.merge(language, 1, Integer::sum);
      }
    }
    return averageOfMap(map);
  }

  private String averageOfMap(Map<String, Integer> map) {
    var totalFiles = map.values().stream().mapToDouble(Double::valueOf).sum();
    var stringJoiner = new StringJoiner(" - ", "[ ", " ]");
    for (var key : map.keySet()) {
      var percentage = (map.get(key) / totalFiles) * 100;
      stringJoiner.add(key + "(" + Math.round(percentage * 10.0) / 10.0 + "%)");
    }
    return stringJoiner.toString();
  }

  private int countCommits(String gitName) throws IOException, GitAPIException {
    var repository = openRepository(gitName);
    var count = 0;
    try (var git = new org.eclipse.jgit.api.Git(repository)) {
      var commits = git.log().all().call();
      for (var ignored : commits) {
        count++;
      }
    }
    return count;
  }
}


