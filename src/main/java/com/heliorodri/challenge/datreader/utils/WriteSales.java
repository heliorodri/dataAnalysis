package com.heliorodri.challenge.datreader.utils;

import com.heliorodri.challenge.datreader.domain.Sale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteSales {
    private static final String salesFile = System.getProperty("user.dir") + "/src/main/java/com/heliorodri/challenge/datreader/database/sales.json";

    public static void save(Sale sale){
        JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();

        jsonArray = getSalesLog();

        json.put("saleId", sale.getSaleId());
        json.put("itemId", sale.getItemId());
        json.put("itemQuantity", sale.getItemQuantity());
        json.put("itemPrice", sale.getItemPrice());
        json.put("salesmenName", sale.getSalesmenName());

        try (FileWriter file = new FileWriter(salesFile)) {
            jsonArray.add(json);
            file.write(jsonArray.toJSONString());

            try {
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("could not write file");
        }
    }

    public static JSONArray getSalesLog(){
        JSONArray jsonArray = new JSONArray();
        try {
            File file = new File(salesFile);
            if (file.length() > 0){
                FileReader fileReader = new FileReader(salesFile);
                Object obj = new JSONParser().parse(fileReader);
                jsonArray = (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            System.out.println("could not read file");
        }

        return jsonArray;
    }
}
