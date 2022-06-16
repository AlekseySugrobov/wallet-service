package com.leovegas.walletservice.service.impl;

import com.google.common.collect.Lists;
import com.leovegas.walletservice.domain.entities.QWallet;
import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.domain.models.WalletFilter;
import com.leovegas.walletservice.exceptions.WalletNotFoundException;
import com.leovegas.walletservice.exceptions.WalletUniqueConstraintViolationException;
import com.leovegas.walletservice.repositories.WalletRepository;
import com.leovegas.walletservice.service.WalletService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class WalletServiceImpl implements WalletService {

    private final JPAQueryFactory jpaQueryFactory;
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository, EntityManager entityManager) {
        this.walletRepository = walletRepository;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Wallet create(long userId) {
        if (this.walletRepository.existsByUserId(userId)) {
            throw new WalletUniqueConstraintViolationException(userId);
        }
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        return this.walletRepository.save(wallet);
    }

    @Override
    public Wallet get(long userId) {
        return this.walletRepository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException(userId));
    }

    @Override
    public void delete(long userId) {
        if (!this.walletRepository.existsByUserId(userId)) {
            throw new WalletNotFoundException(userId);
        }
        this.walletRepository.deleteByUserId(userId);
    }

    @Override
    public List<Wallet> search(WalletFilter walletFilter) {
        BooleanExpression searchExpression = QWallet.wallet.isNotNull();
        if (Objects.nonNull(walletFilter.getUserId())) {
            searchExpression = searchExpression.and(QWallet.wallet.userId.eq(walletFilter.getUserId()));
        }
        if (Objects.nonNull(walletFilter.getFromBalance())) {
            searchExpression = searchExpression.and(QWallet.wallet.balance.goe(walletFilter.getFromBalance()));
        }
        if (Objects.nonNull(walletFilter.getToBalance())) {
            searchExpression = searchExpression.and(QWallet.wallet.balance.loe(walletFilter.getToBalance()));
        }
        return Lists.newArrayList(this.walletRepository.findAll(searchExpression));
    }

    public BigDecimal getBalanceByUserId(long userId) {
        if (!this.walletRepository.existsByUserId(userId)) {
            throw new WalletNotFoundException(userId);
        }
        return this.jpaQueryFactory.query()
                .select(QWallet.wallet.balance)
                .from(QWallet.wallet)
                .where(QWallet.wallet.userId.eq(userId))
                .fetchOne();
    }
}
