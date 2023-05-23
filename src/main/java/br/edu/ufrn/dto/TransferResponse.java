package br.edu.ufrn.dto;

import java.sql.Timestamp;

public record TransferResponse(String message, Timestamp date, boolean flag) {
}
