package com.example.omdb.controller;

import com.example.omdb.entity.Rating;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    private static final String API_KEY = "b5c85bc6";

    @GetMapping("/movies/{id}")
    @ResponseBody
    public List<Rating> test(@PathVariable String id) {

        String url = "http://www.omdbapi.com/";
        WebClient webClient = WebClient.create(url);

        Mono<String> call = webClient.get()
                .uri(uriBuilder-> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("i", id)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        String response = call.block();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);

            Rating[] ratings = objectMapper.convertValue(root.get("Ratings"), Rating[].class);
            return new ArrayList<>(Arrays.asList(ratings));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
