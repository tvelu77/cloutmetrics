package fr.uge.cloutmetrics.repository;

import fr.uge.cloutmetrics.model.DetailedGit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;

/**
 * Defines SQL queries for the join between git, metric and contributors.
 */
public class DetailedGitRepository implements CloutMetricsRepository<DetailedGit> {

  private static final String SELECT_GITS_QUERY = "SELECT "
          + "git.git_id, git_name, git_date, languages_ratio, number_commits "
          + "FROM git "
          + "INNER JOIN metric ON metric.git_id = git.git_id ";
  private static final String SELECT_GIT_QUERY = "SELECT "
          + "git.git_id, git_name, git_date, languages_ratio, number_commits "
          + "FROM git "
          + "INNER JOIN metric ON metric.git_id = git.git_id "
          + "WHERE git.git_id=:gitId";
  private final Jdbi jdbi;

  public DetailedGitRepository(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.jdbi = jdbi;
  }

  @Override
  public List<DetailedGit> findAll() {
    var gits = List.<DetailedGit>of();
    try (var handle = jdbi.open()) {
      gits = handle.createQuery(SELECT_GITS_QUERY)
              .mapTo(DetailedGit.class)
              .list();
    }
    return gits;
  }

  @Override
  public Optional<DetailedGit> findById(Long id) {
    Objects.requireNonNull(id);
    var git = Optional.<DetailedGit>empty();
    try (var handle = jdbi.open()) {
      git = handle.createQuery(SELECT_GIT_QUERY)
              .bind("gitId", id)
              .mapTo(DetailedGit.class).findFirst();
    }
    return git;
  }

  @Override
  public DetailedGit save(DetailedGit element) {
    throw new UnsupportedOperationException("saving a SQL join is not allowed");
  }

  @Override
  public List<DetailedGit> saveAll(List<DetailedGit> elements) {
    throw new UnsupportedOperationException("saving SQL joins is not allowed");
  }
}
