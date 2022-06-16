package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.domain.entities.Player;
import com.leovegas.walletservice.domain.models.PlayerFilter;
import com.leovegas.walletservice.exceptions.PlayerNotFoundException;
import com.leovegas.walletservice.repositories.PlayerRepository;
import com.leovegas.walletservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Player save(Player player) {
        if (Objects.nonNull(player.getId()) && !this.playerRepository.existsById(player.getId())) {
            throw new PlayerNotFoundException(player.getId());
        }
        return this.playerRepository.save(player);
    }

    @Override
    public Player get(long id) {
        return this.playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @Override
    public void delete(long id) {
        if (!this.playerRepository.existsById(id)) {
            throw new PlayerNotFoundException(id);
        }
        this.playerRepository.deleteById(id);
    }

    @Override
    public List<Player> search(PlayerFilter playerFilter) {
        return null;
    }
}
