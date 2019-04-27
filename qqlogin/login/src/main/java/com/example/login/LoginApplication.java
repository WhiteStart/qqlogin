package com.example.login;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.stereotype.Controller;


@SpringBootApplication
public class LoginApplication{

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return  application.sources(SpringApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

}
