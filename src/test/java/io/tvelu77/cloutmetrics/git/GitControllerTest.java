package io.tvelu77.cloutmetrics.git;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.tvelu77.cloutmetrics.metrics.Metrics;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class GitControllerTest {

  @Test
  public void emptyGetAll(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/gits")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  public void returnErrorWithBadId(@Autowired MockMvc mvc) throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/gits/100")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
  }
  
  @Test
  public void correctPost(@Autowired MockMvc mvc) throws Exception {
    var git = new Git(1L, "clout", "localhost", new Metrics());
    mvc.perform(MockMvcRequestBuilders.post("/gits").contentType(MediaType.APPLICATION_JSON).content(toJson(git)))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(git.getName())));
  }
  
  private static byte[] toJson(Object object) throws IOException {
    var mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }
}
