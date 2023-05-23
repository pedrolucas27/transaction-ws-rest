package br.edu.ufrn.service;

import br.edu.ufrn.model.Account;
import br.edu.ufrn.repository.AccountRepository;
import br.edu.ufrn.utils.InsufficientFundsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public boolean withdraw(Account account, double amount) {
        if(amount > account.getBalance()) throw new InsufficientFundsException();

        account.setBalance(account.getBalance() - amount);
        account.setUpdateAt(Timestamp.from(ZonedDateTime.now().toInstant()));

        repository.save(account);

        return Boolean.TRUE;
    }

    @Override
    public boolean deposit(Account account, double amount){
        account.setBalance(account.getBalance() + amount);
        account.setUpdateAt(Timestamp.from(ZonedDateTime.now().toInstant()));

        repository.save(account);

        return Boolean.TRUE;
    }

    @Override
    public boolean rollbackWithdraw(Account account, double amount) {
        return deposit(account, amount);
    }

    @Override
    public boolean rollbackDeposit(Account account, double amount) {
        return withdraw(account, amount);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) throws EntityNotFoundException {
        Optional<Account> optional = repository.findByAccountNumber(accountNumber);
        return optional.orElseThrow(() -> new EntityNotFoundException("Não existe conta com o número: "+accountNumber));
    }

    @Override
    public Account findByAccountNumberAndClientCpf(String accountNumber, String cpf) throws EntityNotFoundException {
        Optional<Account> optional = repository.findByAccountNumberAndClientCpf(accountNumber, cpf);
        return optional.orElseThrow(() -> new EntityNotFoundException("Não existe conta com o número "+accountNumber+" e cpf "+cpf));
    }
}
