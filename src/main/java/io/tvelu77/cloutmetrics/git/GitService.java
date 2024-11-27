package io.tvelu77.cloutmetrics.git;

import io.tvelu77.cloutmetrics.ApplicationService;
import io.tvelu77.cloutmetrics.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
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

  @Autowired
  private Utils utils;

  @Override
  public boolean add(Git git) {
    // TODO : DTO should be used here !
    Objects.requireNonNull(git);
    gitRepository.save(git);
    // var executor = Executors.newSingleThreadExecutor();
    // executor.submit(() -> {
    // try {
    // var operations = new MetricsOperations(git,
    // Path.of(utils.getLocalRepositoryPath() + git.getName()));
    // operations.openRepository();
    // metrics.setOwner(operations.getGitOwner());
    // gitRepository.save(toBeSaved);
    // metrics.setTotalCommits(operations.countCommits());
    // gitRepository.save(toBeSaved);
    // metrics.setTotalTags(operations.countTags());
    // gitRepository.save(toBeSaved);
    // metrics.setTotalBranches(operations.countBranches());
    // gitRepository.save(toBeSaved);
    // metrics.setLanguageAndFileCount(operations.countFilesForEachExtension());
    // gitRepository.save(toBeSaved);
    // metrics.setLanguageRatio(operations.languageRatio());
    // toBeSaved.setStatus(GitStatus.FINISHED);
    // gitRepository.save(toBeSaved);
    // operations.closeRepository();
    // } catch (GitAPIException | IOException e) {
    // toBeSaved.setStatus(GitStatus.ERROR);
    // gitRepository.save(toBeSaved);
    // }
    // });
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
      System.out.println("HELLO");
      return false;
    } catch (IOException e) {
      System.out.println("HELLO BUG");
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

}
