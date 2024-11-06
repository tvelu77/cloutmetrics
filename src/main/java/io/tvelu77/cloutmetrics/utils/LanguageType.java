package io.tvelu77.cloutmetrics.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines an enum for the type of language.<br>
 * It can be a :<br>
 * - PROGRAMMING language ;<br>
 * - MARKUP language ;<br>
 * - DATA language ;<br>
 * - PROSE language.
 */
public enum LanguageType {

  @JsonProperty("programming")
  PROGRAMMING,

  @JsonProperty("markup")
  MARKUP,

  @JsonProperty("data")
  DATA,

  @JsonProperty("prose")
  PROSE;

}
