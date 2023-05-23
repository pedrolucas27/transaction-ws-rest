package br.edu.ufrn.controller;

import br.edu.ufrn.dto.AccountResponse;
import br.edu.ufrn.dto.Message;
import br.edu.ufrn.dto.TransactionRequest;
import br.edu.ufrn.dto.TransferResponse;
import br.edu.ufrn.model.Account;
import br.edu.ufrn.model.Transfer;
import br.edu.ufrn.service.AccountService;
import br.edu.ufrn.service.TransactionService;
import br.edu.ufrn.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/rest")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final TransferService transferService;

    @PostMapping(value = "/transaction")
    public ResponseEntity<String> processTransaction(@RequestBody TransactionRequest request) {
        try {
            Message message = transactionService.process(request);
            return new ResponseEntity<>(message.message(), message.status() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            return new ResponseEntity<>("Erro interno no serviço. Tente novamente mais tarde!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/client/{accountNumber}/{cpf}")
    public ResponseEntity findClientByCpfAndAccountNumber(@PathVariable String accountNumber, @PathVariable String cpf) {
        try {
            Account account = accountService.findByAccountNumberAndClientCpf(accountNumber, cpf);
            return new ResponseEntity(
                    new AccountResponse(
                            account.getClient().getCpf(),
                            account.getClient().getName(),
                            account.getClient().getLastName(),
                            account.getBalance(),
                            account.getAccountNumber()
                    ), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/history/{accountNumber}/{cpf}")
    public ResponseEntity histoy(@PathVariable String accountNumber, @PathVariable String cpf) {
        int i = 0;
        try {
            List<Transfer> list = transferService.getHistory(accountNumber, cpf);
            List<TransferResponse> responses = new ArrayList<>();

            list.forEach(it -> responses.add(toResponse(it, accountNumber)));
            return new ResponseEntity(responses, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private TransferResponse toResponse(Transfer transfer, String accountNumberClient){
        String message;

        String nameClientOrigin = transfer.getOriginAccount().getClient().getName();
        String nameClientDestiny = transfer.getDestinyAccount().getClient().getName();

        boolean flag = transfer.getOriginAccount().getAccountNumber().equals(accountNumberClient);

        if(flag){
            message = "Transferência enviada no valor de R$ "+transfer.getAmount()+" para "+nameClientDestiny;
        }else{
            message = "Transferência recebida no valor de R$ "+transfer.getAmount()+" de "+nameClientOrigin;
        }

        return new TransferResponse(message, transfer.getTimestamp(), flag);
    }


}
