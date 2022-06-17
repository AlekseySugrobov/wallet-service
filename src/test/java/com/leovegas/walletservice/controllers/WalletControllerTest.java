package com.leovegas.walletservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.model.WalletFilter;
import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.exceptions.WalletNotFoundException;
import com.leovegas.walletservice.exceptions.WalletUniqueConstraintViolationException;
import com.leovegas.walletservice.mappers.WalletMapper;
import com.leovegas.walletservice.service.WalletService;
import com.leovegas.walletservice.utils.PodamUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests for wallet controller")
class WalletControllerTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WalletService walletService;
    @SpyBean
    private WalletMapper walletMapper;


    @Test
    @DisplayName("Test for searching wallets")
    void apiV1WalletSearchPost() throws Exception {
        WalletFilter filter = PodamUtils.manufacturePojo(WalletFilter.class);
        List<Wallet> expectedWallets = PodamUtils.manufacturePojo(List.class, Wallet.class);
        when(this.walletService.search(filter)).thenReturn(expectedWallets);

        this.mockMvc.perform(
                        post("/api/v1/wallet/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(filter))
                )
                .andExpect(status().isOk());

        verify(this.walletService).search(filter);
        verify(this.walletMapper).walletListToWalletDTOList(expectedWallets);
    }

    @Test
    @DisplayName("Test for searching wallets. Returned empty coolection")
    void apiV1WalletSearchPostReturnedEmptyCollection() throws Exception {
        WalletFilter filter = PodamUtils.manufacturePojo(WalletFilter.class);
        List<Wallet> expectedWallets = Collections.emptyList();
        when(this.walletService.search(filter)).thenReturn(expectedWallets);

        this.mockMvc.perform(
                        post("/api/v1/wallet/_search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsString(filter))
                )
                .andExpect(status().isNoContent());

        verify(this.walletService).search(filter);
        verify(this.walletMapper).walletListToWalletDTOList(expectedWallets);
    }

    @Test
    @DisplayName("Test for deleting existing wallet")
    void apiV1WalletUserIdDelete() throws Exception {
        this.mockMvc.perform(
                        delete("/api/v1/wallet/{userId}", USER_ID)
                )
                .andExpect(status().isOk());

        verify(this.walletService).delete(USER_ID);
    }

    @Test
    @DisplayName("Test for deleting not existing wallet")
    void apiV1WalletUserIdDeleteNotExistingWallet() throws Exception {
        WalletNotFoundException expectedException = new WalletNotFoundException(USER_ID);
        doThrow(expectedException).when(this.walletService).delete(USER_ID);

        this.mockMvc.perform(
                        delete("/api/v1/wallet/{userId}", USER_ID)
                )
                .andExpect(status().isNotFound());

        verify(this.walletService).delete(USER_ID);
    }

    @Test
    @DisplayName("Test getting existing wallet by user id")
    void apiV1WalletUserIdGet() throws Exception {
        Wallet expectedWallet = PodamUtils.manufacturePojo(Wallet.class);
        when(this.walletService.get(USER_ID)).thenReturn(expectedWallet);

        this.mockMvc.perform(
                        get("/api/v1/wallet/{userId}", USER_ID)
                )
                .andExpect(status().isOk());

        verify(this.walletService).get(USER_ID);
        verify(this.walletMapper).walletToWalletDTO(expectedWallet);
    }

    @Test
    @DisplayName("Test getting not existing wallet by user id")
    void apiV1WalletUserIdGetNotExistingWallet() throws Exception {
        WalletNotFoundException expectedException = new WalletNotFoundException(USER_ID);
        doThrow(expectedException).when(this.walletService).get(USER_ID);

        this.mockMvc.perform(
                        get("/api/v1/wallet/{userId}", USER_ID)
                )
                .andExpect(status().isNotFound());

        verify(this.walletService).get(USER_ID);
        verify(this.walletMapper, never()).walletToWalletDTO(any());
    }

    @Test
    @DisplayName("Test creating wallet for new user")
    void apiV1WalletUserIdPost() throws Exception {
        Wallet expectedWallet = PodamUtils.manufacturePojo(Wallet.class);
        when(this.walletService.create(USER_ID)).thenReturn(expectedWallet);

        this.mockMvc.perform(
                        post("/api/v1/wallet/{userId}", USER_ID)
                )
                .andExpect(status().isCreated());

        verify(this.walletService).create(USER_ID);
        verify(this.walletMapper).walletToWalletDTO(expectedWallet);
    }

    @Test
    @DisplayName("Test creating wallet for existing user")
    void apiV1WalletUserIdPostForExistingUser() throws Exception {
        WalletUniqueConstraintViolationException expectedException =
                new WalletUniqueConstraintViolationException(USER_ID);
        doThrow(expectedException).when(this.walletService).create(USER_ID);

        this.mockMvc.perform(
                        post("/api/v1/wallet/{userId}", USER_ID)
                )
                .andExpect(status().isConflict());

        verify(this.walletService).create(USER_ID);
        verify(this.walletMapper, never()).walletToWalletDTO(any());
    }

    @Test
    @DisplayName("Getting balance for existing user")
    void apiV1WalletUserIdBalanceGet() throws Exception {
        BigDecimal expectedBalance = BigDecimal.valueOf(2.2);
        when(this.walletService.getBalanceByUserId(USER_ID)).thenReturn(expectedBalance);

        this.mockMvc.perform(
                        get("/api/v1/wallet/{userId}/balance", USER_ID)
                )
                .andExpect(status().isOk());

        verify(this.walletService).getBalanceByUserId(USER_ID);
    }

    @Test
    @DisplayName("Getting balance for not existing user")
    void apiV1WalletUserIdBalanceGetForNotExistingUser() throws Exception {
        WalletNotFoundException expectedException = new WalletNotFoundException(USER_ID);
        doThrow(expectedException).when(this.walletService).getBalanceByUserId(USER_ID);

        this.mockMvc.perform(
                        get("/api/v1/wallet/{userId}/balance", USER_ID)
                )
                .andExpect(status().isNotFound());

        verify(this.walletService).getBalanceByUserId(USER_ID);
    }
}