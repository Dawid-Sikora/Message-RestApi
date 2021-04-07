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
import pl.sikora.messageapp.dao.entity.Message;
import pl.sikora.messageapp.service.MessageService;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpPutTest {

    @Autowired
    private MockMvc mockMvc;

    private MessageService messageService;

    String idOfFirstMessage = "";

    String putURL;

    @Autowired
    public HttpPutTest(MessageService messageService) {
        this.messageService = messageService;
        Message firstMessage = messageService.findFirst();
        idOfFirstMessage = firstMessage.getId().toString();
        putURL = "http://localhost:8080/message/" + idOfFirstMessage;
    }

    @Test
    void should_put_first_message_in_database_with_content_test_data1() throws Exception {
        putMethod("test data 1");
    }

    @Test
    void should_put_first_message_in_database_with_content_test_data2() throws Exception {
        putMethod("test data 2");
    }

    @Test
    void should_not_put_the_message() throws Exception {
        failedPutMethod("");
    }

    private void failedPutMethod(String data) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(putURL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(getArticleInJson(data)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    private void putMethod(String data) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(putURL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(getArticleInJson(data)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(idOfFirstMessage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is(data)))
                .andDo(MockMvcResultHandlers.print());
    }

    private String getArticleInJson(String data) {
        return "{\"content\": \"" + data + "\"}";
    }

}
