package br.edu.ufrn.dto;

public record AccountResponse(String cpf, String name, String lastName, double balance, String accountNumber) {
}
