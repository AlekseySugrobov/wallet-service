package com.leovegas.walletservice.controllers;

import com.leovegas.api.WalletApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController implements WalletApi {
    @Override
    public ResponseEntity<Void> apiV1WalletIdGet(Integer id) {
        return ResponseEntity.ok().build();
    }
}
