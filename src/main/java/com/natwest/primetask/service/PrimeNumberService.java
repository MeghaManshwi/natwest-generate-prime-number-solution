package com.natwest.primetask.service;


import com.natwest.primetask.model.PrimeNumberResponse;


public interface PrimeNumberService {

    PrimeNumberResponse getAllPrimeNumbers(final int initial, final String algorithmName,final boolean readFromCache);
}
