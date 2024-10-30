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
import java.time.LocalDateTime;
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
    toBeSaved.setDate(LocalDateTime.now());
    var metrics = new Metrics();
    metrics.setTotalCommits(0L);
    toBeSaved.setMetrics(metrics);
    gitRepository.save(toBeSaved);
    var executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try {
        var operations = new MetricsOperations(git,
            Path.of(utils.getLocalRepositoryPath() + git.getName()));
        operations.openRepository();
        metrics.setTotalCommits(operations.countCommits());
        toBeSaved.setMetrics(metrics);
        gitRepository.save(toBeSaved);
        metrics.setLanguagesRatio(operations.languageRatio());
        gitRepository.save(toBeSaved);
        operations.closeRepository();
      } catch (GitAPIException | IOException e) {
        System.out.println("error :" + e);
        gitRepository.delete(git);
      }
    });
    return true;
  }

  @Override
  public boolean delete(Long id) {
    Objects.requireNonNull(id);
    var git = gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    try {
      deleteDirectory(Path.of(utils.getLocalRepositoryPath() + git.getName()));
      gitRepository.delete(git);
      return true;
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
      renameDirectory(Path.of(utils.getLocalRepositoryPath() + git.getName()),
          Path.of(utils.getLocalRepositoryPath() + newGit.getName()));
      git.setName(newGit.getName());
      gitRepository.save(git);
      return true;
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
