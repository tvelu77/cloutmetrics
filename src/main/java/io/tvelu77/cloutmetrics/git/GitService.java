package io.tvelu77.cloutmetrics.git;

import io.tvelu77.cloutmetrics.ApplicationService;
import io.tvelu77.cloutmetrics.metrics.Metrics;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Git.
 */
@Service
public class GitService implements ApplicationService<Git> {
  
  @Autowired
  private GitRepository gitRepository;

  @Override
  public boolean add(Git git) {
    Objects.requireNonNull(git);
    var toBeSaved = new Git();
    toBeSaved.setName(git.getName());
    toBeSaved.setUrl(git.getUrl());
    toBeSaved.setDate(LocalDateTime.now());
    var metrics = new Metrics();
    metrics.setTotalCommits(100L);
    toBeSaved.setMetrics(metrics);
    gitRepository.save(toBeSaved);
    return true;
  }

  @Override
  public boolean delete(Long id) {
    Objects.requireNonNull(id);
    var git = gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    gitRepository.delete(git);
    return true;
  }

  @Override
  public boolean update(Git newGit, Long id) {
    Objects.requireNonNull(newGit);
    Objects.requireNonNull(id);
    var git = gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    git.setName(newGit.getName());
    git.setUrl(newGit.getUrl());
    gitRepository.save(git);
    return true;
  }

  @Override
  public List<Git> findAll() {
    return gitRepository.findAll();
  }

  @Override
  public Git findById(Long id) {
    return gitRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

}
