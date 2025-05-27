package com.alura.conversorMonedas.model;

import java.util.Map;

public record Divisas(Map<String, Double> conversion_rates) {
}
