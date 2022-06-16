package com.leovegas.walletservice.controllers;

import com.leovegas.api.PlayerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController implements PlayerApi {
    @Override
    public ResponseEntity<Void> apiV1PlayerIdGet(Integer id) {
        return ResponseEntity.ok().build();
    }
}
