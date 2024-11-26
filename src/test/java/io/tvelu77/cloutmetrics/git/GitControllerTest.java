package io.tvelu77.cloutmetrics.git;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GitControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void emptyGetAll() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/gits")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  public void returnErrorWithBadId() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/gits/100")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }

  @Test
  public void correctGet() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(MockMvcRequestBuilders.post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(MockMvcRequestBuilders.get("/gits/1")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is("clout")));
  }
  
  @Test
  public void badGet() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/gits/100")).andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void correctPost() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(MockMvcRequestBuilders.post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(git.getName())));
  }

  @Test
  public void correctDelete() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(MockMvcRequestBuilders.post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(MockMvcRequestBuilders.delete("/gits/1")).andExpect(MockMvcResultMatchers.status().isNoContent());
    mvc.perform(MockMvcRequestBuilders.get("/gits")).andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  public void badDelete() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    git.setStatus(GitStatus.IN_PROGRESS);
    mvc.perform(MockMvcRequestBuilders.post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(git.getName())));
    mvc.perform(MockMvcRequestBuilders.delete("/gits/1")).andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  public void correctUpdate() throws Exception {
    var git = new Git(1L, "clout", "localhost");
    mvc.perform(MockMvcRequestBuilders.post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(git.getName())));
    git.setName("not clout");
    mvc.perform(MockMvcRequestBuilders.put("/gits/1").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    mvc.perform(MockMvcRequestBuilders.get("/gits/1")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is("not clout")));
  }

  private static byte[] toJson(Object object) throws IOException {
    var mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }
}
