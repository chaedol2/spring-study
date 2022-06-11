package com.chs.myhome.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/stepn")
public class StepnController {

    private final Logger LOGGER = LoggerFactory.getLogger(StepnController.class);

//    @GetMapping
//    public String calculator(){
//        LOGGER.info("calculator 출력!");
//
//        return "stepn/calculator";
//    }

    @GetMapping
    public String calculator() throws URISyntaxException, JSONException {
//
//        String url = "https://api.coingecko.com/api/v3/simple/price?ids=green-satoshi-token%2Csolana%2Cstepn&vs_currencies=krw%2Cusd"; // api url
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = new URI(url);
//        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//        JSONObject json = new JSONObject(result.getBody());
//        System.out.println(json.get("green-satoshi-token"));

        return "stepn/calculator";
    }

}


