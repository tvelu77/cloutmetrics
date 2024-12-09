package io.tvelu77.cloutmetrics.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Generic interface for service.
 */
public interface ApplicationService<T> {
  
  /**
   * Saves the object into the database.
   *
   * @param object The record to be saved into the database.
   * @return boolean, true if it was successfully saved.
   */
  boolean save(T object);
  
  /**
   * Deletes the record associated to the given ID.
   *
   * @param id {@link Long}, the record's id.
   * @return boolean, true if it was successfully deleted.
   */
  boolean delete(Long id);

  /**
   * Updates the record by replacing it with a new one.
   *
   * @param object The new record to replace the current one in the database.
   * @param id {@link Long}, the record's id.
   * @return boolean, true if it was successfully updated.
   */
  boolean update(T object, Long id);
  
  /**
   * Returns every record.
   *
   * @return {@link List}, a list of every record.
   */
  List<T> findAll();
  
  /**
   * Returns a record with the given id.<br>
   * This method should throw {@link NoSuchElementException}
   * if the record is not found.
   *
   * @param id {@link Long}, the record's id.
   * @return The record.
   */
  T findById(Long id);
}
