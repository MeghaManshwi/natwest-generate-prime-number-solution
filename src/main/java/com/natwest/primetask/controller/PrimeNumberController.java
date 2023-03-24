package com.natwest.primetask.controller;

import com.natwest.primetask.model.PrimeNumberResponse;
import com.natwest.primetask.service.PrimeNumberService;
import com.natwest.primetask.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller i.e. resource class.
 */
@RestController
public class PrimeNumberController {

    private static final Logger log = LoggerFactory.getLogger(PrimeNumberController.class);
    @Autowired
    private PrimeNumberService primeNumberService;

    /**
     * Get all prime numbers equals to or less than given number.
     * @param initial {@link Integer}
     * @param algorithmName {@link  String}
     * @param readFromCache {@link Boolean}
     * @return {@link ResponseEntity of data type {@link PrimeNumberResponse}}
     */
    @GetMapping(value = "/primes/{initial}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getAllPrimeNumbers(@PathVariable int initial, @RequestParam(value = "algorithmName", required = false, defaultValue = "Default") String algorithmName,
                                                     @RequestParam(value = "readFromCache", required = false, defaultValue = "true") boolean readFromCache
            ) {
        log.info("get all prime numbers till {} using algorithm {}", algorithmName, initial);
        try {
            //validation
            RequestValidator.validateRequestParameters(initial, algorithmName);
        } catch (final Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            //service call
            final PrimeNumberResponse data = primeNumberService.getAllPrimeNumbers(initial, algorithmName,readFromCache);
            return new ResponseEntity<>(data,HttpStatus.OK);
        } catch (final Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
