package com.adithya.WhatsAppScheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adithya.WhatsAppScheduler.Dao.ClientDao;
import com.adithya.WhatsAppScheduler.entities.Client;
import com.adithya.WhatsAppScheduler.exception.ResourceNotFoundException;
//AuthService to communicate with clientDao for authentication
@Service
public class AuthService {
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);
	@Autowired
	ClientDao clientDao;
	public Client validateToken(String token) throws ResourceNotFoundException {
        System.out.println("in validate token service");
        Client client = clientDao.getClientUsingToken(token);
        return client;
    }
	

}
