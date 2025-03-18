package com.bank.projectBank.Model;

import java.time.LocalDate;

public class Conta {
    private String numeroConta; // String para evitar erros como soma de ID
    private String numeroAgencia;
    private String nomeTitular;
    private LocalDate dataAbertura;
    private String cpfTitular;
    private double saldo;
    private Boolean ativo; // Ativo = True/ Desativado = False
    private String tipoConta;

    public Conta(String numero, String agencia, String nome, LocalDate data, String cpf, double saldo, Boolean ativo, String tipo) {
        this.numeroConta = numero;
        this.numeroAgencia = agencia;
        this.nomeTitular = nome;
        this.dataAbertura = data;
        this.cpfTitular = cpf;
        this.saldo = saldo;
        this.ativo = ativo;
        this.tipoConta = tipo;
    }

    // Validação da conta
    public void validarConta() {
        if (this.nomeTitular == null || this.nomeTitular.isEmpty()) {
            throw new IllegalArgumentException("Nome do titular é obrigatório");
        }
        if (this.cpfTitular == null || this.cpfTitular.isEmpty()) {
            throw new IllegalArgumentException("CPF do titular é obrigatório");
        }
        if (this.dataAbertura == null || this.dataAbertura.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de abertura deve ser hoje ou antes");
        }
        if (!this.tipoConta.equalsIgnoreCase("corrente") &&
                !this.tipoConta.equalsIgnoreCase("poupanca") &&
                !this.tipoConta.equalsIgnoreCase("salario")) {
            throw new IllegalArgumentException("O tipo de conta deve ser: Corrente, poupança ou salário");
        }
    }

    // Depósito
    public void deposito(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo");
        }
        this.saldo += valor;
    }

    // Saque
    public void saque(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo");
        }
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque");
        }
        this.saldo -= valor;
    }

    // PIX (Transferência entre contas)
    public void realizarPix(Conta contaDestino, double valor) {
        if (contaDestino == null) {
            throw new IllegalArgumentException("Conta de destino não encontrada");
        }
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o PIX");
        }
        this.saque(valor);
        contaDestino.deposito(valor);
    }

    // Encerrar conta
    public void encerrarConta() {
        if (!this.ativo) {
            throw new IllegalArgumentException("A conta já está encerrada");
        }
        this.ativo = false;
    }

    // Getters e Setters
    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }
}