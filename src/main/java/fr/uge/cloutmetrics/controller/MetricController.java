package fr.uge.cloutmetrics.controller;

import fr.uge.cloutmetrics.model.Metric;
import fr.uge.cloutmetrics.repository.MetricRepository;
import java.util.List;
import java.util.Objects;
import org.jdbi.v3.core.Jdbi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This class is used to calculate the metrics of a git.
 */
public class MetricController {

  private final MetricRepository metricRepository;

  /**
   * MetricController's constructor.
   *
   * @param jdbi Use JDBI to create queries
   */
  public MetricController(Jdbi jdbi) {
    Objects.requireNonNull(jdbi);
    this.metricRepository = new MetricRepository(jdbi);
  }

  /**
   * Create a new metric in the database.
   *
   * @param metric Metric object.
   * @return The added metric.
   */
  @PostMapping("/metric")
  public Metric createMetric(Metric metric) {
    Objects.requireNonNull(metric);
    return metricRepository.save(metric);
  }

  /**
   * List all existing metrics.
   *
   * @return A list of metrics.
   */
  @GetMapping("/metric")
  public List<Metric> getMetric() {
    return metricRepository.findAll();
  }

  /**
   * Return the corresponding metric according to the Metric's id.
   *
   * @param id Metric's id.
   * @return The corresponding metric.
   */
  @GetMapping("/metric/{id}")
  public Metric getMetric(@PathVariable Long id) {
    Objects.requireNonNull(id);
    return metricRepository.findById(id).orElseThrow();
  }

}
