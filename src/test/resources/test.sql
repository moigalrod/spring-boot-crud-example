DELETE FROM reserva;
INSERT INTO reserva (id, cancelable, cliente, fec_entrada, noches, fec_salida) VALUES 
(1, true, 'Ramon', '2020-01-17', 2, '2020-01-20' ),
(2, true, 'Ramon', '2020-01-17', 2, '2020-01-20' )
;