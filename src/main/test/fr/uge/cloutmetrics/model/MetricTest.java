package fr.uge.cloutmetrics.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MetricTest {

  @Test
  public void metricIdsDefaultValue() {
    var metric = new Metric(null, null, "test", "test");
    assertEquals(0L, metric.id());
    assertEquals(0L, metric.gitId());
  }

  @Test
  public void metricMetricsDefaultValue() {
    var metric = new Metric(0L, 0L, null, null);
    assertEquals("loading", metric.languagesRatio());
    assertEquals("loading", metric.numberCommits());
  }
}
