package io.tvelu77.cloutmetrics.metrics;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import java.util.Map;
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
  private Long totalTags;

  @Column
  private Long totalBranches;
  
  @Column
  private String owner;

  @ElementCollection
  @MapKeyColumn(name = "language")
  private Map<String, Long> languageAndFileCount;

  @ElementCollection
  @MapKeyColumn(name = "language")
  private Map<String, Double> languageRatio;
  
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
  
  public Long getTotalTags() {
    return totalTags;
  }

  public void setTotalTags(Long totalTags) {
    this.totalTags = Objects.requireNonNull(totalTags);
  }
  
  public Map<String, Long> getLanguageAndFileCount() {
    return languageAndFileCount;
  }

  public void setLanguageAndFileCount(Map<String, Long> languageAndFileCount) {
    this.languageAndFileCount = Objects.requireNonNull(languageAndFileCount);
  }
  
  public Map<String, Double> getLanguageRatio() {
    return languageRatio;
  }

  public void setLanguageRatio(Map<String, Double> languageRatio) {
    this.languageRatio = Objects.requireNonNull(languageRatio);
  }
  
  public Long getTotalBranches() {
    return totalBranches;
  }

  public void setTotalBranches(Long totalBranches) {
    this.totalBranches = Objects.requireNonNull(totalBranches);
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = Objects.requireNonNull(owner);
  }
  

}
