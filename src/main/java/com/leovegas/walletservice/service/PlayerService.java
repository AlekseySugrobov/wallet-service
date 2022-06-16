package com.leovegas.walletservice.service;

import com.leovegas.walletservice.domain.entities.Player;
import com.leovegas.walletservice.domain.models.PlayerFilter;

import java.util.List;

/**
 * Describes contract for interaction with player entity.
 */
public interface PlayerService {
    /**
     * Saves new or edit player.
     *
     * @param player {@link Player}
     * @return saved {@link Player}
     */
    Player save(Player player);

    /**
     * Returns player by id.
     *
     * @param id player's identifier
     * @return {@link Player}
     */
    Player get(long id);

    /**
     * Deletes player by id.
     *
     * @param id player's identifier
     * @return {@link Player}
     */
    void delete(long id);

    /**
     * Returns players by filter.
     *
     * @param playerFilter {@link PlayerFilter}
     * @return collection of {@link Player}
     */
    List<Player> search(PlayerFilter playerFilter);
}
