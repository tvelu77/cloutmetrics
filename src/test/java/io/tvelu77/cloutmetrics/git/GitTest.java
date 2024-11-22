package io.tvelu77.cloutmetrics.git;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.tvelu77.cloutmetrics.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * Testing the {@link Git} class.<br>
 * In this case, we test the setters and getters.
 */
public class GitTest {

  @Test
  public void emptyConstructorShouldNotThrow() {
    assertDoesNotThrow(() -> new Git());
  }

  @Test
  public void nonDefaultConstructorShouldNotThrow() {
    assertDoesNotThrow(() -> new Git(5L, "clout", "https://localhost:8080", new Metrics()));
  }

  @Test
  public void nonDefaultConstructorShouldThrowIfIdIsNegative() {
    assertThrows(IllegalArgumentException.class,
            () -> new Git(-1L, "clout", "https://localhost:8080", new Metrics()));
  }

  @Test
  public void nonDefaultConstructorShouldThrowIfIdIsNull() {
    assertThrows(NullPointerException.class,
            () -> new Git(null, "clout", "https://localhost:8080", new Metrics()));
  }

  @Test
  public void nonDefaultConstructorShouldThrowIfNameIsNull() {
    assertThrows(NullPointerException.class,
            () -> new Git(1L, null, "https://localhost:8080", new Metrics()));
  }

  @Test
  public void nonDefaultConstructorShouldThrowIfUrlIsNull() {
    assertThrows(NullPointerException.class,
            () -> new Git(1L, "clout", null, new Metrics()));
  }

  @Test
  public void nonDefaultConstructorShouldThrowIfMetricsIsNull() {
    assertThrows(NullPointerException.class,
            () -> new Git(1L, "clout", "https://localhost:8080", null));
  }

  @Test
  public void dateConstructorShouldNotThrow() {
    assertDoesNotThrow(
            () -> new Git(5L, "clout", "https://localhost:8080", new Metrics(), LocalDateTime.now()));
  }

  @Test
  public void statusConstructorShouldNotThrow() {
    assertDoesNotThrow(
            () -> new Git(5L, "clout", "https://localhost:8080", new Metrics(), GitStatus.FINISHED));
  }

  @Test
  public void dateAndStatusConstructorShouldNotThrow() {
    assertDoesNotThrow(
            () -> new Git(5L,
                    "clout",
                    "https://localhost:8080",
                    new Metrics(),
                    LocalDateTime.now(),
                    GitStatus.FINISHED));
  }
  
  @Test
  public void shouldThrowExceptionIfNegativeId() {
    assertThrows(IllegalArgumentException.class, () -> new Git().setId(-1L));
  }
  
  @Test
  public void shouldThrowExceptionIfIdIsZero() {
    assertThrows(IllegalArgumentException.class, () -> new Git().setId(0L));
  }
  
  @Test
  public void shouldThrowExceptionIfIdIsNull() {
    assertThrows(NullPointerException.class, () -> new Git().setId(null));
  }

  @Test
  public void shouldAcceptCorrectId() {
    var expected = 5L;
    var git = new Git();
    assertDoesNotThrow(() -> git.setId(5L));
    assertEquals(expected, git.getId());
  }

  @Test
  public void shouldThrowExceptionIfNameIsNull() {
    assertThrows(NullPointerException.class, () -> new Git().setName(null));
  }

  @Test
  public void shouldAcceptCorrectName() {
    var expected = "git repository";
    var git = new Git();
    assertDoesNotThrow(() -> git.setName("git repository"));
    assertEquals(expected, git.getName());
  }

  @Test
  public void shouldThrowExceptionIfDateIsAfterNow() {
    assertThrows(IllegalArgumentException.class,
            () -> new Git().setDate(LocalDateTime.now().plusSeconds(10L)));
  }

  @Test
  public void shouldAcceptCorrectDate() {
    var expected = LocalDateTime.now();
    var git = new Git();
    assertDoesNotThrow(() -> git.setDate(expected));
    assertEquals(expected, git.getDate());
  }

  @Test
  public void shouldThrowExceptionIfUrlIsNull() {
    assertThrows(NullPointerException.class, () -> new Git().setUrl(null));
  }

  @Test
  public void shouldAcceptCorrectUrl() {
    var expected = "https://localhost:8080";
    var git = new Git();
    assertDoesNotThrow(() -> git.setUrl("https://localhost:8080"));
    assertEquals(expected, git.getUrl());
  }

  @Test
  public void shouldThrowExceptionIfStatusIsNull() {
    assertThrows(NullPointerException.class, () -> new Git().setStatus(null));
  }

  @Test
  public void shouldAcceptCorrectStatus() {
    var expected = GitStatus.UPLOADED;
    var git = new Git();
    assertDoesNotThrow(() -> git.setStatus(GitStatus.UPLOADED));
    assertEquals(expected, git.getStatus());
  }

  @Test
  public void shouldReturnDefaultStatus() {
    assertEquals(GitStatus.UPLOADED, new Git().getStatus());
  }

  @Test
  public void shouldThrowExceptionIfMetricsIsNull() {
    assertThrows(NullPointerException.class, () -> new Git().setMetrics(null));
  }

  @Test
  public void shouldAcceptCorrectMetrics() {
    var expected = new Metrics();
    var git = new Git();
    assertDoesNotThrow(() -> git.setMetrics(expected));
    assertEquals(expected, git.getMetrics());
  }

}
