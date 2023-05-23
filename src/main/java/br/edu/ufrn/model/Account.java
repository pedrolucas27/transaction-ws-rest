package br.edu.ufrn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @ManyToOne
    @JoinColumn(name = "client_cpf", nullable = false)
    private Client client;

    @Column(name = "update_at", nullable = false)
    private Timestamp updateAt;
}
