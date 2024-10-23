package io.tvelu77.cloutmetrics.git;

import io.tvelu77.cloutmetrics.metrics.Metrics;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Git model.<br>
 * Defines the "Git" entity.<br>
 * Actually, it is an entity to identify a git and its metrics.
 */
@Entity
@Table
public class Git {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 256, nullable = false)
  private String name;
  
  @Column(nullable = false)
  private LocalDateTime date;
  
  @Column
  private String url;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "metric_id", referencedColumnName = "id")
  private Metrics metric;
  
  /**
   * Git's empty constructor.
   */
  public Git() {
    
  }
  
  /**
   * Git's full constructor.
   *
   * @param id Long, the given id.
   * @param name String, the git's name (should be the same as the true git repository).
   * @param date LocalDateTime, the date of the git addition to the database.
   * @param url String, the repository link.
   */
  public Git(Long id, String name, LocalDateTime date, String url) {
    this.id = Objects.requireNonNull(id);
    this.name = Objects.requireNonNull(name);
    this.date = Objects.requireNonNull(date);
    this.url = Objects.requireNonNull(url);
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = Objects.requireNonNull(id);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = Objects.requireNonNull(name);
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = Objects.requireNonNull(date);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = Objects.requireNonNull(url);
  }

}
