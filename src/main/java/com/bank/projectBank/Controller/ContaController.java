package com.bank.projectBank.Controller;

import com.bank.projectBank.Model.Conta;
import com.bank.projectBank.Repository.contaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private contaRepository repository;

    @PostMapping
    public Conta create(@RequestBody Conta conta) {
        if (conta.getNomeTitular() == null || conta.getNomeTitular().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.RESET_CONTENT, "Nome do titular é obrigatório");
        }
        if (conta.getCpfTitular() == null || conta.getCpfTitular().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.RESET_CONTENT, "CPF do titular é obrigatório");
        }
        if (conta.getDataAbertura() == null || conta.getDataAbertura().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.RESET_CONTENT, "Data de abertura deve ser hoje ou antes");
        }
        if (!conta.getTipoConta().equalsIgnoreCase("corrente") && 
        !conta.getTipoConta().equalsIgnoreCase("poupanca") &&
        !conta.getTipoConta().equalsIgnoreCase("salario")) {
            throw new ResponseStatusException(HttpStatus.RESET_CONTENT, "O tipo de conta deve ser: Corrente, poupança ou salário");
        }
    repository.salvar(conta);
    return conta;
    }

    @GetMapping
    public List<Conta> getContas() {
        return repository.getContas();
    }

    @GetMapping("/{numeroConta}")
    public Conta getConta(@PathVariable String numeroConta) {
        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada");
        }
        return conta;
    }

    @PutMapping("/{numeroConta}/deposito/{valor}")
    public Conta deposito(@PathVariable String numeroConta, @PathVariable double valor) {
        Conta conta = repository.getNumeroConta(numeroConta);
        if (conta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada");
        }
        conta.deposito(valor);
        return conta;
    }

}
