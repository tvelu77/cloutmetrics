package fr.uge.cloutmetrics.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

/**
 * Represent a Git repository.
 */
@JsonSerialize
public record Git(

    @ColumnName("git_id")
    Long id,

    @ColumnName("git_name")
    String name,

    @ColumnName("git_path")
    String path,

    @ColumnName("git_date")
    LocalDateTime addedDate
) {

  /**
   * JDBI constructor for the Git record.
   *
   * @param id Git's id.
   * @param name Git's name.
   * @param path Git's path.
   * @param addedDate Git's date.
   */
  @JdbiConstructor
  public Git {
    id = id == null ? 0L : id;
    name = name == null ? "loading" : name;
    path = path == null ? "loading" : path;
    addedDate = addedDate == null ? LocalDateTime.now() : addedDate;
  }

}
