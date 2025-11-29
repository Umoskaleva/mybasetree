package com.mybasetree;

import com.mybasetree.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@SpringBootApplication // Главная аннотация

public class MyBaseTreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBaseTreeApplication.class, args); // Запуск
    }
}