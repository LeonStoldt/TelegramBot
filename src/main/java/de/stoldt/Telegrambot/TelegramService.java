package de.stoldt.Telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramService extends TelegramBot {

    private static final String TOKEN = "877330417:AAFuq3wdPE4byTA1EXTgo-N76rpZt7r81To";
    private final Features features;

    @Autowired
    public TelegramService(Features features) {
        super(TOKEN);
        this.features = features;
    }

    public void start() {
        setUpdatesListener(list -> {
            list.forEach(this::onUpdateReceived);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void onUpdateReceived(Update update) {
        if (update.message() != null && update.message().text() != null) {

            String messageText = update.message().text().toLowerCase();
            long chatId = update.message().chat().id();
            String name = update.message().chat().firstName();
            String message = features.getDefaultMessage();

            if (messageText.contains("/start")) {
                message = features.getStartMessage(name);
            } else if (messageText.contains("/hilfe")) {
                message = features.getHelpMessage();
            } else if (messageText.contains("/stop")) {
                message = features.getStopMessage();
            } else if (messageText.contains("nb ")) {
                message = features.getNordbahnMessage(messageText);
            }
            execute(new SendMessage(chatId, message).parseMode(ParseMode.Markdown));
        }
    }
}
