package com.adithya.WhatsAppScheduler.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.adithya.WhatsAppScheduler.Dao.MessageDao;
import com.adithya.WhatsAppScheduler.entities.Client;
import com.adithya.WhatsAppScheduler.entities.Message;
import com.adithya.WhatsAppScheduler.entities.Request;
import com.adithya.WhatsAppScheduler.exception.ResourceNotFoundException;

@Service
public class MessageService {
    @Autowired
	MessageDao eDAO;


	public MessageService(MessageDao eDAO) {
        this.eDAO = eDAO;
    }


    public int saveMessage(Request requestBody, Client client) throws ResourceNotFoundException {
        return eDAO.save(requestBody, client);
    }
    
    public int updateMessageStatus(Boolean pending_status, Boolean submited_status, String whatsAppMessageId, LocalDateTime submitted_at, Integer message_id) throws ResourceNotFoundException {

        return eDAO.updateMessageStatus(pending_status, submited_status, whatsAppMessageId, submitted_at, message_id);
    }


    public List<Message> pollMessagesFromDatabase() throws  ResourceNotFoundException{
        return eDAO.getAllMessagesInOneMinute();
    }


	public int save(@Valid  Request requestBody, Client client) {
		return 0;
	}
	
}
