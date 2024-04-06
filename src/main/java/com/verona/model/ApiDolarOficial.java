package com.verona.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDolarOficial {

    public static double obtenerDolarOficial() {
        try {

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

            String jsonResponse = response.toString();
            int index = jsonResponse.indexOf("venta\":") + 7;
            int endIndex = jsonResponse.indexOf(",", index);
            String ventaStr = jsonResponse.substring(index, endIndex);
            double venta = Double.parseDouble(ventaStr);

            return venta;

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
