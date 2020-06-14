package com.iliushchenko.bitcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BitcoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinApplication.class, args);
	}

	@EventListener(ContextRefreshedEvent.class)
	public void doSomethingAfterStartup() {
		System.out.println("hello world, I have just started up");
	}

}
