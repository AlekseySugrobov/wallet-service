package com.leovegas.walletservice.service.impl;

import com.leovegas.model.RegisterTransactionRequest;
import com.leovegas.walletservice.domain.entities.Transaction;
import com.leovegas.walletservice.domain.entities.TransactionStatus;
import com.leovegas.walletservice.domain.entities.TransactionType;
import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.exceptions.TransactionIdAlreadyExists;
import com.leovegas.walletservice.exceptions.WalletNotFoundException;
import com.leovegas.walletservice.repositories.WalletRepository;
import com.leovegas.walletservice.service.TransactionService;
import com.leovegas.walletservice.service.WalletService;
import com.leovegas.walletservice.utils.TransactionGenerationUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@Rollback
@Transactional
@SpringBootTest
@DisplayName("Tests for TransactionService")
class TransactionServiceImplTest {

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = 1_000_000L;
    private static final String NOT_EXISTING_TRANSACTION_ID = "qwerty12345";
    private static final String EXISITNG_TRANSACTION_ID = "asd123";

    @Autowired
    private TransactionService transactionService;
    @SpyBean
    private WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Test for handling DEBIT transaction with SUCCESS status")
    void handleDebitTransactionShouldHandleSuccessfullyTest() {
        BigDecimal amount = BigDecimal.valueOf(100.0);
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest(amount, TransactionType.DEBIT,
                        EXISTING_USER_ID, NOT_EXISTING_TRANSACTION_ID);
        Wallet wallet = this.walletRepository.findByUserId(EXISTING_USER_ID).get();
        BigDecimal initialBalance = wallet.getBalance();

        Transaction registeredTransaction =
                this.transactionService.handleTransaction(registerTransactionRequest);

        Wallet updatedWallet = this.walletRepository.findByUserId(EXISTING_USER_ID).get();
        SoftAssertions handlingTransactionAssertions = new SoftAssertions();
        handlingTransactionAssertions.assertThat(registeredTransaction.getWallet()).isEqualTo(wallet);
        handlingTransactionAssertions.assertThat(registeredTransaction.getTransactionId())
                .isEqualTo(NOT_EXISTING_TRANSACTION_ID);
        handlingTransactionAssertions.assertThat(registeredTransaction.getType())
                .isEqualTo(TransactionType.DEBIT);
        handlingTransactionAssertions.assertThat(registeredTransaction.getAmount())
                .isEqualTo(amount);
        handlingTransactionAssertions.assertThat(registeredTransaction.getStatus())
                .isEqualTo(TransactionStatus.SUCCESS);
        handlingTransactionAssertions.assertThat(updatedWallet.getBalance())
                .isEqualTo(initialBalance.add(registerTransactionRequest.getAmount()));
        handlingTransactionAssertions.assertAll();

        verify(this.walletService).get(EXISTING_USER_ID);
        verify(this.walletService).proceedTransaction(wallet, amount, TransactionType.DEBIT);
    }

    @Test
    @DisplayName("Test for handling CREDIT transaction with SUCCESS status")
    void handleCreditTransactionShouldHandleSuccessfullyTest() {
        BigDecimal amount = BigDecimal.valueOf(100.0);
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest(amount, TransactionType.CREDIT,
                        EXISTING_USER_ID, NOT_EXISTING_TRANSACTION_ID);
        Wallet wallet = this.walletRepository.findByUserId(EXISTING_USER_ID).get();
        BigDecimal initialBalance = wallet.getBalance();

        Transaction registeredTransaction =
                this.transactionService.handleTransaction(registerTransactionRequest);

        Wallet updatedWallet = this.walletRepository.findByUserId(EXISTING_USER_ID).get();
        SoftAssertions handlingTransactionAssertions = new SoftAssertions();
        handlingTransactionAssertions.assertThat(registeredTransaction.getWallet()).isEqualTo(wallet);
        handlingTransactionAssertions.assertThat(registeredTransaction.getTransactionId())
                .isEqualTo(NOT_EXISTING_TRANSACTION_ID);
        handlingTransactionAssertions.assertThat(registeredTransaction.getType())
                .isEqualTo(TransactionType.CREDIT);
        handlingTransactionAssertions.assertThat(registeredTransaction.getAmount())
                .isEqualTo(amount);
        handlingTransactionAssertions.assertThat(registeredTransaction.getStatus())
                .isEqualTo(TransactionStatus.SUCCESS);
        handlingTransactionAssertions.assertThat(updatedWallet.getBalance())
                .isEqualTo(initialBalance.subtract(registerTransactionRequest.getAmount()));
        handlingTransactionAssertions.assertAll();

        verify(this.walletService).get(EXISTING_USER_ID);
        verify(this.walletService).proceedTransaction(wallet, amount, TransactionType.CREDIT);
    }

    @Test
    @DisplayName("Test for handling CREDIT transaction with FAILED status")
    void handleCreditTransactionShouldFailedTest() {
        BigDecimal amount = BigDecimal.valueOf(1_000_000.0);
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest(amount, TransactionType.CREDIT,
                        EXISTING_USER_ID, NOT_EXISTING_TRANSACTION_ID);
        Wallet wallet = this.walletRepository.findByUserId(EXISTING_USER_ID).get();
        BigDecimal initialBalance = wallet.getBalance();

        Transaction registeredTransaction =
                this.transactionService.handleTransaction(registerTransactionRequest);

        Wallet updatedWallet = this.walletRepository.findByUserId(EXISTING_USER_ID).get();
        SoftAssertions handlingTransactionAssertions = new SoftAssertions();
        handlingTransactionAssertions.assertThat(registeredTransaction.getWallet()).isEqualTo(wallet);
        handlingTransactionAssertions.assertThat(registeredTransaction.getTransactionId())
                .isEqualTo(NOT_EXISTING_TRANSACTION_ID);
        handlingTransactionAssertions.assertThat(registeredTransaction.getType())
                .isEqualTo(TransactionType.CREDIT);
        handlingTransactionAssertions.assertThat(registeredTransaction.getAmount())
                .isEqualTo(amount);
        handlingTransactionAssertions.assertThat(registeredTransaction.getStatus())
                .isEqualTo(TransactionStatus.FAILED);
        handlingTransactionAssertions.assertThat(registeredTransaction.getComment())
                .isEqualTo("Unable credit 1000000.0 from user id 1 wallet. Insufficient funds");
        handlingTransactionAssertions.assertThat(updatedWallet.getBalance())
                .isEqualTo(initialBalance);
        handlingTransactionAssertions.assertAll();

        verify(this.walletService).get(EXISTING_USER_ID);
        verify(this.walletService).proceedTransaction(wallet, amount, TransactionType.CREDIT);
    }

    @Test
    @DisplayName("Test handling transaction with existing transaction id")
    void handleTransactionWithExistingTransactionIdShouldThrowExceptionTest() {
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest(BigDecimal.TEN, TransactionType.DEBIT,
                        EXISTING_USER_ID, EXISITNG_TRANSACTION_ID);

        assertThatThrownBy(() -> this.transactionService.handleTransaction(registerTransactionRequest))
                .isInstanceOf(TransactionIdAlreadyExists.class)
                .hasMessage("Transaction with id asd123 already exists");
    }

    @Test
    @DisplayName("Test handling transaction with not existing user id")
    void handleTransactionWithNotExistingUserIdShouldThrowExceptionTest() {
        RegisterTransactionRequest registerTransactionRequest =
                TransactionGenerationUtils.generateRegisterTransactionRequest(BigDecimal.TEN, TransactionType.CREDIT,
                        NOT_EXISTING_USER_ID, NOT_EXISTING_TRANSACTION_ID);

        assertThatThrownBy(() -> this.transactionService.handleTransaction(registerTransactionRequest))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Unable find wallet by user id 1000000");
    }

    @Test
    @DisplayName("Test getting transaction by existing user id")
    void getTransactionsByExistingUserIdShouldReturnTransactionsTest() {
        List<Transaction> transactionsByUserId = this.transactionService.getTransactionsByUserId(EXISTING_USER_ID);

        assertThat(transactionsByUserId).hasSize(3);
    }
}