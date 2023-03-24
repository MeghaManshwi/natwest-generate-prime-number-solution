package com.natwest.primetask;

import com.natwest.primetask.controller.PrimeNumberController;
import com.natwest.primetask.model.PrimeNumberResponse;
import com.natwest.primetask.service.PrimeNumberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test case.
 * Method naming convention- methodUnderTest_testCondition_verificationScenario.
 */
@SpringBootTest
class PrimeTaskApplicationTests {

	@Autowired
	private PrimeNumberController primeNumberController;

	/**
	 * parameterized test case with read from cache disabled.
	 * Cache is disabled so fetching would happen everytime.
	 * @param algorithmName {@link String}
	 */
	@ParameterizedTest
	@ValueSource(strings = {"Naive","Sieve","Miller-Rabin","Default"})
	public void getAllPrimeNumbers_sameInputWithDifferentAlgorithms_sameResult(String algorithmName) {
		ResponseEntity<Object> responseEntity = primeNumberController.getAllPrimeNumbers(10, algorithmName,false);
		PrimeNumberResponse data= (PrimeNumberResponse) responseEntity.getBody();
		assertEquals(List.of(2,3,5,7), data.getPrimes());
		assertEquals(10, data.getInitial());
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	/**
	 * parameterized test case with read from cache enabled.
	 * Cache is enabled so if cache has data, it would be returned from there.
	 * @param algorithmName {@link String}
	 */
	@ParameterizedTest
	@ValueSource(strings = {"Naive","Sieve","Miller-Rabin","Default"})
	public void getAllPrimeNumbers_cacheEnabled_sameResult(String algorithmName) {
		ResponseEntity<Object> responseEntity = primeNumberController.getAllPrimeNumbers(10, algorithmName,true);
		PrimeNumberResponse data= (PrimeNumberResponse) responseEntity.getBody();
		assertEquals(List.of(2,3,5,7), data.getPrimes());
		assertEquals(10, data.getInitial());
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
	}

	/**
	 * Negative number in request would lead to a bad request.
	 */
	@Test
	public void getAllPrimeNumbers_negativeNumberAsInitial_badRequest() {
		ResponseEntity<Object> errorResponse = primeNumberController.getAllPrimeNumbers(-5, "Sieve",false);
		String errorMessage= (String) errorResponse.getBody();
		assertEquals("-5 is not allowed; only non negative number is allowed", errorMessage);
		assertEquals(HttpStatus.BAD_REQUEST,errorResponse.getStatusCode());
	}

	/**
	 * Invalid algorithm in request would lead to a bad request.
	 */
	@Test
	public void getAllPrimeNumbers_invalidAlgorithm_badRequest() {
		ResponseEntity<Object> errorResponse = primeNumberController.getAllPrimeNumbers(15, "ABC",false);
		String errorMessage= (String) errorResponse.getBody();
		assertEquals("ABC is not accepted algorithm", errorMessage);
		assertEquals(HttpStatus.BAD_REQUEST,errorResponse.getStatusCode());
	}


}
