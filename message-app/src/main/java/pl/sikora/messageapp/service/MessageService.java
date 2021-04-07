package pl.sikora.messageapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sikora.messageapp.dao.MessageRepo;
import pl.sikora.messageapp.dao.entity.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepo messageRepo;

    @Autowired
    public MessageService(MessageRepo messageRepo){
        this.messageRepo=messageRepo;
    }

    public Iterable<Message> findAll(){
        return messageRepo.findAll();
    }

    public Message save(Message message){
        return messageRepo.save(message);
    }

    public Message findFirst(){
        List<Message> list = new ArrayList<>();
        messageRepo.findAll().forEach(element -> list.add(element));
        return list.get(0);
    }

    public boolean ifMessageAlreadyExist(String id){
            List<String > list = new ArrayList<>();
            messageRepo.findAll().forEach(element -> list.add(element.getId().toString()));
            return list.contains(id);
    }
}
