package io.tvelu77.cloutmetrics.domain;

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
  private LocalDateTime date = LocalDateTime.now();
  
  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private GitStatus status = GitStatus.UPLOADED;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "metric_id", referencedColumnName = "id")
  private Metrics metrics = new Metrics();
  
  /**
   * Git's empty constructor.<br>
   * Mainly used by JPA.<br>
   * You should see {@link Git#Git(Long, String, String)}.
   */
  public Git() {}

  /**
   * Git's default constructor.<br>
   * This constructor is used if the default values for date and status
   * is fine for you.<br>
   * By default, status has {@link GitStatus#UPLOADED}
   * and date has {@link LocalDateTime#now()}.<br>
   * The ID should be superior to 0.
   *
   * @param id {@link Long}, a long object representing an unique ID.
   * @param name {@link String}, a String representing the name.
   * @param url {@link String}, a String representing the url for the git.
   */
  public Git(Long id, String name, String url) {
    Objects.requireNonNull(id, "Git's id cannot be null !");
    if (id < 1L) {
      throw new IllegalArgumentException("Git's id should not be less or equal to 0 !");
    }
    this.id = id;
    this.name = Objects.requireNonNull(name, "Git's name cannot be null !");
    this.url = Objects.requireNonNull(url, "Git's url cannot be null !");
  }
  
  public Long getId() {
    return id;
  }

  /**
   * The setter for the ID.<br>
   * The ID should not be less than 0 (nor equal).
   *
   * @param id {@link Long}, an id.
   */
  public void setId(Long id) {
    if (id < 1) {
      throw new IllegalArgumentException("Git's id should not be less or equal to 0 !");
    }
    this.id = Objects.requireNonNull(id, "Git's id cannot be null !");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = Objects.requireNonNull(name, "Git's name cannot be null !");
  }

  public LocalDateTime getDate() {
    return date;
  }

  /**
   * The setter for the date.<br>
   * The given datetime should be inferior to the current datetime.
   *
   * @param date {@link LocalDateTime}, the datetime when the git has been posted.
   */
  public void setDate(LocalDateTime date) {
    if (date.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Git's date cannot be in the future !");
    }
    this.date = Objects.requireNonNull(date);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = Objects.requireNonNull(url);
  }

  public GitStatus getStatus() {
    return status;
  }

  public void setStatus(GitStatus status) {
    this.status = Objects.requireNonNull(status);
  }
  
  public Metrics getMetrics() {
    return metrics;
  }
  
  public void setMetrics(Metrics metrics) {
    this.metrics = Objects.requireNonNull(metrics);
  }

}
