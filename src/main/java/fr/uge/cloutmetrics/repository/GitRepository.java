package fr.uge.cloutmetrics.repository;

import fr.uge.cloutmetrics.model.Git;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class represents all the SQL queries used for Git.
 */
@Repository
@Transactional
public class GitRepository implements CloutMetricsRepository<Git> {

  private static final String INSERT_GIT_QUERY = "INSERT INTO git"
          + "(git_name, git_path, git_date) "
          + "VALUES(:name, :path, :addedDate)";
  private static final String SELECT_GITS_QUERY = "SELECT * FROM git";
  private static final String SELECT_GIT_QUERY = "SELECT * FROM git WHERE git_id=:id";
  private final Jdbi jdbi;

  public GitRepository(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.jdbi = jdbi;
  }

  @Override
  public List<Git> findAll() {
    var gits = List.<Git>of();
    try (var handle = jdbi.open()) {
      gits = handle.createQuery(SELECT_GITS_QUERY)
              .mapTo(Git.class)
              .list();
    }
    return gits;
  }

  @Override
  public Optional<Git> findById(Long id) {
    Objects.requireNonNull(id);
    var git = Optional.<Git>empty();
    try (var handle = jdbi.open()) {
      git = handle.createQuery(SELECT_GIT_QUERY)
              .bind("id", id)
              .mapTo(Git.class).findFirst();
    }
    return git;
  }

  @Override
  @GetGeneratedKeys("git_id")
  public Git save(Git git) {
    Objects.requireNonNull(git);
    jdbi.useHandle(handle ->
            handle.createUpdate(INSERT_GIT_QUERY)
                    .bind("name", git.name())
                    .bind("path", git.path())
                    .bind("addedDate", git.addedDate())
                    .execute());
    return git;
  }

  @Override
  @GetGeneratedKeys("git_id")
  public List<Git> saveAll(List<Git> gits) {
    jdbi.useHandle(handle -> {
      PreparedBatch preparedBatch =
              handle.prepareBatch(INSERT_GIT_QUERY);
      gits.forEach(git -> preparedBatch
                      .bind("git_name", git.name())
                      .bind("git_path", git.path())
                      .bind("git_date", git.addedDate()));
      preparedBatch.execute();
    });
    return gits;
  }
}
