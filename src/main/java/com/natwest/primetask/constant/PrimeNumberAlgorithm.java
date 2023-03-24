package com.natwest.primetask.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Accepted Prime number algorithms.
 */
public enum PrimeNumberAlgorithm {

    DEFAULT("Default"), NAIVE("Naive"), SIEVE_OF_ERATOSTHENE("Sieve"), MILLER_RABIN("Miller-Rabin");

    private final String algorithmName;

    /**
     * Look up map.
     */
    private static final Map<String, PrimeNumberAlgorithm> lookup = new HashMap<>();

    //static block
    static {
        for (PrimeNumberAlgorithm algorithmName : PrimeNumberAlgorithm.values()) {
            lookup.put(algorithmName.getAlgorithmName(), algorithmName);
        }
    }

    PrimeNumberAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     * Get algorithm name.
     *
     * @return {@link String}
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Get prime number algorithm.
     *
     * @param algorithmName {@link String}
     * @return {@link PrimeNumberAlgorithm}
     */
    public static PrimeNumberAlgorithm getPrimeNumberAlgorithm(final String algorithmName){
        return lookup.get(algorithmName);
    }
}
