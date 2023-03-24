package com.natwest.primetask.service;

import com.natwest.primetask.cache.CachedDataImpl;
import com.natwest.primetask.model.PrimeNumberResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit test cases.
 * Method naming convention- methodUnderTest_testCondition_verificationScenario.
 */
@ExtendWith(MockitoExtension.class)
public class PrimeNumberServiceImplTest {

    @InjectMocks
    private PrimeNumberServiceImpl primeNumberService;

    @Mock
    private CachedDataImpl cachedData;

    @ParameterizedTest
    @ValueSource(strings = {"Naive", "Sieve", "Miller-Rabin", "Default"})
    public void getAllPrimeNumbers_sameInputWithDifferentAlgorithms_sameResult(String algorithmName) {
        PrimeNumberResponse primeNumberResponse = primeNumberService.getAllPrimeNumbers(10, algorithmName, false);
        assertEquals(List.of(2, 3, 5, 7), primeNumberResponse.getPrimes());
        assertEquals(10, primeNumberResponse.getInitial());
        verify(cachedData, times(0)).get(10);
        verify(cachedData, times(1)).add(10, List.of(2, 3, 5, 7));
    }

    @Test
    public void getAllPrimeNumbers_resultAvailableInCache_resultFromCache() {
        when(cachedData.get(10)).thenReturn(List.of(2,3,5,7));
        PrimeNumberResponse primeNumberResponse = primeNumberService.getAllPrimeNumbers(10, "Naive", true);
        assertEquals(List.of(2, 3, 5, 7), primeNumberResponse.getPrimes());
        assertEquals(10, primeNumberResponse.getInitial());
        verify(cachedData, times(2)).get(10);
        verify(cachedData, times(0)).add(10, List.of(2, 3, 5, 7));
    }

}
