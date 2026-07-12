-- Quito: cuentas que empiezan con "17"
INSERT INTO clientes (id, nombre, direccion, telefono) VALUES
(4, 'Ana Martínez', 'Av. Amazonas, Quito', '0965432109'),
(5, 'Pedro Sánchez', 'Calle 6 de Diciembre, Quito', '0954321098'),
(6, 'Lucía Torres', 'Av. 10 de Agosto, Quito', '0943210987');

INSERT INTO cuentas (numero, cliente_id, tipo, saldo, sucursal) VALUES
('170001', 4, 'ahorros', 2500.00, 'Quito'),
('170002', 4, 'corriente', 4800.00, 'Quito'),
('170003', 5, 'ahorros', 1100.25, 'Quito'),
('170004', 6, 'corriente', 7800.50, 'Quito'),
('170005', 6, 'ahorros', 3400.00, 'Quito');

INSERT INTO transacciones (cuenta_numero, tipo, monto, descripcion) VALUES
('170001', 'deposito', 2000.00, 'Depósito inicial'),
('170002', 'deposito', 5000.00, 'Depósito nómina'),
('170002', 'retiro', 300.00, 'Retiro cajero automático'),
('170003', 'deposito', 1200.00, 'Apertura cuenta'),
('170005', 'transferencia', 1500.00, 'Transferencia recibida');
