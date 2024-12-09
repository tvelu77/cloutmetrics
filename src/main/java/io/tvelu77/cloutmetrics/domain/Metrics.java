package io.tvelu77.cloutmetrics.domain;

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
 * Represents a {@link Git} metrics such as :<br>
 * - The number of commits ;<br>
 * - The languages ratio ;<br>
 * - etc (TODO : add more metrics such as the top list of contributor !).
 */
@Entity
@Table
public class Metrics {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;
  
  @Column
  private Long totalCommits = 0L;

  @Column
  private Long totalTags = 0L;

  @Column
  private Long totalBranches = 0L;
  
  @Column
  private String owner = "UNKNOWN";

  @ElementCollection
  @MapKeyColumn(name = "language")
  private Map<String, Long> languageAndFileCount;

  @ElementCollection
  @MapKeyColumn(name = "language")
  private Map<String, Double> languageRatio;
  
  /**
   * Returns the id.
   *
   * @return {@link Long}, the metrics id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.<br>
   * The id should not be null nor inferior to 1.
   *
   * @param id {@link Long}, the new id.
   */
  public void setId(Long id) {
    Objects.requireNonNull(id, "The id should not be null");
    if (id < 1) {
      throw new IllegalArgumentException("The id should be superior to 0 !");
    }
    this.id = id;
  }

  /**
   * Returns the number of commits done.
   *
   * @return {@link Long}, the number of commits done.
   */
  public Long getTotalCommits() {
    return totalCommits;
  }

  /**
   * Sets the number of commits.<br>
   * The commits should not be null nor inferior to 0.
   *
   * @param totalCommits {@link Long}, the new number of commits.
   */
  public void setTotalCommits(Long totalCommits) {
    Objects.requireNonNull(totalCommits, "The number of commits should not be null !");
    if (totalCommits < 0) {
      throw new IllegalArgumentException("The number of commits should not be inferior to 0");
    }
    this.totalCommits = totalCommits;
  }

  /**
   * Returns the number of tags.
   *
   * @return {@link Long}
   */
  public Long getTotalTags() {
    return totalTags;
  }

  /**
   * Sets the number of tags.<br>
   * The parameter should not be null.
   *
   * @param totalTags {@link Long}, the new number of tags.
   */
  public void setTotalTags(Long totalTags) {
    this.totalTags = Objects.requireNonNull(totalTags);
  }
  
  /**
   * Returns the number of files for every programming language.
   *
   * @return {@link Map}<{@link String}, {@link Long}>,
   *        the current files count for every language.
   */
  public Map<String, Long> getLanguageAndFileCount() {
    return languageAndFileCount;
  }

  /**
   * Sets the files count for every programming language.<br>
   * The parameter should not be null.
   *
   * @param languageAndFileCount {@link Map}<{@link String}, {@link Long}>,
   *        the new files count for every language.
   */
  public void setLanguageAndFileCount(Map<String, Long> languageAndFileCount) {
    this.languageAndFileCount = Objects.requireNonNull(languageAndFileCount);
  }
  
  /**
   * Returns the lines/language ratio.
   *
   * @return {@link Map}<{@link String}, {@link Long}>,
   *          the lines/language ratio.
   */
  public Map<String, Double> getLanguageRatio() {
    return languageRatio;
  }

  /**
   * Sets the lines/language ratio.<br>
   * The parameter should not be null.
   *
   * @param languageRatio {@link Map}<{@link String}, {@link Long}>,
   *        the new lines/language ratio.
   */
  public void setLanguageRatio(Map<String, Double> languageRatio) {
    this.languageRatio = Objects.requireNonNull(languageRatio);
  }
  
  /**
   * Returns the number of branches.
   *
   * @return {@link Long}, the number of branches.
   */
  public Long getTotalBranches() {
    return totalBranches;
  }

  /**
   * Sets the number of branches.<br>
   * The parameter should not be null.
   *
   * @param totalBranches {@link Long}, the new number of branches.
   */
  public void setTotalBranches(Long totalBranches) {
    this.totalBranches = Objects.requireNonNull(totalBranches);
  }

  /**
   * Returns the git's owner.
   *
   * @return {@link String}, the owner.
   * @deprecated The owner attribute is wrong, it should not be used.
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Sets the owner.<br>
   * The parameter should not be null.
   *
   * @param owner {@link String}, the new owner.
   * @deprecated The owner attribute is wrong, it should not be used.
   */
  public void setOwner(String owner) {
    this.owner = Objects.requireNonNull(owner);
  }
  

}
