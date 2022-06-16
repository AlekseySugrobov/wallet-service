package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.domain.entities.Player;
import com.leovegas.walletservice.domain.models.PlayerFilter;
import com.leovegas.walletservice.exceptions.PlayerNotFoundException;
import com.leovegas.walletservice.repositories.PlayerRepository;
import com.leovegas.walletservice.utils.PlayerGenerationUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Rollback
@Transactional
@SpringBootTest
@DisplayName("Tests for PlayerServiceImpl")
class PlayerServiceImplTest {

    @Autowired
    private PlayerServiceImpl playerService;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @DisplayName("Test for saving new player")
    void saveNewPlayerTest_ShouldCreateNewPlayer() {
        Player player = PlayerGenerationUtils.generatePlayer();

        Player savedPlayer = this.playerService.save(player);

        SoftAssertions savedPlayerAssertions = new SoftAssertions();
        savedPlayerAssertions.assertThat(savedPlayer.getId()).isNotNull();
        savedPlayerAssertions.assertThat(savedPlayer.getFirstName()).isEqualTo(player.getFirstName());
        savedPlayerAssertions.assertThat(savedPlayer.getLastName()).isEqualTo(player.getLastName());
        savedPlayerAssertions.assertThat(savedPlayer.getBalance()).isEqualTo(player.getBalance());
        savedPlayerAssertions.assertThat(savedPlayer.getCreated()).isNotNull();
        savedPlayerAssertions.assertThat(savedPlayer.getUpdated()).isNotNull();
        savedPlayerAssertions.assertAll();
    }

    @Test
    @DisplayName("Test for editing existing player")
    void editExistingPlayer_shouldEditSuccessfully() {
        String expectedLastName = RandomStringUtils.randomAlphabetic(30);
        String expectedFirstName = RandomStringUtils.randomAlphabetic(30);
        BigDecimal expectedBalance = BigDecimal.valueOf(4.0);
        Player player = PlayerGenerationUtils.generatePlayer();
        this.playerService.save(player);
        player.setFirstName(expectedFirstName);
        player.setLastName(expectedLastName);
        player.setBalance(expectedBalance);

        Player savedPlayer = this.playerService.save(player);

        SoftAssertions playerAssertions = new SoftAssertions();
        playerAssertions.assertThat(savedPlayer.getFirstName()).isEqualTo(expectedFirstName);
        playerAssertions.assertThat(savedPlayer.getLastName()).isEqualTo(expectedLastName);
        playerAssertions.assertThat(savedPlayer.getBalance()).isEqualTo(expectedBalance);
        playerAssertions.assertAll();
    }

    @Test
    @DisplayName("Test for editing not existing player")
    void editNotExistingPlayer_shouldThrowException() {
        Player player = PlayerGenerationUtils.generatePlayer();
        player.setId(1_000_000L);

        assertThatThrownBy(() -> this.playerService.save(player))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Unable find player by id " + player.getId());
    }

    @Test
    @DisplayName("Test for getting existing player by id")
    void getExistingPlayerById_ShouldReturnExistingPlayer() {
        Player player = PlayerGenerationUtils.generatePlayer();
        this.playerService.save(player);

        Player playerFromDatabase = this.playerService.get(player.getId());

        SoftAssertions savedPlayerAssertions = new SoftAssertions();
        savedPlayerAssertions.assertThat(playerFromDatabase.getId()).isEqualTo(player.getId());
        savedPlayerAssertions.assertThat(playerFromDatabase.getFirstName()).isEqualTo(player.getFirstName());
        savedPlayerAssertions.assertThat(playerFromDatabase.getLastName()).isEqualTo(player.getLastName());
        savedPlayerAssertions.assertThat(playerFromDatabase.getBalance()).isEqualTo(player.getBalance());
        savedPlayerAssertions.assertAll();
    }

    @Test
    @DisplayName("Test for getting not existing player by id")
    void getNotExistingPlayerById_ShouldThrowException() {
        long id = 1_000_000;
        assertThatThrownBy(() -> this.playerService.get(id))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Unable find player by id " + id);
    }

    @Test
    @DisplayName("Test deleting player by id")
    void deleteExistingPlayerById_ShouldDeletePlayer() {
        Player player = PlayerGenerationUtils.generatePlayer();
        this.playerService.save(player);

        this.playerService.delete(player.getId());

        assertThat(this.playerRepository.existsById(player.getId())).isFalse();
    }

    @Test
    @DisplayName("Test for deleting not existing player by id")
    void deleteNotExistingPlayerById_ShouldThrowException() {
        long id = 1_000_000;
        assertThatThrownBy(() -> this.playerService.delete(id))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Unable find player by id " + id);
    }

    @Test
    @DisplayName("Test searching players by filter")
    void searchPlayersByFilterTest() {
        PlayerFilter playerFilter = new PlayerFilter();
        playerFilter.setFirstName("o");
        playerFilter.setFromBalance(BigDecimal.valueOf(0.0));
        playerFilter.setToBalance(BigDecimal.valueOf(100.0));

        List<Player> players = this.playerService.search(playerFilter);

        SoftAssertions filteredPlayersAssertions = new SoftAssertions();
        filteredPlayersAssertions.assertThat(players).extracting(Player::getBalance)
                .filteredOn(balance -> balance.compareTo(BigDecimal.valueOf(0.0)) == -1 && balance.compareTo(BigDecimal.valueOf(100.0)) == 1)
                .isEmpty();
        filteredPlayersAssertions.assertThat(players).extracting(Player::getFirstName)
                .filteredOn(firstName -> !firstName.toLowerCase(Locale.ROOT).contains("o"))
                .isEmpty();
        filteredPlayersAssertions.assertAll();
    }

    @Test
    @DisplayName("Test getting balance for not existing player by id")
    void gettingBalanceForNotExistingPlayerById_ShouldThrowException() {
        long id = 1_000_000;
        assertThatThrownBy(() -> this.playerService.getBalanceById(id))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Unable find player by id " + id);
    }

    @Test
    @DisplayName("Test getting balance for existing player by id")
    void gettingBalanceForNotExistingPlayerById_ShouldReturnBalance() {
        BigDecimal expectedBalance = BigDecimal.valueOf(1234.56);
        Player player = PlayerGenerationUtils.generatePlayer();
        player.setBalance(expectedBalance);
        this.playerService.save(player);

        BigDecimal balance = this.playerService.getBalanceById(player.getId());

        assertThat(balance).isEqualTo(expectedBalance);
    }
}