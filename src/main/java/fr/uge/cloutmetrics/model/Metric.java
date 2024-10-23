package fr.uge.cloutmetrics.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

/**
 * Defines the metrics.
 * <br>For example : the language's ratio, number of commits, etc.
 */
@JsonSerialize
public record Metric(

    @ColumnName("metric_id")
    Long id,

    @ColumnName("git_id")
    Long gitId,

    @ColumnName("languages_ratio")
    String languagesRatio,

    @ColumnName("number_commits")
    String numberCommits
) {

  /**
   * JDBI constructor for the Metrics record.
   *
   * @param id Metric's id.
   * @param languagesRatio Metrics : compute percentage of file extension.
   */
  @JdbiConstructor
  public Metric {
    id = id == null ? 0L : id;
    gitId = gitId == null ? 0L : gitId;
    languagesRatio = languagesRatio == null ? "loading" : languagesRatio;
    numberCommits = numberCommits == null ? "loading" : numberCommits;
  }

}