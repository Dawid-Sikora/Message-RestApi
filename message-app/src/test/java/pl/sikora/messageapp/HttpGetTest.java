package pl.sikora.messageapp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class HttpGetTest {

    @Autowired
    private MockMvc mockMvc;

    String randomMessagesUrl = "http://localhost:8080/message/random/10";

    @Test
    void should_get_10_random_messages() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(randomMessagesUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(10)))
                .andDo(MockMvcResultHandlers.print());

    }
}