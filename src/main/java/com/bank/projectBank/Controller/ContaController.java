package com.bank.projectBank.Controller;

import com.bank.projectBank.Model.Conta;
import com.bank.projectBank.Repository.contaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private contaRepository repository;

    // Criar conta
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Conta conta) {
        try {
            conta.validarConta();
            if (repository.getNumeroConta(conta.getNumeroConta()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe uma conta com este número");
            }
            repository.salvar(conta);
            return ResponseEntity.status(HttpStatus.CREATED).body(conta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Retorna todas as contas
    @GetMapping
    public ResponseEntity<?> getContas() {
        List<Conta> contas = repository.getContas();
        if (contas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma conta encontrada");
        }
        return ResponseEntity.ok(contas);
    }

    // Retorna a conta pelo ID [Número da conta]
    @GetMapping("/{numeroConta}")
    public ResponseEntity<?> getConta(@PathVariable String numeroConta) {
        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
        return ResponseEntity.ok(conta);
    }

    // Retorna a conta pelo CPF do titular
    @GetMapping("/cpf/{cpfTitular}")
    public ResponseEntity<?> getContaByCpf(@PathVariable String cpfTitular) {
        Conta conta = repository.getCpfTitularConta(cpfTitular);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada para o CPF fornecido");
        }
        return ResponseEntity.ok(conta);
    }

    // Faz depósito na conta
    @PutMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody Map<String, Object> depositoRequest) {
        String numeroConta = (String) depositoRequest.get("numeroConta");
        double valor = Double.parseDouble(depositoRequest.get("valor").toString());

        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }

        try {
            conta.deposito(valor);
            return ResponseEntity.ok(conta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Faz saque na conta
    @PutMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody Map<String, Object> saqueRequest) {
        String numeroConta = (String) saqueRequest.get("numeroConta");
        double valor = Double.parseDouble(saqueRequest.get("valor").toString());

        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }

        try {
            conta.saque(valor);
            return ResponseEntity.ok(conta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Encerra uma conta
    @PutMapping("/encerrar/{numeroConta}")
    public ResponseEntity<?> encerrarConta(@PathVariable String numeroConta) {
        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }

        try {
            conta.encerrarConta();
            return ResponseEntity.ok(conta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Faz PIX (transferência entre contas)
    @PostMapping("/pix")
    public ResponseEntity<?> realizarPix(@RequestBody Map<String, Object> pixRequest) {
        String numeroContaOrigem = (String) pixRequest.get("numeroContaOrigem");
        String numeroContaDestino = (String) pixRequest.get("numeroContaDestino");
        double valor = Double.parseDouble(pixRequest.get("valor").toString());

        Conta contaOrigem = repository.getNumeroConta(numeroContaOrigem);
        Conta contaDestino = repository.getNumeroConta(numeroContaDestino);

        if (contaOrigem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta de origem não encontrada");
        }
        if (contaDestino == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta de destino não encontrada");
        }

        try {
            contaOrigem.realizarPix(contaDestino, valor);
            return ResponseEntity.ok(contaOrigem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}