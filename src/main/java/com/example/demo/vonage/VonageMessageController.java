package com.example.demo.vonage;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class VonageMessageController {

    @PostMapping("/send-message")
    
    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        String apiKey = "fd48d033";
        String apiSecret = "w993lJT8tKqV0GzU";
        String url = "https://messages-sandbox.nexmo.com/v1/messages";

        String fromNumber = "14157386102";
        String toNumber = "212678556675";
        String jsonPayload = String.format(
                "{\"from\": \"%s\", \"to\": \"%s\", \"message_type\": \"text\", \"text\": \"%s\", \"channel\": \"whatsapp\"}",
                fromNumber, toNumber, request.getText());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((apiKey + ":" + apiSecret).getBytes()));

            StringEntity entity = new StringEntity(jsonPayload);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                return ResponseEntity.status(response.getStatusLine().getStatusCode()).body("Message sent successfully");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
        }
    }
}
