package com.leovegas.walletservice.service.impl;

import com.google.common.collect.Lists;
import com.leovegas.walletservice.domain.entities.Player;
import com.leovegas.walletservice.domain.entities.QPlayer;
import com.leovegas.walletservice.domain.models.PlayerFilter;
import com.leovegas.walletservice.exceptions.PlayerNotFoundException;
import com.leovegas.walletservice.repositories.PlayerRepository;
import com.leovegas.walletservice.service.PlayerService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final JPAQueryFactory jpaQueryFactory;
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, EntityManager entityManager) {
        this.playerRepository = playerRepository;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

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
        BooleanExpression searchExpression = QPlayer.player.isNotNull();
        if (StringUtils.isNotBlank(playerFilter.getFirstName())) {
            searchExpression = searchExpression.and(QPlayer.player.firstName.containsIgnoreCase(playerFilter.getFirstName()));
        }
        if (StringUtils.isNotBlank(playerFilter.getLastName())) {
            searchExpression = searchExpression.and(QPlayer.player.lastName.containsIgnoreCase(playerFilter.getLastName()));
        }
        if (Objects.nonNull(playerFilter.getFromBalance())) {
            searchExpression = searchExpression.and(QPlayer.player.balance.goe(playerFilter.getFromBalance()));
        }
        if (Objects.nonNull(playerFilter.getToBalance())) {
            searchExpression = searchExpression.and(QPlayer.player.balance.loe(playerFilter.getToBalance()));
        }
        return Lists.newArrayList(this.playerRepository.findAll(searchExpression));
    }

    public BigDecimal getBalanceById(long id) {
        if (!this.playerRepository.existsById(id)) {
            throw new PlayerNotFoundException(id);
        }
        return this.jpaQueryFactory.query()
                .select(QPlayer.player.balance)
                .from(QPlayer.player)
                .where(QPlayer.player.id.eq(id))
                .fetchOne();
    }
}
