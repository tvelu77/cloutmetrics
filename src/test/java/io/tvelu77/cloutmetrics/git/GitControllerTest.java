package io.tvelu77.cloutmetrics.git;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.tvelu77.cloutmetrics.domain.Git;
import io.tvelu77.cloutmetrics.domain.GitStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GitControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void shouldReturnEmptyJsonAfterGet() throws Exception {
    mvc.perform(get("/gits")).andExpect(status().isOk())
            .andExpect(content().json("[]"));
  }

  @Test
  public void shouldReturnJsonAfterGet() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    var git2 = new Git(2L, "clout2", "localhost");
    var git3 = new Git(3L, "clout3", "localhost");
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git2)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git2.getName())));
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git3)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git3.getName())));
    mvc.perform(get("/gits")).andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(3)));
  }

  @Test
  public void shouldReturnGitAfterGetById() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(get("/gits/1")).andExpect(status().isOk())
            .andExpect(jsonPath("name", CoreMatchers.is("clout")));
  }

  @Test
  public void shouldReturn404AfterGetByIdIfWrongId() throws Exception {
    mvc.perform(get("/gits/100")).andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturn201AfterGetById() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
  }

  @Test
  public void shouldReturn204AfterDelete() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(delete("/gits/1")).andExpect(status().isNoContent());
    mvc.perform(get("/gits")).andExpect(content().json("[]"));
  }

  @Test
  public void shouldReturn404AfterDeleteIfBadId() throws Exception {
    mvc.perform(delete("/gits/100")).andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturn401AfterDeleteIfGitIsInProgress() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    git.setStatus(GitStatus.IN_PROGRESS);
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(delete("/gits/1")).andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldReturn204AfterPut() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    git.setName("not clout");
    mvc.perform(put("/gits/1").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isNoContent());
    mvc.perform(get("/gits/1")).andExpect(status().isOk())
            .andExpect(jsonPath("name", CoreMatchers.is("not clout")));
  }

  @Test
  public void shouldReturn404AfterPutIfBadId() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    git.setName("hello");
    mvc.perform(put("/gits/500").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturn401AfterPutIfGitIsInProgress() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    git.setStatus(GitStatus.IN_PROGRESS);
    mvc.perform(post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name", CoreMatchers.is(git.getName())));
    git.setName("hello");
    mvc.perform(put("/gits/1").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
            .andExpect(status().isUnauthorized());
  }

  /**
   * Converts an Object to a byte array formatted as a JSON.
   *
   * @param object In this case, it will be entities defined in this project.
   * @return byte[], a JSON represented by a byte array.
   * @throws IOException If the byte array couldn't be created.
   */
  private static byte[] toJson(Object object) throws IOException {
    var mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }
}
