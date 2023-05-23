package br.edu.ufrn.utils;

public class TransferException extends RuntimeException{
    public TransferException() {
        super("Houve um erro ao tentar salvar a transferência, as operações serão revertidas.");
    }
}
