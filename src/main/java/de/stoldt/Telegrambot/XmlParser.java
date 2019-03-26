package de.stoldt.Telegrambot;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlParser {

    public static final String NORDBAHN_BASE_URL = "https://datnet-nbe.etc-consult.de/datnet-nbe/xml?bhf={station}&id_prod=DPN,Bus,DPN-G&format=xml&callback=?";
    public static final int ONE_HOUR_IN_MILLIS = 3600000;
    private DocumentBuilder builder;

    public XmlParser() throws ParserConfigurationException {
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    private String getUrl(String station) {
        return NORDBAHN_BASE_URL.replace("{station}", station);
    }

    public String readXml(String station) throws IOException, SAXException, ParseException {
        List<Train> trains = new ArrayList<>();
        Document document = builder.parse(getXmlFromUrl(station));
        document.getDocumentElement().normalize();
        NodeList childNodes = document.getDocumentElement().getChildNodes();
        String stationName = childNodes.item(0).getTextContent();
        String time = childNodes.item(1).getTextContent();
        for (int i = 2; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equals("abfahrt")) {
                NodeList details = childNodes.item(i).getChildNodes();
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                Date trainTime = parser.parse(details.item(0).getTextContent());
                Date timeNow = parser.parse(time);
                if (trainTime.compareTo(new Date(timeNow.getTime() + ONE_HOUR_IN_MILLIS)) <= 0) {
                    String trainLine = details.item(2).getTextContent();
                    Stations destination = Stations.valueOf(details.item(3).getTextContent().toUpperCase().replace(" ", "_").replace("-", "_"));
                    String forecast = details.item(4).getTextContent();
                    int forecastMin = Integer.valueOf(details.item(5).getTextContent());
                    int rail = Integer.valueOf(details.item(6).getTextContent());
                    boolean cancelled = Boolean.valueOf(details.item(7).getTextContent());
                    boolean shuttleService = Boolean.valueOf(details.item(8).getTextContent());
                    Train train = new Train(trainTime, trainLine, destination, forecast, forecastMin, rail, cancelled, shuttleService);
                    trains.add(train);
                }
            }
        }

        StringBuffer data = new StringBuffer();
        trains.forEach(train -> data.append(train.toString()));
        return "*Anfrage des Bahnhofs " + stationName + " um " + time + ":*\n" + data;
    }

    private InputStream getXmlFromUrl(String station) throws IOException {
        return new URL(getUrl(station)).openStream();
    }

}
