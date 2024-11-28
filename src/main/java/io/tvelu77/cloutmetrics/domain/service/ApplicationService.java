package io.tvelu77.cloutmetrics.domain.service;

import java.util.List;

/**
 * Generic interface for service.
 */
public interface ApplicationService<T> {
  
  boolean add(T object);
  
  boolean delete(Long id);

  boolean update(T object, Long id);
  
  List<T> findAll();
  
  T findById(Long id);
}
