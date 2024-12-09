package io.tvelu77.cloutmetrics.application.rest;

import io.tvelu77.cloutmetrics.domain.Git;
import io.tvelu77.cloutmetrics.domain.MetricsOperations;
import io.tvelu77.cloutmetrics.domain.service.GitService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link Git}'s controller.<br>
 * For sending HTTP response containing a code and sometimes the {@link Git} object.
 */
@RestController
@RequestMapping("/gits")
public class GitController {
  
  @Autowired
  private GitService gitService;
  
  
  /**
   * Fetches all the {@link Git} records in the database
   * and returns a {@link ResponseEntity}.
   *
   * @return  {@link ResponseEntity}<{@link List}<{@link Git}>>
   *      Contains a 200 HTTP code and the {@link List} of {@link Git}.
   *      The {@link List} can be empty.
   */
  @GetMapping("")
  public ResponseEntity<List<Git>> findAll() {
    return new ResponseEntity<>(gitService.findAll(), HttpStatus.OK);
  }
  
  /**
   * Fetches the {@link Git} with the given id
   * and returns a {@link ResponseEntity}.
   *
   * @param id {@link Long}, the {@link Git}'s id.
   * @return A {@link ResponseEntity} :<br>
   *     - 200 and the {@link Git} if it was found ;<br>
   *     - 404 if the {@link Git} wasn't found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Git> findById(@PathVariable Long id) {
    Objects.requireNonNull(id);
    try {
      return new ResponseEntity<>(gitService.findById(id), HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  /**
   * Adds the given {@link Git} into the database
   * and returns a {@link ResponseEntity}.
   *
   * @param git {@link Git}, the {@link Git} record to be added in the database.
   * @return A {@link ResponseEntity} :<br>
   *     - 201 if the {@link Git} was created ;<br>
   *     - 400 if it wasn't successful.
   */
  @PostMapping("")
  public ResponseEntity<Git> add(@RequestBody Git git) {
    Objects.requireNonNull(git);
    if (gitService.save(git)) {
      return new ResponseEntity<>(git, HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
  
  /**
   * Removes the given {@link Git} (by its id) from the database
   * and returns a {@link ResponseEntity}.
   *
   * @param id {@link Long}, the {@link Git}'s id to be removed.
   * @return A {@link ResponseEntity} :<br>
   *     - 204 if it was successful ;<br>
   *     - 401 if it couldn't be deleted ;<br>
   *     - 404 if the {@link Git} couldn't be found.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Git> remove(@PathVariable Long id) {
    Objects.requireNonNull(id);
    try {
      if (gitService.delete(id)) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  /**
   * Updates the given {@link Git} (by its id) in the database
   * and returns a {@link ResponseEntity}.
   *
   * @param newGit {@link Git}, the new git to replace the current one.
   * @param id {@link Long}, the git's id to be removed.
   * @return A {@link ResponseEntity} :<br>
   *     - 204 if it was successful ;<br>
   *     - 401 if it wasn't successful ;<br>
   *     - 404 if the {@link Git} couldn't be found.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Git> update(@RequestBody Git newGit, @PathVariable Long id) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(newGit);
    try {
      if (gitService.update(newGit, id)) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  /**
   * Updates the {@link Git} associated to the given id by computing the metrics.
   * It is updated with {@link MetricsOperations}.
   *
   * @param id {@link Long}, the git's id to be computed.
   * @return A {@link ResponseEntity} :<br>
   *     - 204 if it was successful ;<br>
   *     - 401 if it wasn't successful ;<br>
   *     - 404 if the {@link Git} couldn't be found.
   */
  @PutMapping("/{id}/compute")
  public ResponseEntity<Git> update(@PathVariable Long id) {
    Objects.requireNonNull(id);
    try {
      if (gitService.update(id)) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
