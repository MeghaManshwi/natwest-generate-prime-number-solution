package com.natwest.primetask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrimeNumberResponse {
    @JsonProperty("Initial")
    private Integer initial;
    @JsonProperty("Primes")
    private List<Integer> primes = new ArrayList<>();
}
