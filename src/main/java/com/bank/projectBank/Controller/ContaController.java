package com.bank.projectBank.Controller;

import com.bank.projectBank.Model.Conta;
import com.bank.projectBank.Repository.contaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private contaRepository repository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Conta conta) {
        if (conta.getNomeTitular() == null || conta.getNomeTitular().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome do titular é obrigatório");
        }
        if (conta.getCpfTitular() == null || conta.getCpfTitular().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF do titular é obrigatório");
        }
        if (conta.getDataAbertura() == null || conta.getDataAbertura().isAfter(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de abertura deve ser hoje ou antes");
        }
        if (!conta.getTipoConta().equalsIgnoreCase("corrente") &&
                !conta.getTipoConta().equalsIgnoreCase("poupanca") &&
                !conta.getTipoConta().equalsIgnoreCase("salario")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O tipo de conta deve ser: Corrente, poupança ou salário");
        }

        repository.salvar(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> getContas() {
        List<Conta> contas = repository.getContas();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<?> getConta(@PathVariable String numeroConta) {
        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/cpf/{cpfTitular}")
    public ResponseEntity<?> getContaByCpf(@PathVariable String cpfTitular) {
        Conta conta = repository.getCpfTitularConta(cpfTitular);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada para o CPF fornecido");
        }
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/deposito")
    public ResponseEntity<?> deposito(@RequestBody Conta depositoRequest) {
        Conta conta = repository.getNumeroConta(depositoRequest.getNumeroConta());
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
        conta.deposito(depositoRequest.getSaldo());
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/saque")
    public ResponseEntity<?> saque(@RequestBody Conta saqueRequest) {
        Conta conta = repository.getNumeroConta(saqueRequest.getNumeroConta());
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
        if (conta.getSaldo() < saqueRequest.getSaldo()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente para realizar o saque");
        }
        conta.saque(saqueRequest.getSaldo());
        return ResponseEntity.ok(conta);
    }

    @PutMapping("/encerrar/{numeroConta}")
    public ResponseEntity<?> encerrarConta(@PathVariable String numeroConta) {
        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
        if (!conta.getAtivo()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A conta já está encerrada");
        }
        conta.setAtivo(false);
        return ResponseEntity.ok(conta);
    }

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
        if (contaOrigem.getSaldo() < valor) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente para realizar o PIX");
        }

        try {
            contaOrigem.saque(valor);
            contaDestino.deposito(valor);
            return ResponseEntity.ok(contaOrigem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}