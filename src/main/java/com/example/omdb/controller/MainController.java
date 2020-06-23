package com.example.omdb.controller;

import com.example.omdb.entity.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class MainController {

    private static final String API_KEY = "b5c85bc6";

    @GetMapping("/movies/{id}")
    public String test(@PathVariable String id, Model out) {

        String url = "http://www.omdbapi.com/";
        WebClient webClient = WebClient.create(url);

        Mono<Movie> call = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("i", id)
                        .build())
                .retrieve()
                .bodyToMono(Movie.class);
        Movie movie = call.block();
        out.addAttribute("movie", movie);

        return "movie";
    }
}
