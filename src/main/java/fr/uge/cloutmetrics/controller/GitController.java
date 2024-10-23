package fr.uge.cloutmetrics.controller;

import fr.uge.cloutmetrics.model.DetailedGit;
import fr.uge.cloutmetrics.model.Git;
import fr.uge.cloutmetrics.repository.DetailedGitRepository;
import fr.uge.cloutmetrics.service.GitService;
import fr.uge.cloutmetrics.service.MetricService;
import java.util.List;
import java.util.Objects;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jdbi.v3.core.Jdbi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * It is the controller of the model Git
 * It maps every SQL Queries to a path.
 */
@RestController
public class GitController {

  private final GitService gitService;
  private final MetricService metricService;
  private final DetailedGitRepository detailedGitRepository;

  /**
   * GitController's constructor.
   *
   * @param jdbi Use JDBI to create queries
   */
  public GitController(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.gitService = new GitService(jdbi);
    this.metricService = new MetricService(jdbi);
    this.detailedGitRepository = new DetailedGitRepository(jdbi);
  }

  /**
   * Create a new Git in the database.
   *
   * @param git Git object.
   * @return The added git.
   */
  @PostMapping("/gits")
  public Git createGit(@RequestBody Git git) {
    Objects.requireNonNull(git);
    try {
      var newGit = gitService.newGit(git);
      metricService.newMetric(git);
      return newGit;
    } catch (GitAPIException e) {
      throw new AssertionError(e);
    }
  }

  /**
   * List all existing gits.
   *
   * @return A list of gits.
   */
  @GetMapping("/gits")
  public List<Git> getGits() {
    return gitService.findAll();
  }

  /**
   * Return the corresponding git according to the id.
   *
   * @param id Git's id.
   * @return The corresponding Git.
   */
  @GetMapping("/gits/{id}")
  public DetailedGit getGit(@PathVariable Long id) {
    Objects.requireNonNull(id);
    return detailedGitRepository.findById(id).orElseThrow();
  }
}
