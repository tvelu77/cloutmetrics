package fr.uge.cloutmetrics.repository;

import java.util.List;
import java.util.Optional;

/**
 * This class is used to define SQL methods that will be used with JDBI.
 */
public interface CloutMetricsRepository<T> {

  /**
   * Search and return all the elements found in the database.
   *
   * @return a List of elements.
   */
  List<T> findAll();

  /**
   * Search the element with corresponding id.
   *
   * @param id Element's id.
   * @return An Optional of an element.
   */
  Optional<T> findById(Long id);

  /**
   * Save in the database the given element.
   *
   * @param element The element.
   * @return The saved element.
   */
  T save(T element);

  /**
   * Save the list of elements in the database.
   *
   * @param elements A list of elements.
   * @return The saved list of elements.
   */
  List<T> saveAll(List<T> elements);
}
