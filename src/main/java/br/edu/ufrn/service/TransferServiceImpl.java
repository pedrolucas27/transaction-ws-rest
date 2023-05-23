package br.edu.ufrn.service;

import br.edu.ufrn.model.Account;
import br.edu.ufrn.model.Transfer;
import br.edu.ufrn.dto.Message;
import br.edu.ufrn.repository.TransferRepository;
import br.edu.ufrn.utils.InsufficientFundsException;
import br.edu.ufrn.utils.TransferException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;
    private final TransferRepository repository;

    @Override
    @Transactional
    public Message processTransfer(String originAccountNumber, String destinyAccountNumber, Double transferAmount) {
        boolean withdrawalSuccessful = false;
        Account originAccount, destinyAccount;

        if(transferAmount <= 0) return new Message("O valor informado para transferência é inválido.", false);

        if(destinyAccountNumber.equals(originAccountNumber)) return new Message("A conta de destino informada é inválida.", false);

        try {
            originAccount = accountService.findByAccountNumber(originAccountNumber);
            destinyAccount = accountService.findByAccountNumber(destinyAccountNumber);
        }catch (EntityNotFoundException ex) {
            return new Message(ex.getMessage(), false);
        }

        try {
            withdrawalSuccessful = accountService.withdraw(originAccount, transferAmount);
            if(withdrawalSuccessful){
                accountService.deposit(destinyAccount, transferAmount);
            }
        } catch (InsufficientFundsException ex) {
            if (withdrawalSuccessful) {
                accountService.rollbackWithdraw(originAccount, transferAmount);
            }
            return new Message(ex.getMessage(), false);
        }

        return saveTransfer(originAccount, destinyAccount, transferAmount);
    }

    @Override
    public Message saveTransfer(Account origin, Account destiny, Double amount) {

        try {
            Transfer transfer = new Transfer();

            transfer.setOriginAccount(origin);
            transfer.setDestinyAccount(destiny);
            transfer.setAmount(amount);
            transfer.setTimestamp(Timestamp.from(ZonedDateTime.now().toInstant()));

            repository.save(transfer);
        }catch (TransferException ex){
            accountService.rollbackWithdraw(origin, amount);
            accountService.rollbackDeposit(destiny, amount);

            return new Message(ex.getMessage(), false);
        }

        return new Message("Transferência realizada.", true);
    }

    @Override
    public List<Transfer> getHistory(String accountNumber, String cpf) {
        Account account = accountService.findByAccountNumberAndClientCpf(accountNumber, cpf);
        return repository.findByAccount(account);
    }

}
