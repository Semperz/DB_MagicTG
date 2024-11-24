package edu.badpals.db_magictg.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataExporter {

    public static void exportData(String fileName, String data) {
        String exportDir = "exports";
        File dir = new File(exportDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        String filePath = exportDir + "/" + fileName + ".json";


        File file = new File(filePath);
        if (file.exists()) {

            updateJson(filePath, data);
        } else {
            // Si no existe, lo creamos
            saveAsJson(filePath, data);
        }
    }

    private static void saveAsJson(String filePath, String data) {
        try {
            String[] rows = data.split("\n");
            List<Map<String, String>> cardList = new ArrayList<>();

            for (String row : rows) {
                Map<String, String> card = new LinkedHashMap<>();
                String[] columns = row.split(", (?=\\w+:)");
                for (String column : columns) {
                    String[] keyValue = column.split(":", 2);
                    card.put(keyValue[0].trim(), keyValue[1].trim());
                }
                if (!card.isEmpty()) {
                    cardList.add(card);
                }
            }

            Map<String, Object> resultJson = new HashMap<>();
            resultJson.put("cards", cardList);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), resultJson);
            System.out.println("Archivo JSON creado exitosamente: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateJson(String filePath, String data) {
        try {
            String[] rows = data.split("\n");
            List<Map<String, String>> cardList = new ArrayList<>();

            for (String row : rows) {
                Map<String, String> card = new LinkedHashMap<>();
                String[] columns = row.split(", (?=\\w+:)");
                for (String column : columns) {
                    String[] keyValue = column.split(":", 2);
                    card.put(keyValue[0].trim(), keyValue[1].trim());
                }
                if (!card.isEmpty()) {
                    cardList.add(card);
                }
            }

            Map<String, Object> resultJson = new HashMap<>();
            resultJson.put("cards", cardList);

            ObjectMapper mapper = new ObjectMapper();
            // Leemos el archivo existente y lo actualizamos
            File file = new File(filePath);
            Map<String, Object> existingData = mapper.readValue(file, Map.class);

            // Suponiendo que queremos agregar más datos, unimos las listas existentes con las nuevas
            List<Map<String, String>> existingCards = (List<Map<String, String>>) existingData.get("cards");
            existingCards.addAll(cardList);
            resultJson.put("cards", existingCards);

            // Guardamos los datos actualizados en el mismo archivo
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, resultJson);
            System.out.println("Archivo JSON actualizado exitosamente: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
