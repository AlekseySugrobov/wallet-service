package com.leovegas.walletservice.utils;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Utility operations for generating data via Podam library.
 */
public final class PodamUtils {
    private static final PodamFactory PODAM_FACTORY;

    static {
        PODAM_FACTORY = new PodamFactoryImpl();
    }

    /**
     * Creates and generates data for object of T class.
     *
     * @param type            - type.
     * @param <T>             - Class.
     * @param genericTypeArgs - the generic Type arguments for a generic class instance.
     * @return object with generated data.
     */
    public static <T> T manufacturePojo(Class<T> type, Type... genericTypeArgs) {
        return PODAM_FACTORY.manufacturePojo(type, genericTypeArgs);
    }


    /**
     * Creates and generates data for object of T class and wrap as {@link Optional}.
     *
     * @param type - type.
     * @param <T>  - Class.
     * @return object with generated data.
     */
    public static <T> Optional<T> manufactureOptionalPojo(Class<T> type) {
        return Optional.of(manufacturePojo(type));
    }

    private PodamUtils() {
    }
}
