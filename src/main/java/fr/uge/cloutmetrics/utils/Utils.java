package fr.uge.cloutmetrics.utils;

import java.util.HashMap;
import java.util.Set;

/**
 * Defines methods and unmodifiable fields usable everywhere.
 */
public class Utils {
  public static final String LOCAL_REPOSITORY_PATH = "src/main/resources/repositories/";
  public static final HashMap<String, String> EXTENSION = new HashMap<>();

  /**
   * Enumerate every language and their extensions.
   */
  // Package visibility for testing purposes
  enum Language {
    JAVA("java", Set.of("jsp", "java")),
    C("c", Set.of("c", "h")),
    RUBY("ruby", Set.of("rs")),
    PYTHON("python", Set.of("py")),
    HTML("html", Set.of("html", "xhtml")),
    CSS("css", Set.of("css")),
    JAVASCRIPT("javascript", Set.of("js", "mjs", "cjs", "jsx")),
    PHP("php", Set.of("php")),
    CSharp("c#", Set.of("cs")),
    CPP("c++", Set.of("cpp", "hpp", "def")),
    PERL("perl", Set.of("pl")),
    LUA("lua", Set.of("lua")),
    BASH("bash", Set.of("sh"));

    private final String name;
    private final Set<String> extensions;

    Language(String name, Set<String> extensions) {
      this.name = name;
      this.extensions = extensions;
    }

    String getLanguage() {
      return name;
    }

    Set<String> getExtensions() {
      return extensions;
    }

  }

  static {
    for (var language : Language.values()) {
      for (var extension : language.extensions) {
        EXTENSION.putIfAbsent(extension, language.name);
      }
    }
  }

}
