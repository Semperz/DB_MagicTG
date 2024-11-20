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
        StringBuilder sb = new StringBuilder();
        sb.append(exportDir).append("/").append(fileName).append(".").append("json");
        String filePath = sb.toString();
        saveAsJson(filePath, data);
    }



    private static void saveAsJson(String filePath, String data) {
        try {
            String[] rows = data.split("\n");
            // creacion de la lista que formará el JSON
            List<Map<String, String>> cardList = new ArrayList<>();

            // recorrer las filas y columnas de para crear un mapa de cada carta
            for (String row : rows) {
                // Crear un mapa para una sola carta
                Map<String, String> card = new LinkedHashMap<>();

                // Dividir columnas correctamente por ", " sin perder datos
                String[] columns = row.split(", (?=\\w+:)");
                for (String column : columns) {
                    String[] keyValue = column.split(":", 2); // Dividir clave y valor con ":" como separador
                    card.put(keyValue[0].trim(), keyValue[1].trim()); // El trim() limpia espacios y devuelve el valor
                }
                if (!card.isEmpty()) {
                    cardList.add(card);
                }
            }

            // Crear el objeto padre "cards"
            Map<String, Object> resultJson = new HashMap<>();
            resultJson.put("cards", cardList);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), resultJson);
            System.out.println("Archivo JSON creado exitosamente: ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


