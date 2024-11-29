package io.tvelu77.cloutmetrics.infrastructure;

import io.tvelu77.cloutmetrics.domain.Git;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Git repository.<br>
 * Implements JpaRepository for CRUD operations and pagination.<br>
 */
public interface GitRepository extends JpaRepository<Git, Long> {

}
