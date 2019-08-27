package de.stoldt.Telegrambot;

import org.springframework.stereotype.Component;

@Component
public class Features {

    public String getDefaultMessage() {
        return "Tut mir leid, darauf habe ich aktuell noch keine Antwort. :pensive:";
    }

    public String getStartMessage(String name) {
        return "Moin " + name + ",\nEs freut mich sehr, dass du meine Funktionen in Anspruch nehmen möchtest. Mithilfe des Kommandos '/hilfe' erzähle ich dir alles über meine bisherigen Funktionen. Ich wünsche dir viel Spaß und hoffe, du schreibst mir schon bald. :blush:";
    }

    public String getHelpMessage() {
        return "/start - Beim Bot registrieren/anmelden\n" +
                "/stop - Beim Bot abmelden\n" +
                "/delete - Gespeicherte Daten löschen lassen\n" +
                "/nb {Bahnhof} - Erhalte die Abfahrtszeiten der Nordbahn Züge innerhalb der nächsten Stunde";
    }

    public String getStopMessage() {
        return "Tut mir leid, dass du meine Funktionen nicht weiter nutzen möchtest. Ich lass dich erstmal in Ruhe, bis du wieder '/start' schreibst. :wink:\n" +
                "Solltest du deine Daten löschen lassen wollen, schreibe einfach '/delete' und ich lösche deine Daten.";
    }

    public String getDeleteMessage() {
        return "Deine Daten wurden erfolgreich gelöscht.";
    }

    public String getInformationMessage() {
        return "Diese Daten habe ich über dich gespeichert:";
    }

    public String getNordbahnMessage(String messageText) {
        String message = "Tut mir leid. Ich konnte den angegebenen Bahnhof leider nicht finden. Stelle sicher, dass er existiert und richtig geschrieben ist und versuche es erneut.";
        String[] words = messageText.split("\\s+");
        int position = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("nb")) {
                position = i;
            }
        }
        Stations destination;

        String searchString = "";
        int maxPosition = (position + 5 > words.length) ? words.length : position + 5;
        for (int j = position + 1; j < maxPosition; j++) {
            try {
                if (!(j == position + 1)) {
                    searchString = searchString + "_" + words[j].toUpperCase();
                } else {
                    searchString = words[j].toUpperCase();
                }
                destination = Stations.valueOf(searchString);
                message = getNordbahnInformation(destination);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    private String getNordbahnInformation(Stations station) {
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
