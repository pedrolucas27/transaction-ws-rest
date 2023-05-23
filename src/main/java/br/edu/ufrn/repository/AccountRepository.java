package br.edu.ufrn.repository;

import br.edu.ufrn.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("select a from Account a where a.accountNumber = ?1 and a.client.cpf = ?2")
    Optional<Account> findByAccountNumberAndClientCpf(String accountNumber, String cpf);

}
