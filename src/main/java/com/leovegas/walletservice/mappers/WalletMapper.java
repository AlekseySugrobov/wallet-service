package com.leovegas.walletservice.mappers;

import com.leovegas.model.WalletDTO;
import com.leovegas.walletservice.domain.entities.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "balance", target = "balance")
    WalletDTO walletToWalletResponse(Wallet wallet);

    List<WalletDTO> walletListToWalletDTOList(List<Wallet> wallets);
}
