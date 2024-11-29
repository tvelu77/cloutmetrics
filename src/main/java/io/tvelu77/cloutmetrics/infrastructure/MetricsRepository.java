package io.tvelu77.cloutmetrics.infrastructure;

import io.tvelu77.cloutmetrics.domain.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Metrics repository.<br>
 * Implements JpaRepository for CRUD operations and pagination.<br>
 */
public interface MetricsRepository extends JpaRepository<Metrics, Long> {

}
