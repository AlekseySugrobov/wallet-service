package com.leovegas.walletservice.mappers;

import com.leovegas.model.WalletDTO;
import com.leovegas.walletservice.domain.entities.Wallet;
import com.leovegas.walletservice.utils.WalletGenerationUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@DisplayName("Test for wallet mapper")
class WalletMapperTest {
    private static final WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Test
    @DisplayName("Test mapping wallet to walletDTO")
    void walletToWalletDtoTest() {
        Wallet wallet = WalletGenerationUtils.generateWallet();

        WalletDTO walletDTO = INSTANCE.walletToWalletDTO(wallet);

        SoftAssertions walletDTOAssertions = new SoftAssertions();
        walletDTOAssertions.assertThat(walletDTO.getId()).isEqualTo(wallet.getId());
        walletDTOAssertions.assertThat(walletDTO.getUserId()).isEqualTo(wallet.getUserId());
        walletDTOAssertions.assertThat(walletDTO.getBalance()).isEqualTo(wallet.getBalance());
        walletDTOAssertions.assertAll();
    }
}