package ru.foto73.services;

import org.springframework.stereotype.Service;
import ru.foto73.model.Message;
import ru.foto73.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAllMessage(){
        return messageRepository.findAll();
    }
    public void saveMessage(Message message){
        messageRepository.save(message);
    }
    public void deleteMessage(Long id){
        messageRepository.deleteById(id);
    }
}
