package de.stoldt.Telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegrambotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegrambotApplication.class, args);
        TelegramAPI bot = new TelegramAPI();
    }

}
