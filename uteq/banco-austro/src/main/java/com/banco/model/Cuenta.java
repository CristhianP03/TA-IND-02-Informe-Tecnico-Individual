package com.banco.model;

public class Cuenta {
    private String numero;
    private int clienteId;
    private String nombreCliente;
    private String tipo;
    private double saldo;
    private String sucursal;

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public String getSucursal() { return sucursal; }
    public void setSucursal(String sucursal) { this.sucursal = sucursal; }
}
