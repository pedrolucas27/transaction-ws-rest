package br.edu.ufrn.service;

import br.edu.ufrn.dto.Message;
import br.edu.ufrn.dto.TransactionRequest;

@FunctionalInterface
public interface TransactionService {
    Message process(TransactionRequest request);
}
