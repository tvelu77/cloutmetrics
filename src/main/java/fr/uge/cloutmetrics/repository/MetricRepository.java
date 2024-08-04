package fr.uge.cloutmetrics.repository;

import fr.uge.cloutmetrics.model.Metric;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;

/**
 * This class represents all the SQL queries used for Metric.
 */
public class MetricRepository implements CloutMetricsRepository<Metric> {

  private static final String INSERT_METRIC_QUERY = "INSERT INTO metric"
          + "(languages_ratio, number_commits) "
          + "VALUES(:languagesRatio, :numberCommits)";
  private static final String SELECT_METRICS_QUERY = "SELECT * FROM metric";
  private static final String SELECT_METRIC_QUERY = "SELECT * FROM metric WHERE metric_id=:id";
  private final Jdbi jdbi;

  public MetricRepository(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.jdbi = jdbi;
  }

  @Override
  public List<Metric> findAll() {
    var metrics = List.<Metric>of();
    try (var handle = jdbi.open()) {
      metrics = handle.createQuery(SELECT_METRICS_QUERY)
              .mapTo(Metric.class)
              .list();
    }
    return metrics;
  }

  @Override
  public Optional<Metric> findById(Long id) {
    Objects.requireNonNull(id);
    var metric = Optional.<Metric>empty();
    try (var handle = jdbi.open()) {
      metric = handle.createQuery(SELECT_METRIC_QUERY)
              .bind("id", id)
              .mapTo(Metric.class).findFirst();
    }
    return metric;
  }

  @Override
  @GetGeneratedKeys({"metric_id", "git_id"})
  public Metric save(Metric metric) {
    Objects.requireNonNull(metric);
    jdbi.useHandle(handle ->
            handle.createUpdate(INSERT_METRIC_QUERY)
                    .bind("languagesRatio", metric.languagesRatio())
                    .bind("numberCommits", metric.numberCommits())
                    .execute());
    return metric;
  }

  @Override
  @GetGeneratedKeys("metric_id")
  public List<Metric> saveAll(List<Metric> metrics) {
    Objects.requireNonNull(metrics);
    jdbi.useHandle(handle -> {
      PreparedBatch preparedBatch =
              handle.prepareBatch(INSERT_METRIC_QUERY);
      metrics.forEach(metric -> preparedBatch
                      .bind("git_id", metric.gitId())
                      .bind("languagesRatio", metric.languagesRatio())
                      .bind("numberCommits", metric.numberCommits()));
      preparedBatch.execute();
    });
    return metrics;
  }

}
