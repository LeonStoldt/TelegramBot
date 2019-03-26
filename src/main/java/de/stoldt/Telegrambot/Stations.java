package de.stoldt.Telegrambot;

public enum Stations {
    PINNEBERG("AP"),
    ITZEHOE("AIZ"),
    HAMBURG_HBF("AH"),
    HAMBURG_ALTONA("AA"),
    WRIST("AWST"),
    ELMSHORN("AEL");

    private String abbr;

    Stations(String abbr) {
        this.abbr = abbr;
    }

    public String abbr() {
        return abbr;
    }
}
