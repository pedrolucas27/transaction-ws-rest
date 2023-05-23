package br.edu.ufrn.service;

import br.edu.ufrn.dto.Message;
import br.edu.ufrn.model.Account;
import br.edu.ufrn.model.Transfer;

import java.util.List;

public interface TransferService {
    Message processTransfer(String originAccount, String destinyAccount, Double amount);

    Message saveTransfer(Account origin, Account destiny, Double amount);

    List<Transfer> getHistory(String accountNumber, String cpf);
}
