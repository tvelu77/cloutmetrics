package io.tvelu77.cloutmetrics.git;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
}
