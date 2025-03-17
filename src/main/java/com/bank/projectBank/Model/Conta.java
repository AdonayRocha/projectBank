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

    public void deposito(double valor) {
        this.saldo += valor;
    }

    public void saque(double valor) {
        if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente ou valor inválido");
        }
    }
    // Getters and Setters
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

    public double getSaque() {
        return this.saldo;
    }

    public void setSaque(double valor) {
        saque(valor);
    }
}