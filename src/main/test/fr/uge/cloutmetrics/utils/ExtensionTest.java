package fr.uge.cloutmetrics.utils;

import static fr.uge.cloutmetrics.utils.Utils.EXTENSION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ExtensionTest {

  @Test
  public void shouldReturnLanguage() {
    assertEquals("javascript", EXTENSION.get("js"));
  }

  @Test
  public void shouldReturnNull() {
    assertNull(EXTENSION.get("aaaaaaah"));
  }

  @Test
  public void shouldReturnEveryLanguageInEnum() {
    for (var language : Utils.Language.values()) {
      for (var extension : language.getExtensions()) {
        assertEquals(language.getLanguage(), EXTENSION.get(extension));
      }
    }
  }
}
