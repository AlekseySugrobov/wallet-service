package com.leovegas.walletservice.mappers;

import com.leovegas.model.WalletDTO;
import com.leovegas.walletservice.domain.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for {@link Wallet}
 */
@Mapper(componentModel = "spring")
public interface WalletMapper {
    /**
     * Creates instance of {@link WalletDTO} from instance of {@link Wallet}.
     *
     * @param wallet instance of {@link Wallet}
     * @return instance of {@link WalletDTO}
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "balance", target = "balance")
    WalletDTO walletToWalletDTO(Wallet wallet);

    /**
     * Creates list of {@link WalletDTO} from list of {@link Wallet}.
     *
     * @param wallets list of {@link Wallet}
     * @return list of {@link WalletDTO}
     */
    List<WalletDTO> walletListToWalletDTOList(List<Wallet> wallets);
}
