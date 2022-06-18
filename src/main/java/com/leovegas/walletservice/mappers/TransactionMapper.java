package com.leovegas.walletservice.mappers;

import com.leovegas.model.TransactionDto;
import com.leovegas.walletservice.domain.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Mapper for {@link Transaction}
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {
    /**
     * Creates instance of {@link TransactionDto} from instance of {@link Transaction}.
     *
     * @param transaction instance of {@link Transaction}
     * @return instance of {@link TransactionDto}
     */
    @Mapping(source = "wallet.userId", target = "userId")
    @Mapping(source = "transactionId", target = "transactionId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "created", target = "startDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(source = "updated", target = "endDate", qualifiedByName = "localDateTimeToOffsetDateTime")
    TransactionDto transactionToTransactionDto(Transaction transaction);

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return OffsetDateTime.of(localDateTime, ZoneId.systemDefault().getRules().getOffset(localDateTime));
    }

    /**
     * Creates list of {@link TransactionDto} from list of {@link Transaction}
     *
     * @param transactions list of {@link Transaction}
     * @return list of {@link TransactionDto}
     */
    List<TransactionDto> transactionListToTransactionDtoList(List<Transaction> transactions);
}
