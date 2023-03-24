package com.natwest.primetask.service;

import com.natwest.primetask.cache.CachedData;
import com.natwest.primetask.constant.PrimeNumberAlgorithm;
import com.natwest.primetask.model.PrimeNumberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.natwest.primetask.constant.PrimeNumberAlgorithm.*;

/**
 * Service layer.
 */
@Service
public class PrimeNumberServiceImpl implements PrimeNumberService {

    @Autowired
    private CachedData cachedData;

    private static final Logger log = LoggerFactory.getLogger(PrimeNumberServiceImpl.class);

    /**
     * Get all prime numbers.
     *
     * @param initial {@link Integer}
     * @param algorithmName {@link String}
     * @param readFromCache {@link Boolean}
     * @return {@link PrimeNumberResponse}
     */
    @Override
    public PrimeNumberResponse getAllPrimeNumbers(final int initial, final String algorithmName, final boolean readFromCache) {

        //fetch from cache
        if (readFromCache && !CollectionUtils.isEmpty(cachedData.get(initial))) {
            log.info("Prime numbers found in cache; getting response from cache");
            return PrimeNumberResponse.builder().primes(cachedData.get(initial)).initial(initial).build();
        }

        final PrimeNumberAlgorithm name = getPrimeNumberAlgorithm(algorithmName);
        log.info("fetching all prime numbers till {} using {} algorithm", initial, name);
        final List<Integer> primeNumbers;
        if (name == DEFAULT) {
            primeNumbers = getPrimeNumbersWithDefaultApproach(initial);
        } else if (name == SIEVE_OF_ERATOSTHENE) {
            primeNumbers = getPrimeNumbersWithSieveApproach(initial);
        } else if (name == NAIVE) {
            primeNumbers = getPrimeNumbersWithNaiveApproach(initial);
        } else {
            primeNumbers = getPrimeNumbersWithMillerApproach(initial);
        }
        cachedData.add(initial, primeNumbers);
        return PrimeNumberResponse.builder().primes(primeNumbers).initial(initial).build();
    }

    /**
     * Get prime numbers with miller approach.
     *
     * @param initial {@link Integer}
     * @return {@link List of {@link Integer}}
     */
    private List<Integer> getPrimeNumbersWithMillerApproach(Integer initial) {
        final List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= initial; i++) {
            if (isPrime(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    /**
     * Get prime numbers with sieve approach.
     *
     * @param initial {@link Integer}
     * @return {@link List of {@link Integer}}
     */
    private List<Integer> getPrimeNumbersWithSieveApproach(final int initial) {
        boolean[] primes = new boolean[initial + 1];
        Arrays.fill(primes, true);

        for (int p = 2; p * p <= initial; p++) {
            if (primes[p]) {
                for (int i = p * p; i <= initial; i += p) {
                    primes[i] = false;
                }
            }
        }

        final List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= initial; i++) {
            if (primes[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;

    }


    /**
     * Get prime numbers with naive approach.
     *
     * @param initial {@link Integer}
     * @return {@link List of {@link Integer}}
     */
    private List<Integer> getPrimeNumbersWithNaiveApproach(final int initial) {
        final List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= initial; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    /**
     * Get prime numbers with default approach.
     *
     * @param initial {@link Integer}
     * @return {@link List of {@link Integer}}
     */
    private List<Integer> getPrimeNumbersWithDefaultApproach(final int initial) {
        return IntStream.rangeClosed(2, initial).filter(PrimeNumberServiceImpl::isPrimeCheck).boxed().collect(Collectors.toList());
    }

    /**
     * Checks if given number is prime.
     *
     * @param n {@link Integer}
     * @return {@link Boolean}
     */
    public boolean isPrime(final int n) {
        if (n == 2 || n == 3) {
            return true;
        }
        if (n == 1 || n % 2 == 0) {
            return false;
        }

        int d = n - 1;
        int s = 0;
        while (d % 2 == 0) {
            d /= 2;
            s++;
        }

        for (int i = 0; i < 5; i++) {
            int a = (int) (Math.random() * (n - 3)) + 2;
            int x = fastModularExponentiation(a, d, n);
            if (x == 1 || x == n - 1) {
                continue;
            }
            for (int j = 0; j < s - 1; j++) {
                x = (int) ((long) x * x % n);
                if (x == n - 1) {
                    break;
                }
            }
            if (x != n - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fast modular exponentiation.
     *
     * @param a {@link Integer}
     * @param b {@link Integer}
     * @param n {@link Integer}
     * @return {@link Integer}
     */
    public static int fastModularExponentiation(int a, int b, final int n) {
        int result = 1;
        while (b > 0) {
            if (b % 2 == 1) {
                result = (int) ((long) result * a % n);
            }
            a = (int) ((long) a * a % n);
            b /= 2;
        }
        return result;
    }

    /**
     * Checks if given number is prime.
     * @param number {@link Integer}
     * @return {@link Boolean}
     */
    private static boolean isPrimeCheck(final int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number))).allMatch(n -> number % n != 0);
    }
}
