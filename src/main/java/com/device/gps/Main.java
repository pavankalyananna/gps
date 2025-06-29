package com.device.gps;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Random;

public class Main {
    private static final String SERVER_URL = "http://localhost:8080";
    private static final String DEVICE_ID = "DEVICE-001"; // Unique per device
    private static final int INTERVAL_SECONDS = 30;

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        Random random = new Random();
        
        while (true) {
            try {
                // Generate GPS data (replace with real GPS module integration)
                double latitude = 40.7128 + (random.nextDouble() - 0.5) * 0.01;
                double longitude = -74.0060 + (random.nextDouble() - 0.5) * 0.01;
                
                // Create JSON payload
                String json = String.format(
                    "{\"deviceId\":\"%s\",\"latitude\":%f,\"longitude\":%f}",
                    DEVICE_ID, latitude, longitude
                );
                
                // Send POST request
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();
                
                HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
                );
                
                System.out.printf("Sent location: %.6f, %.6f | Status: %d%n",
                    latitude, longitude, response.statusCode());
                
                // Wait for next transmission
                Thread.sleep(INTERVAL_SECONDS * 1000);
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                try {
                    Thread.sleep(5000); // Wait before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
