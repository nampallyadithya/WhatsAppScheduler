package com.adithya.WhatsAppScheduler;

import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.adithya.WhatsAppScheduler.timertask.CustomTimerTask;

@SpringBootApplication
@ComponentScan({"com.*"})
@EnableScheduling
public class WhatsAppSchedulerApplication {
	
	@Autowired
	public Timer timer;
	
	@Autowired
	public CustomTimerTask task;
	
	

	public static void main(String[] args) {
		SpringApplication.run(WhatsAppSchedulerApplication.class, args);
		
	}
	@EventListener(ApplicationReadyEvent.class)
    public void startScheduler()
    {
        //run a task after every 30 seconds....
        timer.schedule(task,1000,30000);

    }

}
