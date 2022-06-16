package com.leovegas.walletservice.repositories;

import com.leovegas.walletservice.domain.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
