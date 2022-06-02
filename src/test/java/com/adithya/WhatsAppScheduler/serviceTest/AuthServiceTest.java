package com.adithya.WhatsAppScheduler.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.adithya.WhatsAppScheduler.Dao.ClientDao;
import com.adithya.WhatsAppScheduler.entities.Client;
import com.adithya.WhatsAppScheduler.exception.ResourceNotFoundException;
import com.adithya.WhatsAppScheduler.service.AuthService;

@SpringBootTest
public class AuthServiceTest {
	@Autowired
    AuthService authService;


    @MockBean
    ClientDao clientDao;


    @Test
    void validateToken() throws ResourceNotFoundException {
        Client testClient = new Client(10125, "dummy", "dummytoken");
        when(clientDao.getClientUsingToken("dummytoken")).thenReturn(testClient);
        assertThat(authService.validateToken("dummytoken")).isEqualTo(testClient);
    }


    @Test
    void validateTokenAsInvalid() throws ResourceNotFoundException {
        when(clientDao.getClientUsingToken("Invalid Token")).thenReturn(null);
        assertThat(authService.validateToken("Invalid Token")).isNull();
    }
}
