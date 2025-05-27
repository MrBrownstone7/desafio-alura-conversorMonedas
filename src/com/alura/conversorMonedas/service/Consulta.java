package com.alura.conversorMonedas.service;

import com.alura.conversorMonedas.model.Divisas;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Consulta {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private final static String API_KEY = "6dfaafe3d8adf113286015ba";
    private final static String API_URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public Double consultarAPI(String monedaOrigen, String monedaDestino, double monto){
        //La url de la consulta que le vamos a hacer a la API debe ser de la moneda de origen que ingreso el usuario
        //Asi luego solo debemos multiplicar la moneda de destino por el monto ingresado.
        URI url = URI.create(API_URL_BASE + API_KEY + "/latest/" + monedaOrigen);

        //Conexion a la API
        //Cliente
        HttpClient client = HttpClient.newHttpClient();
        //Solicitud
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .build();

        //Respuesta
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Error al consultar la API. Código de estado: " + response.statusCode());
                System.err.println("Cuerpo de la respuesta: " + response.body());
            }
        } catch (IOException e) {
            // Loguear el error y lanzar una excepción más específica
            throw  new RuntimeException("Error de IO al conectar con la API: " + e.getMessage());
        } catch (InterruptedException e) {

            throw new RuntimeException("La conexión con la API fue interrumpida: " + e.getMessage());
        }

        String json = response.body();
        //Deserializamos el json entregado por la API a formato Divisas
        Divisas respuesta = gson.fromJson(json, Divisas.class);

        //obtenemos el valor de la moneda de destino == igualada a la de una unidad de la moneda de origen, por ejemplo 1 dolar == 940CLP aprox.
        double tasaConversion = respuesta.conversion_rates().get(monedaDestino);
        //Retornamos el monto convertido.
        return monto * tasaConversion;
    }
}
