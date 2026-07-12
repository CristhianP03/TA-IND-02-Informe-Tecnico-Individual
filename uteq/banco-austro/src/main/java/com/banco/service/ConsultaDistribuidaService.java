package com.banco.service;

import com.banco.model.Cliente;
import com.banco.model.Cuenta;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaDistribuidaService {

    private final JdbcTemplate cuencaJdbc;
    private final JdbcTemplate quitoJdbc;
    private final JdbcTemplate guayaquilJdbc;

    public ConsultaDistribuidaService(
            @Qualifier("cuencaJdbcTemplate") JdbcTemplate cuencaJdbc,
            @Qualifier("quitoJdbcTemplate") JdbcTemplate quitoJdbc,
            @Qualifier("guayaquilJdbcTemplate") JdbcTemplate guayaquilJdbc) {
        this.cuencaJdbc = cuencaJdbc;
        this.quitoJdbc = quitoJdbc;
        this.guayaquilJdbc = guayaquilJdbc;
    }

    public Cuenta consultarSaldo(String numeroCuenta) {
        JdbcTemplate jdbc = determinarOrigen(numeroCuenta);
        String sql = """
                SELECT c.numero, c.cliente_id, cl.nombre, c.tipo, c.saldo, c.sucursal
                FROM cuentas c
                JOIN clientes cl ON c.cliente_id = cl.id
                WHERE c.numero = ?
                """;
        List<Cuenta> resultados = jdbc.query(sql, (rs, rowNum) -> {
            Cuenta cuenta = new Cuenta();
            cuenta.setNumero(rs.getString("numero"));
            cuenta.setClienteId(rs.getInt("cliente_id"));
            cuenta.setNombreCliente(rs.getString("nombre"));
            cuenta.setTipo(rs.getString("tipo"));
            cuenta.setSaldo(rs.getDouble("saldo"));
            cuenta.setSucursal(rs.getString("sucursal"));
            return cuenta;
        }, numeroCuenta);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public String transferir(String cuentaOrigen, String cuentaDestino, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        JdbcTemplate jdbcOrigen = determinarOrigen(cuentaOrigen);
        JdbcTemplate jdbcDestino = determinarOrigen(cuentaDestino);

        double saldoOrigen = obtenerSaldo(jdbcOrigen, cuentaOrigen);
        if (saldoOrigen < monto) {
            throw new IllegalArgumentException("Saldo insuficiente. Disponible: " + saldoOrigen);
        }
        obtenerSaldo(jdbcDestino, cuentaDestino);

        jdbcOrigen.update("UPDATE cuentas SET saldo = saldo - ? WHERE numero = ?", monto, cuentaOrigen);
        jdbcDestino.update("UPDATE cuentas SET saldo = saldo + ? WHERE numero = ?", monto, cuentaDestino);

        String descOrigen = "Transferencia enviada a " + cuentaDestino;
        String descDestino = "Transferencia recibida de " + cuentaOrigen;
        jdbcOrigen.update("INSERT INTO transacciones (cuenta_numero, tipo, monto, descripcion) VALUES (?, 'retiro', ?, ?)",
                cuentaOrigen, monto, descOrigen);
        jdbcDestino.update("INSERT INTO transacciones (cuenta_numero, tipo, monto, descripcion) VALUES (?, 'deposito', ?, ?)",
                cuentaDestino, monto, descDestino);

        return "Transferencia de " + monto + " realizada: " + cuentaOrigen + " -> " + cuentaDestino;
    }

    private double obtenerSaldo(JdbcTemplate jdbc, String numeroCuenta) {
        String sql = "SELECT saldo FROM cuentas WHERE numero = ?";
        List<Double> saldos = jdbc.query(sql, (rs, rowNum) -> rs.getDouble("saldo"), numeroCuenta);
        if (saldos.isEmpty()) {
            throw new IllegalArgumentException("Cuenta no encontrada: " + numeroCuenta);
        }
        return saldos.get(0);
    }

    public List<Cliente> listarTodosLosClientes() {
        List<Cliente> todos = new ArrayList<>();
        try {
            todos.addAll(consultarClientes(cuencaJdbc, "Cuenca"));
        } catch (Exception e) {
            System.err.println("Nodo Cuenca no disponible: " + e.getMessage());
        }
        try {
            todos.addAll(consultarClientes(quitoJdbc, "Quito"));
        } catch (Exception e) {
            System.err.println("Nodo Quito no disponible: " + e.getMessage());
        }
        try {
            todos.addAll(consultarClientes(guayaquilJdbc, "Guayaquil"));
        } catch (Exception e) {
            System.err.println("Nodo Guayaquil no disponible: " + e.getMessage());
        }
        return todos;
    }

    private List<Cliente> consultarClientes(JdbcTemplate jdbc, String sucursal) {
        String sql = "SELECT id, nombre, direccion, telefono FROM clientes";
        return jdbc.query(sql, (rs, rowNum) -> {
            Cliente cliente = new Cliente();
            cliente.setId(rs.getInt("id"));
            cliente.setNombre(rs.getString("nombre"));
            cliente.setDireccion(rs.getString("direccion"));
            cliente.setTelefono(rs.getString("telefono"));
            return cliente;
        });
    }

    private JdbcTemplate determinarOrigen(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new IllegalArgumentException("Número de cuenta inválido");
        }
        if (numeroCuenta.startsWith("22")) {
            return cuencaJdbc;
        } else if (numeroCuenta.startsWith("17")) {
            return quitoJdbc;
        } else if (numeroCuenta.startsWith("09")) {
            return guayaquilJdbc;
        } else {
            throw new IllegalArgumentException(
                    "Número de cuenta no reconocido: " + numeroCuenta +
                    ". Cuenca: 22, Quito: 17, Guayaquil: 09");
        }
    }
}
