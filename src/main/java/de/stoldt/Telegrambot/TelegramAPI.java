package de.stoldt.Telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class TelegramAPI extends TelegramBot {

    private static final String TOKEN = "877330417:AAFuq3wdPE4byTA1EXTgo-N76rpZt7r81To";
    private static final int CHAT_ID = 742068445;


    public TelegramAPI() {
        super(TOKEN);
        setUpdatesListener(list -> {
            list.forEach(this::onUpdateReceived);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void onUpdateReceived(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String messageText = update.message().text();
            long chatId = update.message().chat().id();
            String name = update.message().chat().firstName();
            String message = "Tut mir leid, darauf habe ich aktuell noch keine Antwort. :pensive:";

            if (messageText.contains("/start")) {
                message = "Moin " + name + ",\nEs freut mich sehr, dass du meine Funktionen in Anspruch nehmen möchtest. Mithilfe des Kommandos '/hilfe' erzähle ich dir alles über meine bisherigen Funktionen. Ich wünsche dir viel Spaß und hoffe, du schreibst mir schon bald. :blush:";
            } else if (messageText.contains("/hilfe")) {
                message =
                        "start - subscribe bot\n" +
                        "stop - unsubscribe bot\n" +
                        "nb - get departure time for nordbahn trains";
            } else if (messageText.contains("/stop")) {
                message = "Tut mir leid, dass du meine Funktionen nicht weiter nutzen möchtest. Ich lass dich erstmal in Ruhe, bis du wireder '/start' schreibst. :wink:";
            } else if (messageText.contains("nb ")) {
                String[] words = messageText.split("\\s+");
                int position = 0;
                for (int i = 0; i < words.length; i++) if (words[i].equals("nb")) position = i;
                Stations destination;
                String firstWord = words[position + 1];
                String secondWord = words[position + 2];
                try {
                    destination = Stations.valueOf(firstWord.toUpperCase());
                    message = test(destination);
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        destination = Stations.valueOf(firstWord.toUpperCase() + "_" + secondWord.toUpperCase());
                        message = test(destination);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        message = "Leider konnte der Bahnhof " + firstWord + " oder " + firstWord + " " + secondWord + " nicht gefunden werden.";
                    }
                }
            }
            execute(new SendMessage(chatId, message).parseMode(ParseMode.Markdown));
        }
    }

    public String test(Stations station) {
        String data = "";
        try {
            XmlParser parser = new XmlParser();
            data = parser.readXml(station.abbr());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
