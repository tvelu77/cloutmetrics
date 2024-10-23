package io.tvelu77.cloutmetrics.metrics;

import io.tvelu77.cloutmetrics.Utils;
import io.tvelu77.cloutmetrics.git.Git;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Map;

/**
 * Represents a git metrics :
 * - The number of commits ;
 * - The languages ratio ;
 * - etc (TODO : add more metrics such as the top list of contributor !).
 */
public class Metrics {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;
  
  @Column
  private Long totalCommits;
  
  @Column
  private Map<Utils.Language, Double> languagesRatio;
  
  @OneToOne(mappedBy = "metrics")
  private Git git;

}
