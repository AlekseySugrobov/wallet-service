package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.domain.models.WalletFilter;
import com.leovegas.walletservice.exceptions.WalletNotFoundException;
import com.leovegas.walletservice.exceptions.WalletUniqueConstraintViolationException;
import com.leovegas.walletservice.repositories.WalletRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Rollback
@Transactional
@SpringBootTest
@DisplayName("Tests for WalletServiceImpl")
class WalletServiceImplTest {

    private static final long USER_ID = 1_000_000;

    @Autowired
    private WalletServiceImpl walletService;
    @Autowired
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Test for creating new wallet")
    void createNewWalletTest() {
        Wallet wallet = this.walletService.create(USER_ID);

        SoftAssertions walletAssertions = new SoftAssertions();
        walletAssertions.assertThat(wallet.getUserId()).isEqualTo(USER_ID);
        walletAssertions.assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ZERO);
        walletAssertions.assertThat(wallet.getCreated()).isNotNull();
        walletAssertions.assertThat(wallet.getUpdated()).isNotNull();
        walletAssertions.assertAll();
    }

    @Test
    @DisplayName("Test for creating wallet for same user id")
    void createNewWalletForSameUserTest() {
        this.walletService.create(USER_ID);
        assertThatThrownBy(() -> this.walletService.create(USER_ID))
                .isInstanceOf(WalletUniqueConstraintViolationException.class)
                .hasMessage("Wallet unique constraint violation for user id " + USER_ID);
    }

    @Test
    @DisplayName("Test getting wallet by existing user id")
    void getWalletByUserIdTest() {
        this.walletService.create(USER_ID);

        Wallet wallet = this.walletService.get(USER_ID);

        SoftAssertions walletAssertions = new SoftAssertions();
        walletAssertions.assertThat(wallet.getUserId()).isEqualTo(USER_ID);
        walletAssertions.assertThat(wallet.getBalance()).isEqualTo(BigDecimal.ZERO);
        walletAssertions.assertThat(wallet.getCreated()).isNotNull();
        walletAssertions.assertThat(wallet.getUpdated()).isNotNull();
        walletAssertions.assertAll();
    }

    @Test
    @DisplayName("Test getting wallet by not existing user id")
    void getWalletByNotExistingUserId() {
        assertThatThrownBy(() -> this.walletService.get(USER_ID))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Unable find wallet by user id " + USER_ID);
    }

    @Test
    @DisplayName("Test deleting wallet by user id")
    void deleteWalletByExistingUserIdTest() {
        this.walletService.create(USER_ID);

        this.walletService.delete(USER_ID);

        assertThat(this.walletRepository.existsByUserId(USER_ID)).isFalse();
    }

    @Test
    @DisplayName("Test for deleting not existing player by id")
    void deleteWalletByNotExistingUserIdTest() {
        assertThatThrownBy(() -> this.walletService.delete(USER_ID))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Unable find wallet by user id " + USER_ID);
    }

    @Test
    @DisplayName("Test searching wallets by filter")
    void searchWalletsByFilterTest() {
        WalletFilter walletFilter = new WalletFilter();
        walletFilter.setFromBalance(BigDecimal.valueOf(0.0));
        walletFilter.setToBalance(BigDecimal.valueOf(100.0));

        List<Wallet> players = this.walletService.search(walletFilter);

        assertThat(players).extracting(Wallet::getBalance)
                .filteredOn(balance -> balance.compareTo(BigDecimal.valueOf(0.0)) == -1 && balance.compareTo(BigDecimal.valueOf(100.0)) == 1)
                .isEmpty();
    }

    @Test
    @DisplayName("Test getting balance for not existing player by id")
    void gettingBalanceForNotExistingUserIdTest() {
        assertThatThrownBy(() -> this.walletService.getBalanceByUserId(USER_ID))
                .isInstanceOf(WalletNotFoundException.class)
                .hasMessage("Unable find wallet by user id " + USER_ID);
    }

    @Test
    @DisplayName("Test getting balance for existing player by id")
    void gettingBalanceForExistingUserIdTest() {
        this.walletService.create(USER_ID);

        BigDecimal balance = this.walletService.getBalanceByUserId(USER_ID);

        assertThat(balance.compareTo(BigDecimal.ZERO)).isEqualTo(0);
    }
}
