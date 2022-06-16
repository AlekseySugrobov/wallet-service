package com.leovegas.walletservice.utils;

import com.leovegas.walletservice.domain.entities.Player;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;

public class PlayerGenerationUtils {


    public static Player generatePlayer() {
        Player player = new Player();
        player.setFirstName(RandomStringUtils.randomAlphabetic(20));
        player.setLastName(RandomStringUtils.randomAlphabetic(30));
        player.setBalance(BigDecimal.valueOf(RandomUtils.nextDouble(0.0, 10_000_000.0)));
        return player;
    }

    private PlayerGenerationUtils() {

    }
}
