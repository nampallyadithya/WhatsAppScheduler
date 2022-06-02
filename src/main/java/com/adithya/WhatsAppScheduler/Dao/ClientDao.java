package com.adithya.WhatsAppScheduler.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adithya.WhatsAppScheduler.entities.Client;
import com.adithya.WhatsAppScheduler.exception.ResourceNotFoundException;
import com.adithya.WhatsAppScheduler.rowmappers.ClientMapper;

@Repository
public class ClientDao {
	Logger logger = LoggerFactory.getLogger(ClientDao.class);
	
	
	@Autowired
	JdbcTemplate jdbc;
	public Client getClientUsingToken(String token) throws ResourceNotFoundException {
		
		String query = "select * from client_details where auth_token= ?";
        Client client = null;
        try {
        	
        	//System.out.println(jdbc.queryForList(query));
            client = jdbc.queryForObject(query, new ClientMapper(), token);
        	System.out.println("query--> " + query);
            System.out.println("query result--> " + client.toString());
            return client;
            //return (Client) jdbc.query(query,new ClientMapper(),token);
        }     catch (Exception e) {
            logger.warn(e.getMessage());
            //throw new SQLErrorException("SQL error while validating client using token");
            return null;
        }
        
        
        
		
	}
	

}
