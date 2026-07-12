-- Guayaquil: cuentas que empiezan con "09"
INSERT INTO clientes (id, nombre, direccion, telefono) VALUES
(7, 'Roberto Vera', 'Av. 9 de Octubre, Guayaquil', '0932109876'),
(8, 'Laura Campos', 'Malecón 2000, Guayaquil', '0921098765'),
(9, 'Fernando Reyes', 'Av. Francisco de Orellana, Guayaquil', '0910987654');

INSERT INTO cuentas (numero, cliente_id, tipo, saldo, sucursal) VALUES
('090001', 7, 'ahorros', 3200.00, 'Guayaquil'),
('090002', 7, 'corriente', 5600.75, 'Guayaquil'),
('090003', 8, 'ahorros', 1800.50, 'Guayaquil'),
('090004', 9, 'corriente', 9500.00, 'Guayaquil'),
('090005', 9, 'ahorros', 4200.25, 'Guayaquil');

INSERT INTO transacciones (cuenta_numero, tipo, monto, descripcion) VALUES
('090001', 'deposito', 2500.00, 'Depósito inicial'),
('090002', 'deposito', 4000.00, 'Depósito nómina'),
('090002', 'retiro', 500.00, 'Retiro cajero automático'),
('090003', 'deposito', 1500.00, 'Apertura cuenta'),
('090005', 'transferencia', 2000.00, 'Transferencia recibida');
