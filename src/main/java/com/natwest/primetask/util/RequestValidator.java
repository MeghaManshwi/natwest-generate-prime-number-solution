package com.natwest.primetask.util;

import com.natwest.primetask.constant.PrimeNumberAlgorithm;
import com.natwest.primetask.exception.IncorrectRequestParameterException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility to validate request parameters.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestValidator {

    /**
     * Validate request parameters.
     * @param initial {@link Integer}
     * @param algorithmName {@link String}
     * @throws IncorrectRequestParameterException {@link IncorrectRequestParameterException}
     */
    public static void validateRequestParameters(final int initial, final String algorithmName) throws IncorrectRequestParameterException {
        if (initial < 0) {
            throw new IncorrectRequestParameterException(initial + " is not allowed; only non negative number is allowed");
        }
        if (PrimeNumberAlgorithm.getPrimeNumberAlgorithm(algorithmName) == null) {
            throw new IncorrectRequestParameterException(algorithmName + " is not accepted algorithm");
        }
    }
}