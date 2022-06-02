package com.adithya.WhatsAppScheduler.timertask;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adithya.WhatsAppScheduler.entities.Message;
import com.adithya.WhatsAppScheduler.exception.ResourceNotFoundException;
import com.adithya.WhatsAppScheduler.service.MessageService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Component
public class CustomTimerTask extends TimerTask{
	@Autowired
    MessageService messageService;
	
	public static String encodeParam(String data) {
		String result = "";
		try {
			result = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static byte[] getParamsByte(Map<String, Object> params) {
		byte[] result = null;
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}
			postData.append(encodeParam(param.getKey()));
			postData.append('=');
			postData.append(encodeParam(String.valueOf(param.getValue())));
		}
		try {
			result = postData.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	

	@Override
	public void run() {
System.out.println("Timer Task is called every half minute");
		
		List<Message> messageList = null;
		/*
		 * if (messageList.isEmpty()) { System.out.println("messageList is empty");
		 * return; }
		 */
		 try {
	            messageList = messageService.pollMessagesFromDatabase();
	        } catch (ResourceNotFoundException e) {
	            System.out.println(e.getMessage());
	            return;
	        }

	        if (messageList.isEmpty()) {
	            System.out.println("messagelist is empty");
	            return;
	        }
	        System.out.println("The following message will be send on specified time");
			Gson gson = new Gson();
			URL url = null;
			HttpURLConnection con = null;

			//Iterate over messageList and send it to the destination using Gupshup Whatsapp API..
			for (Message ms : messageList) {
				System.out.println("Running for message_id- " + ms.getMessage_id());
				try {
					url = new URL("https://api.gupshup.io/sm/api/v1/msg");
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setUseCaches(false);
					con.setDoOutput(true);
					con.setDoInput(true);

					//set header to the request.............
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					con.setRequestProperty("apikey", "yfqgnr8cg1xilwnq2o6keltmjhbr0kbp");
					con.setRequestProperty("Accept", "application/json");

					OutputStream outputStream = con.getOutputStream();

					//form the message{type:"{type of the message want to send}" ,text:"{message to send}"}
					HashMap<String, String> message = new HashMap<String, String>();
					message.put("type", "text");
					message.put("text", ms.getMessage());

					
					String jsonString = gson.toJson(message);
					JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
					//form request body....
					Map<String, Object> body = new HashMap<>();
					body.put("channel", "whatsapp");
					body.put("source", "917834811114");
					body.put("destination", "91"+ ms.getDestination_phone_number());
					body.put("message", jsonObject);
					body.put("src.name", "AdithyaWhatsapp ");

					//write body to the stream
					outputStream.write(getParamsByte(body));
					System.out.println("outputstream here... " + outputStream.toString());
					System.out.println(" response code here--> " + con.getResponseCode());

					if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
						ObjectMapper objectMapper = new ObjectMapper();
						Map<String, String> response = objectMapper.readValue(con.getInputStream(), Map.class);
						System.out.println("MessageID is -->  " + response.get("messageId"));
						System.out.println(response.toString());
						int result = messageService.updateMessageStatus(false,true, response.get("messageId"),LocalDateTime.now(),ms.getMessage_id());
						if(result <1){
							System.out.println("Error occured while updating status....");
						}else System.out.println("Status of meesages is updated--> "+ result);
					} else {
						//mark submitted_status as failed
						int result = messageService.updateMessageStatus(false,false, null,null,ms.getMessage_id());
						System.out.println("Message sending failed for mesageID " + ms.getMessage_id());
					}
				} catch (Exception e) {
					//mark submitted_status as failed
					System.out.println("exception occured during sending messages through gupshup API");
					e.printStackTrace();
				}
			}
			
		}  
	}
	


