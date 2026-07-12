-- Cuenca: cuentas que empiezan con "22"
INSERT INTO clientes (id, nombre, direccion, telefono) VALUES
(1, 'Carlos Pérez', 'Av. República, Cuenca', '0987654321'),
(2, 'María Gómez', 'Calle Larga, Cuenca', '0998765432'),
(3, 'Juan López', 'Av. Solano, Cuenca', '0976543210');

INSERT INTO cuentas (numero, cliente_id, tipo, saldo, sucursal) VALUES
('220001', 1, 'ahorros', 1500.00, 'Cuenca'),
('220002', 1, 'corriente', 3200.50, 'Cuenca'),
('220003', 2, 'ahorros', 850.75, 'Cuenca'),
('220004', 3, 'corriente', 12000.00, 'Cuenca');

INSERT INTO transacciones (cuenta_numero, tipo, monto, descripcion) VALUES
('220001', 'deposito', 500.00, 'Depósito inicial'),
('220001', 'retiro', 200.00, 'Retiro cajero'),
('220002', 'deposito', 3000.00, 'Depósito nómina'),
('220003', 'deposito', 1000.00, 'Apertura cuenta'),
('220004', 'transferencia', 5000.00, 'Transferencia recibida');
