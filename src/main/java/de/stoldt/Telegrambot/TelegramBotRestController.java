package de.stoldt.Telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramBotRestController {

    private final TelegramService telegramService;

    @Autowired
    public TelegramBotRestController(TelegramService telegramService) {
        this.telegramService = telegramService;
        telegramService.start();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public String getStatus() {
        return "TelegramBot is running.";
    }
}
