package io.tvelu77.cloutmetrics.git;

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
 * Git's controller.<br>
 * For sending HTTP response.
 */
@RestController
@RequestMapping("/gits")
public class GitController {
  
  @Autowired
  private GitService gitService;
  
  @GetMapping("")
  public ResponseEntity<List<Git>> findAll() {
    return new ResponseEntity<>(gitService.findAll(), HttpStatus.OK);
  }
  
  /**
   * Fetches the git with the given id
   * and returns a response.
   *
   * @param id Long, the git id.
   * @return A response :<br>
   *     - 200 and the git if it was found ;<br>
   *     - 404 if the git wasn't found.
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
   * Adds the given git into the database
   * and returns a response.
   *
   * @param git Git, the git model to be added in the database.
   * @return A response :<br>
   *     - 200 if it was successful ;<br>
   *     - 400 if it wasn't successful.
   */
  @PostMapping("")
  public ResponseEntity<Git> add(@RequestBody Git git) {
    Objects.requireNonNull(git);
    if (gitService.add(git)) {
      return new ResponseEntity<>(git, HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
  
  /**
   * Removes the given git (by its id) from the database
   * and returns a response.
   *
   * @param id Long, the git's id to be removed.
   * @return A response :<br>
   *     - 200 if it was successful ;<br>
   *     - 404 if it wasn't successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Git> remove(@PathVariable Long id) {
    Objects.requireNonNull(id);
    try {
      gitService.delete(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  
  /**
   * Updates the given git (by its id) in the database
   * and returns a response.
   *
   * @param newGit Git, the new git to replace the current one.
   * @param id Long, the git's id to be removed.
   * @return A response :<br>
   *     - 200 if it was successful ;<br>
   *     - 404 if it wasn't successful.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Git> update(@RequestBody Git newGit, @PathVariable Long id) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(newGit);
    try {
      gitService.update(newGit, id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
