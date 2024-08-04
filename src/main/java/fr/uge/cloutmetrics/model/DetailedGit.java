package fr.uge.cloutmetrics.model;

import java.time.LocalDateTime;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

/**
 * Defines the join between git, metrics and contributors.
 */
public record DetailedGit(

        @ColumnName("git_id")
        Long gitId,

        @ColumnName("git_name")
        String gitName,

        @ColumnName("git_date")
        LocalDateTime addedDate,

        @ColumnName("languages_ratio")
        String languagesRatio,

        @ColumnName("number_commits")
        String numberCommits

) {

  @JdbiConstructor
  public DetailedGit {
  }

}
