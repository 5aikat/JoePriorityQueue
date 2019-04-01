package com.saikat.JoePriorityQueue.JoePriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@SpringBootApplication
public class JoePriorityQueueApplication {

	public static void main(String[] args) {

		SpringApplication.run(JoePriorityQueueApplication.class, args);
	}
}
