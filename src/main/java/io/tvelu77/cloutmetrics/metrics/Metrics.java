package io.tvelu77.cloutmetrics.metrics;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Represents a git metrics :
 * - The number of commits ;
 * - The languages ratio ;
 * - etc (TODO : add more metrics such as the top list of contributor !).
 */
@Entity
@Table
public class Metrics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;
  
  @Column
  private Long totalCommits;
  
  @Column
  private Status status;
  
  private enum Status {
    NOT_LAUNCHED,
    STARTED,
    IN_PROGRESS,
    FINISHED,
    ERROR
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = Objects.requireNonNull(id);
  }

  public Long getTotalCommits() {
    return totalCommits;
  }

  public void setTotalCommits(Long totalCommits) {
    this.totalCommits = Objects.requireNonNull(totalCommits);
  }

}
