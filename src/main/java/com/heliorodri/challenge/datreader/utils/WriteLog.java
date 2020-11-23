package com.heliorodri.challenge.datreader.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class WriteLog {
    private static final String logFile =
            System.getProperty("user.dir") +
            "/src/main/java/com/heliorodri/challenge/datreader/database/log.json";

    public static void log(Exception exception, Class errorClass, String method){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();

        json.put("message", exception.getMessage());
        json.put("class", errorClass.getName());
        json.put("method", method);
        json.put("date", Date.from(Instant.now()).toString());

        try {
            FileReader fileReader = new FileReader(logFile);
            Object obj = new JSONParser().parse(fileReader);
            jsonArray = (JSONArray) obj;
        } catch (IOException | ParseException e) {
            System.out.println("couldn't log");
        }

        try (FileWriter file = new FileWriter(logFile)) {
            jsonArray.add(json);
            file.write(jsonArray.toJSONString());

            try {
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("couldn't log");
        }
    }
}
