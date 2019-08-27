package de.stoldt.Telegrambot;

public enum Stations {
    HAMBURG_HBF("AH"), HBF("AH"),
    HAMBURG_DAMMTOR("ADF"), DAMMTOR("ADF"),
    HAMBURG_ALTONA("AA"), ALTONA("AA"),
    PINNEBERG("AP"), PB("AP"),
    PRISDORF("APD"),
    TORNESCH("ATM"),
    ELMSHORN("AEL"),
    HERZHORN("AHZH"),
    GLÜCKSTADT("AGST"), GLUECKSTADT("AGST"),
    KREMPE("AKM"),
    KREMPERHEIDE("AKHD"),
    ITZEHOE("AIZ"),
    HORST("AHOT"),
    DAUENHOF("ADH"),
    WRIST("AWST");

    private String abbr;

    Stations(String abbr) {
        this.abbr = abbr;
    }

    public String abbr() {
        return abbr;
    }
}
