package fr.uge.cloutmetrics.service;

import static fr.uge.cloutmetrics.utils.Utils.LOCAL_REPOSITORY_PATH;

import fr.uge.cloutmetrics.model.Git;
import fr.uge.cloutmetrics.repository.GitRepository;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.jdbi.v3.core.Jdbi;

/**
 * This class is used to modify a given git with the correct information.
 */
public class GitService {

  private final GitRepository gitRepository;

  /**
   * Git service constructor.
   *
   * @param jdbi Jdbi plugin.
   */
  public GitService(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.gitRepository = new GitRepository(jdbi);
  }

  /**
   * Create a git with all the correct information and download the repository.
   *
   * @param git The given git to modify.
   * @return A new git.
   * @throws GitAPIException If the jgit plugin had a problem.
   */
  public Git newGit(Git git) throws GitAPIException {
    Objects.requireNonNull(git);
    addFromRemote(git);
    return gitRepository.save(new Git(0L,
            getName(git.path()),
            git.path(),
            LocalDateTime.now()));
  }

  /**
   * Return all saved gits.
   *
   * @return List of gits.
   */
  public List<Git> findAll() {
    return gitRepository.findAll();
  }

  private void addFromRemote(Git git) throws GitAPIException {
    org.eclipse.jgit.api.Git.cloneRepository()
            .setURI(git.path())
            .setDirectory(Path.of(LOCAL_REPOSITORY_PATH + git.name()).toFile())
            .call();
  }

  private String getName(String gitPath) {
    return gitPath.substring(gitPath.lastIndexOf("/") + 1);
  }

}


