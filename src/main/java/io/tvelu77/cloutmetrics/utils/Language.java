package io.tvelu77.cloutmetrics.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Objects;

/**
 * Defines a programming language and its extension.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Language {
  
  private String name;

  private LanguageType type;

  private List<String> extensions;
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = Objects.requireNonNull(name);
  }
  
  public LanguageType getType() {
    return type;
  }
  
  public void setType(LanguageType type) {
    this.type = Objects.requireNonNull(type);
  }
  
  public List<String> getExtensions() {
    return extensions;
  }
  
  public void setExtensions(List<String> extensions) {
    this.extensions = Objects.requireNonNull(extensions);
  }

}
