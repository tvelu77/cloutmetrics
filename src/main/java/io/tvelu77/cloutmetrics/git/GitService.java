package io.tvelu77.cloutmetrics.git;

import io.tvelu77.cloutmetrics.ApplicationService;
import io.tvelu77.cloutmetrics.Utils;
import io.tvelu77.cloutmetrics.metrics.Metrics;
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
    Objects.requireNonNull(git);
    var toBeSaved = new Git();
    toBeSaved.setName(git.getName());
    toBeSaved.setUrl(git.getUrl());
    var metrics = new Metrics();
    metrics.setTotalCommits(0L);
    toBeSaved.setMetrics(metrics);
    toBeSaved.setStatus(GitStatus.IN_PROGRESS);
    gitRepository.save(toBeSaved);
    var executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try {
        var operations = new MetricsOperations(git,
                Path.of(utils.getLocalRepositoryPath() + git.getName()));
        operations.openRepository();
        metrics.setOwner(operations.getGitOwner());
        gitRepository.save(toBeSaved);
        metrics.setTotalCommits(operations.countCommits());
        gitRepository.save(toBeSaved);
        metrics.setTotalTags(operations.countTags());
        gitRepository.save(toBeSaved);
        metrics.setTotalBranches(operations.countBranches());
        gitRepository.save(toBeSaved);
        metrics.setLanguageAndFileCount(operations.countFilesForEachExtension());
        gitRepository.save(toBeSaved);
        metrics.setLanguageRatio(operations.languageRatio());
        toBeSaved.setStatus(GitStatus.FINISHED);
        gitRepository.save(toBeSaved);
        operations.closeRepository();
      } catch (GitAPIException | IOException e) {
        toBeSaved.setStatus(GitStatus.ERROR);
        gitRepository.save(toBeSaved);
      }
    });
    return true;
  }

  @Override
  public boolean delete(Long id) {
    Objects.requireNonNull(id);
    var git = gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    try {
      if (git.getStatus() == GitStatus.FINISHED) {
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
    var git = gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    try {
      if (git.getStatus() == GitStatus.FINISHED) {
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

  @Override
  public List<Git> findAll() {
    return gitRepository.findAll();
  }

  @Override
  public Git findById(Long id) {
    return gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }
  
  private void deleteDirectory(Path directory) throws IOException {
    try (var paths = Files.walk(directory)) {
      paths.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
  }
  
  private void renameDirectory(Path directory, Path newDirectory) throws IOException {
    Files.move(directory,
        newDirectory,
        StandardCopyOption.REPLACE_EXISTING,
        StandardCopyOption.ATOMIC_MOVE);   
  }
  
}
