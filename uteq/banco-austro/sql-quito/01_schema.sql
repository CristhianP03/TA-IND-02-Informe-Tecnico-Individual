CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS cuentas (
    numero VARCHAR(20) PRIMARY KEY,
    cliente_id INTEGER NOT NULL REFERENCES clientes(id),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('ahorros', 'corriente')),
    saldo NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
    sucursal VARCHAR(20) NOT NULL DEFAULT 'Quito'
);

CREATE TABLE IF NOT EXISTS transacciones (
    id SERIAL PRIMARY KEY,
    cuenta_numero VARCHAR(20) NOT NULL REFERENCES cuentas(numero),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('deposito', 'retiro', 'transferencia')),
    monto NUMERIC(15, 2) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descripcion VARCHAR(200)
);

CREATE INDEX idx_cuentas_cliente ON cuentas(cliente_id);
CREATE INDEX idx_transacciones_cuenta ON transacciones(cuenta_numero);
