package com.verona.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDolarOficial {

    public static double obtenerDolarOficial() {
        try {
            // URL de la API
            @SuppressWarnings("deprecation")
            URL url = new URL("https://dolarapi.com/v1/dolares/oficial");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            con.disconnect();

            // Analizar la respuesta manualmente
            String jsonResponse = response.toString();
            int index = jsonResponse.indexOf("venta\":") + 7; // Buscar el índice del valor de venta
            int endIndex = jsonResponse.indexOf(",", index); // Buscar el índice del final del número
            String ventaStr = jsonResponse.substring(index, endIndex); // Obtener el número como una cadena
            double venta = Double.parseDouble(ventaStr); // Convertir la cadena a double

            return venta;

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
