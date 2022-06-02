package com.adithya.WhatsAppScheduler.DaoTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adithya.WhatsAppScheduler.Dao.ClientDao;
import com.adithya.WhatsAppScheduler.entities.Client;
import com.adithya.WhatsAppScheduler.exception.ResourceNotFoundException;

@SpringBootTest
public class ClientDaoTest {
	@Autowired
    ClientDao clientDao;


    Logger logger = LoggerFactory.getLogger(ClientDaoTest.class);

	
	 @Test void getClientUsingToken() throws ResourceNotFoundException
	 {
		 String token = "testing";
		 Client expectedClient = new Client(1, "sunny", "testing");
	  Client actualResult = clientDao.getClientUsingToken(token);
	  assertThat(actualResult.toString()).isEqualTo(expectedClient.toString()); }
	 


    @Test
    void getClientUsingTokenInvalid() {
        Client actualResult = null;
        try {
            actualResult = clientDao.getClientUsingToken("Invalid token");
            System.out.println("actualresult " + actualResult);
        } catch (ResourceNotFoundException e) {
            logger.info(e.getMessage());
            assertThat(actualResult).isEqualTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
