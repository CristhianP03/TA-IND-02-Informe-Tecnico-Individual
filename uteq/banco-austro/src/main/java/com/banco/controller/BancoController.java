package com.banco.controller;

import com.banco.model.Cliente;
import com.banco.model.Cuenta;
import com.banco.service.ConsultaDistribuidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/banco")
public class BancoController {

    private final ConsultaDistribuidaService service;

    public BancoController(ConsultaDistribuidaService service) {
        this.service = service;
    }

    @GetMapping("/saldo/{numero}")
    public ResponseEntity<?> consultarSaldo(@PathVariable String numero) {
        try {
            Cuenta cuenta = service.consultarSaldo(numero);
            if (cuenta == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(Map.of(
                    "numero", cuenta.getNumero(),
                    "cliente", cuenta.getNombreCliente(),
                    "tipo", cuenta.getTipo(),
                    "saldo", cuenta.getSaldo(),
                    "sucursal", cuenta.getSucursal()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(service.listarTodosLosClientes());
    }

    @PostMapping("/transferencia")
    public ResponseEntity<?> transferir(@RequestBody Map<String, Object> body) {
        try {
            String origen = (String) body.get("cuentaOrigen");
            String destino = (String) body.get("cuentaDestino");
            double monto = ((Number) body.get("monto")).doubleValue();
            String resultado = service.transferir(origen, destino, monto);
            return ResponseEntity.ok(Map.of("mensaje", resultado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error interno: " + e.getMessage()));
        }
    }
}
