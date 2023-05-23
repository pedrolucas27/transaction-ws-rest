package br.edu.ufrn.dto;

public record TransactionRequest(String originAccount, String destinyAccount, Double amount) {
}

