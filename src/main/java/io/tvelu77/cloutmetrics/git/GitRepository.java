package io.tvelu77.cloutmetrics.git;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Git repository.<br>
 * Implements JpaRepository for CRUD operations and pagination.<br>
 */
public interface GitRepository extends JpaRepository<Git, Long> {

}
