package pl.sikora.messageapp.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sikora.messageapp.dao.entity.Message;

@Repository
public interface MessageRepo extends CrudRepository<Message,Integer> {
}
