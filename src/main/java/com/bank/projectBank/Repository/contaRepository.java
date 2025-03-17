package com.bank.projectBank.Repository;

import org.springframework.stereotype.Repository;

import com.bank.projectBank.Model.Conta;

import java.util.ArrayList;
import java.util.List;

@Repository
public class contaRepository {
    private List<Conta> contas = new ArrayList<>();

    public List<Conta> getContas() {
        return contas;
    }

    public Conta getNumeroConta(String numeroConta) {
        return contas.stream()
                .filter(c -> c.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElse(null);
    }
    
    public Conta getCpfTitularConta(String cpfTitular) {
        return contas.stream()
                    .filter(c -> c.getCpfTitular().equals(cpfTitular))
                    .findFirst()
                    .orElse(null);
    }
    public void salvar(Conta conta) {
        contas.add(conta);
    }
}
