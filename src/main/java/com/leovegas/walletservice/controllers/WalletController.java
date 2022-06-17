package com.leovegas.walletservice.controllers;

import com.leovegas.api.WalletApi;
import com.leovegas.model.BalanceResponse;
import com.leovegas.model.WalletDTO;
import com.leovegas.model.WalletFilter;
import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.mappers.WalletMapper;
import com.leovegas.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WalletController implements WalletApi {

    private final WalletMapper walletMapper;
    private final WalletService walletService;

    @Override
    public ResponseEntity<List<WalletDTO>> apiV1WalletSearchPost(WalletFilter filter) {
        List<Wallet> wallets = this.walletService.search(filter);
        List<WalletDTO> walletDTOList = this.walletMapper.walletListToWalletDTOList(wallets);
        HttpStatus status = wallets.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(status).body(walletDTOList);
    }

    @Override
    public ResponseEntity<Void> apiV1WalletUserIdDelete(Long userId) {
        this.walletService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<WalletDTO> apiV1WalletUserIdGet(Long userId) {
        Wallet wallet = this.walletService.get(userId);
        WalletDTO walletResponse = this.walletMapper.walletToWalletResponse(wallet);
        return ResponseEntity.ok(walletResponse);
    }

    @Override
    public ResponseEntity<WalletDTO> apiV1WalletUserIdPost(Long userId) {
        Wallet wallet = this.walletService.create(userId);
        WalletDTO walletResponse = this.walletMapper.walletToWalletResponse(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(walletResponse);
    }

    @Override
    public ResponseEntity<BalanceResponse> apiV1WalletUserIdBalanceGet(Long userId) {
        BigDecimal balance = this.walletService.getBalanceByUserId(userId);
        return ResponseEntity.ok(new BalanceResponse().balance(balance).userId(userId));
    }
}
