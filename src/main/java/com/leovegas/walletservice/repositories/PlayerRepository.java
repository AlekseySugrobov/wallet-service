package com.leovegas.walletservice.repositories;

import com.leovegas.walletservice.domain.entities.Player;
import com.leovegas.walletservice.domain.entities.QPlayer;

public interface PlayerRepository extends QueryDslRepository<Player, QPlayer, Long> {
}
