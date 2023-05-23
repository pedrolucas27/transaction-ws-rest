package br.edu.ufrn.utils;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException() {
        super("Saldo insuficiente.");
    }
}
