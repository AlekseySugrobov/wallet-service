package com.leovegas.walletservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.walletservice.domain.entities.Transaction;
import com.leovegas.walletservice.exceptions.TransactionIdAlreadyExists;
import com.leovegas.walletservice.exceptions.WalletNotFoundException;
import com.leovegas.walletservice.mappers.TransactionMapper;
import com.leovegas.walletservice.service.TransactionService;
import com.leovegas.walletservice.utils.PodamUtils;
import com.leovegas.walletservice.utils.TransactionGenerationUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test for Transaction controller")
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private TransactionMapper transactionMapper;
    @MockBean
    private TransactionService transactionService;

    @Test
    @DisplayName("Test for successfully handling transaction")
    void successfullyHandlingTransactionShouldReturn201StatusCodeTest() throws Exception {
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest();
        Transaction expectedTransaction = PodamUtils.manufacturePojo(Transaction.class);
        when(this.transactionService.handleTransaction(registerTransactionRequest))
                .thenReturn(expectedTransaction);

        this.mockMvc.perform(
                        post("/api/v1/transaction")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(this.objectMapper.writeValueAsString(registerTransactionRequest))
                )
                .andExpect(status().isCreated());

        verify(this.transactionService).handleTransaction(registerTransactionRequest);
        verify(this.transactionMapper).transactionToTransactionDto(expectedTransaction);
    }

    @Test
    @DisplayName("Test for handling transaction with not valid request")
    void handlingTransactionWithNotValidRequestShouldReturn400StatusCodeTest() throws Exception {
        RegisterTransactionRequest registerTransactionRequest = new RegisterTransactionRequest();

        this.mockMvc.perform(
                        post("/api/v1/transaction")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(this.objectMapper.writeValueAsString(registerTransactionRequest))
                )
                .andExpect(status().isBadRequest());

        verify(this.transactionService, never()).handleTransaction(any());
        verify(this.transactionMapper, never()).transactionToTransactionDto(any());
    }

    @Test
    @DisplayName("Test for handling transaction with not existing user id")
    void handlingTransactionWithNotExistingUserIdShouldReturn404StatusCodeTest() throws Exception {
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest();
        WalletNotFoundException walletNotFoundException = new WalletNotFoundException(123L);
        doThrow(walletNotFoundException).when(this.transactionService).handleTransaction(registerTransactionRequest);

        this.mockMvc.perform(
                        post("/api/v1/transaction")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(this.objectMapper.writeValueAsString(registerTransactionRequest))
                )
                .andExpect(status().isNotFound());

        verify(this.transactionService).handleTransaction(registerTransactionRequest);
        verify(this.transactionMapper, never()).transactionToTransactionDto(any());
    }

    @Test
    @DisplayName("Test for handling transaction with exiting transaction id")
    void handlingTransactionWithExistingTransactionIdShouldReturn409StatusCodeTest() throws Exception {
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest();
        TransactionIdAlreadyExists transactionIdAlreadyExists = new TransactionIdAlreadyExists("1234");
        doThrow(transactionIdAlreadyExists).when(this.transactionService).handleTransaction(registerTransactionRequest);

        this.mockMvc.perform(
                        post("/api/v1/transaction")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(this.objectMapper.writeValueAsString(registerTransactionRequest))
                )
                .andExpect(status().isConflict());

        verify(this.transactionService).handleTransaction(registerTransactionRequest);
        verify(this.transactionMapper, never()).transactionToTransactionDto(any());
    }

    @Test
    @DisplayName("Getting transactions by existing user id")
    void gettingTransactionByExistingUserIdShouldReturn200StatusCode() throws Exception {
        long userId = RandomUtils.nextLong();
        List<Transaction> transactions = PodamUtils.manufacturePojo(List.class, Transaction.class);
        when(this.transactionService.getTransactionsByUserId(userId)).thenReturn(transactions);

        this.mockMvc.perform(
                        get("/api/v1/transaction/{userId}", userId)
                )
                .andExpect(status().isOk());

        verify(this.transactionService).getTransactionsByUserId(userId);
        verify(this.transactionMapper).transactionListToTransactionDtoList(transactions);
    }

    @Test
    @DisplayName("Getting transactions by existing user id. Returns empty list")
    void gettingTransactionByExistingUserIdShouldReturn204StatusCode() throws Exception {
        long userId = RandomUtils.nextLong();
        when(this.transactionService.getTransactionsByUserId(userId)).thenReturn(Collections.emptyList());

        this.mockMvc.perform(
                        get("/api/v1/transaction/{userId}", userId)
                )
                .andExpect(status().isNoContent());

        verify(this.transactionService).getTransactionsByUserId(userId);
        verify(this.transactionMapper).transactionListToTransactionDtoList(any());
    }

    @Test
    @DisplayName("Getting transactions by not existing user id")
    void gettingTransactionsByNotExistingUserIdShouldReturn404StatusCode() throws Exception {
        long userId = RandomUtils.nextLong();
        doThrow(WalletNotFoundException.class).when(this.transactionService).getTransactionsByUserId(userId);

        this.mockMvc.perform(
                        get("/api/v1/transaction/{userId}", userId)
                )
                .andExpect(status().isNotFound());

        verify(this.transactionService).getTransactionsByUserId(userId);
        verify(this.transactionMapper, never()).transactionListToTransactionDtoList(any());
    }

}