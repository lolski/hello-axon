package com.lolski;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main( String[] args )
	{
		SpringApplication.run(HelloAxonApplication.class, args);

		System.out.println("hello -- ");
	}
}


