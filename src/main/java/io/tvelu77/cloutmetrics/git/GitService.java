package io.tvelu77.cloutmetrics.git;

import io.tvelu77.cloutmetrics.ApplicationService;
import io.tvelu77.cloutmetrics.Utils;
import io.tvelu77.cloutmetrics.metrics.MetricsOperations;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Executors;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Git.
 */
@Service
public class GitService implements ApplicationService<Git> {

  @Autowired
  private GitRepository gitRepository;

  @Autowired
  private Utils utils;

  @Override
  public boolean add(Git git) {
    // TODO : DTO should be used here !
    Objects.requireNonNull(git);
    gitRepository.save(git);
    return true;
  }

  @Override
  public boolean delete(Long id) {
    Objects.requireNonNull(id);
    var git = gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    try {
      if (git.getStatus() != GitStatus.IN_PROGRESS) {
        deleteDirectory(Path.of(utils.getLocalRepositoryPath() + git.getName()));
        gitRepository.delete(git);
        return true;
      }
      return false;
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public boolean update(Git newGit, Long id) {
    Objects.requireNonNull(newGit);
    Objects.requireNonNull(id);
    var git = gitRepository.findById(id).orElseThrow(NoSuchElementException::new);
    try {
      if (git.getStatus() != GitStatus.IN_PROGRESS) {
        renameDirectory(Path.of(utils.getLocalRepositoryPath() + git.getName()),
            Path.of(utils.getLocalRepositoryPath() + newGit.getName()));
        git.setName(newGit.getName());
        gitRepository.save(git);
        return true;
      }
      return false;
    } catch (IOException e) {
      return false;
    }
  }
  
  /**
   * Updates the git associated with the given id
   * by computing its metrics.
   *
   * @param id {@link Long}, the git's id.
   * @return True if the update has been successful or 
   */
  public boolean update(Long id) {
    Objects.requireNonNull(id, "Id should not be null !");
    var git = gitRepository.findById(id).orElseThrow(NoSuchElementException::new);
    if (git.getStatus() != GitStatus.IN_PROGRESS) {
      git.setStatus(GitStatus.IN_PROGRESS);
      gitRepository.save(git);
      var executor = Executors.newSingleThreadExecutor();
      executor.submit(() -> compute(git));
      return true;
    }
    return false;
  }

  @Override
  public List<Git> findAll() {
    return gitRepository.findAll();
  }

  @Override
  public Git findById(Long id) {
    return gitRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  private void deleteDirectory(Path directory) throws IOException {
    try (var paths = Files.walk(directory)) {
      paths.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    } catch (IOException e) {
      if (Files.exists(directory)) {
        throw e;
      }
    }
  }

  private void renameDirectory(Path directory, Path newDirectory) throws IOException {
    try {
      Files.move(directory,
          newDirectory,
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.ATOMIC_MOVE);
    } catch (IOException e) {
      if (Files.exists(directory)) {
        throw e;
      }
    }
  }
  
  private void compute(Git git) {
    try {
      var operations = new MetricsOperations(git,
          Path.of(utils.getLocalRepositoryPath() + git.getName()));
      var metrics = git.getMetrics();
      operations.openRepository();
      metrics.setOwner(operations.getGitOwner());
      metrics.setTotalCommits(operations.countCommits());
      metrics.setTotalTags(operations.countTags());
      metrics.setTotalBranches(operations.countBranches());
      metrics.setLanguageAndFileCount(operations.countFilesForEachExtension());
      metrics.setLanguageRatio(operations.languageRatio());
      git.setStatus(GitStatus.FINISHED);
      operations.closeRepository();
    } catch (GitAPIException | IOException e) {
      git.setStatus(GitStatus.ERROR);
    } finally {
      gitRepository.save(git);
    }
  }
}
