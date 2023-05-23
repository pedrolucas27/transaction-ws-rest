package br.edu.ufrn.service;

import br.edu.ufrn.model.Account;
import br.edu.ufrn.utils.InsufficientFundsException;
import jakarta.persistence.EntityNotFoundException;

public interface AccountService {

    boolean withdraw(Account account, double amount) throws InsufficientFundsException;

    boolean deposit(Account account, double amount);

    boolean rollbackWithdraw(Account account, double amount);

    boolean rollbackDeposit(Account account, double amount);

    Account findByAccountNumber(String accountNumber) throws EntityNotFoundException;

    Account findByAccountNumberAndClientCpf(String accountNumber, String cpf) throws EntityNotFoundException;

}
