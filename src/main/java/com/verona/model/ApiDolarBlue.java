package com.verona.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDolarBlue {

    public static double obtenerDolarBlue() {
        try {

            @SuppressWarnings("deprecation")
            URL url = new URL("https://dolarapi.com/v1/dolares/blue");

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
            int indexVenta = jsonResponse.indexOf("venta\":") + 7;
            int endIndexVenta = jsonResponse.indexOf(",", indexVenta);
            String ventaStr = jsonResponse.substring(indexVenta, endIndexVenta);
            double venta = Double.parseDouble(ventaStr);

            int indexCompra = jsonResponse.indexOf("compra\":") + 8;
            int endIndexCompra = jsonResponse.indexOf(",", indexCompra);
            String compraStr = jsonResponse.substring(indexCompra, endIndexCompra);
            double compra = Double.parseDouble(compraStr);

            double valorMedio = (venta + compra) / 2.0;

            return valorMedio;

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
