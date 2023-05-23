package br.edu.ufrn.repository;

import br.edu.ufrn.model.Account;
import br.edu.ufrn.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    @Query("SELECT t FROM Transfer t WHERE t.originAccount = :account OR t.destinyAccount = :account ORDER BY t.timestamp ASC")
    List<Transfer> findByAccount(@Param("account") Account account);

}
