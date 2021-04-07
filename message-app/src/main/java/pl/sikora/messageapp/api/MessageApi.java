package pl.sikora.messageapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.sikora.messageapp.dao.entity.Message;
import pl.sikora.messageapp.service.MessageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/message")
public class MessageApi {

    private MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/{id}")
    public String read(@PathVariable("id") String Id, Model model) {
        return Id;
    }


    @GetMapping
    public Iterable<Message> getAll() {
        return messageService.findAll();
    }

    @GetMapping("/random/10")
    public List<Message> getRandom10() {
        List<Message> list = new ArrayList<>();
        while (list.size() < 10) {
            messageService.findAll().forEach(x -> {
                double d = Math.random();
                if (d > 0.5) {
                    if (!list.contains(x) && list.size() < 10) {
                        list.add(x);
                    }
                }
            });
        }
        return list;
    }

    @PostMapping
    public Message addMessage(@Valid @RequestBody Message message, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Content can't be empty");
        }
        boolean isExist;
        try {
            isExist = messageService.ifMessageAlreadyExist(message.getId().toString());
        } catch (Exception e) {
            isExist = false;
        }
        if (!isExist) {
            return messageService.save(message);
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Message with ID: " + message.getId().toString() + " already exists.");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Message updateMessage(@PathVariable("id") String id, @Valid @RequestBody Message message, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Content can't be empty");
        }
        if (messageService.ifMessageAlreadyExist(id)) {
            return messageService.save(new Message(UUID.fromString(id), message.getContent()));
        } else
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Message with ID: " + id + " doesn't exists.");
    }

}

