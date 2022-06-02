package com.adithya.WhatsAppScheduler.Interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.adithya.WhatsAppScheduler.entities.Client;
import com.adithya.WhatsAppScheduler.entities.Response;
import com.adithya.WhatsAppScheduler.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	AuthService authService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
       System.out.println("In prehandler interceptor");
        String token = request.getHeader("token");
        System.out.println("token as header " + token);
        Client client = authService.validateToken(token);
        
        if (client == null) {
        	 System.out.println("client is NULL.....authentication failed.");
            response.setContentType("application/json");
            response.setStatus(400);
            PrintWriter out = response.getWriter();
            Response resp = new Response(500, "Authentication failed");
            String responseString = new ObjectMapper().writeValueAsString(resp);
            out.print(responseString);
            return false;
        }
        System.out.println("client here in interceptor " + client);
        request.setAttribute("client", client);
        return true;
    }
	

}
