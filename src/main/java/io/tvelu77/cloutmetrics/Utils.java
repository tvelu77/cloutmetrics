package io.tvelu77.cloutmetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tvelu77.cloutmetrics.utils.Language;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Defines methods and unmodifiable fields usable everywhere.
 */
@Component
public class Utils {
  
  public static final Map<String, Language> EXTENSION = new HashMap<>();
  
  @Autowired
  private Environment env;

  public String getLocalRepositoryPath() {
    return env.getProperty("repositories.path");
  }
  
  static {
    try {
      var languages = new ObjectMapper()
          .readValue(
              Path.of("src/main/resources/Programming_Languages_Extensions.json").toFile(),
              Language[].class);
      for (var language : languages) {
        if (language.getExtensions() != null) {
          for (var extension : language.getExtensions()) {
            EXTENSION.putIfAbsent(extension, language);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
