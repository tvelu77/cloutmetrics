package fr.uge.cloutmetrics.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class GitTest {

  @Test
  public void gitDefaultIdValue() {
    var git = new Git(null, "test", "test", LocalDateTime.MIN);
    assertEquals(0L, git.id());
  }

  @Test
  public void gitDefaultNameValue() {
    var git = new Git(0L, null, "test", LocalDateTime.MIN);
    assertEquals("loading", git.name());
  }

  @Test
  public void gitDefaultPathValue() {
    var git = new Git(0L, "test", null, LocalDateTime.MIN);
    assertEquals("loading", git.path());
  }

  @Test
  public void gitReturnCorrect() {
    var git = new Git(0L, "test", "test", LocalDateTime.MIN);
    assertEquals(0L, git.id());
    assertEquals("test", git.name());
    assertEquals("test", git.path());
    assertEquals(LocalDateTime.MIN, git.addedDate());
  }
}
