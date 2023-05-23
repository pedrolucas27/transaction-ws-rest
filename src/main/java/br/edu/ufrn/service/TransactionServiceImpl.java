package br.edu.ufrn.service;

import br.edu.ufrn.dto.Message;
import br.edu.ufrn.dto.TransactionRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransferService transferService;

    @Override
    public Message process(TransactionRequest request) {
        if (Objects.nonNull(request)) {
            return transferService.processTransfer(request.originAccount(), request.destinyAccount(), request.amount());
        } else {
            return new Message("Informe as informações solicitadas para efetuação da transação!", false);
        }
    }
}
