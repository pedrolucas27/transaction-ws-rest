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
@Table(name = "transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "origin_account", nullable = false, referencedColumnName = "account_number")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destiny_account", nullable = false, referencedColumnName = "account_number")
    private Account destinyAccount;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;
}
