
delete from PaseadorEntity_HorarioEntity;
delete from HoraHotelEntity_ContratoHotelEntity;
delete from CalificacionEntity;
delete from PuntoEntity;
delete from ContratoPaseoEntity;
delete from HorarioEntity;
delete from PaseoEntity;
delete from ContratoHotelEntity;
delete from PerroEntity;
delete from PagoPaseadorEntity;
delete from PagoClienteEntity;
delete from RecorridoEntity;
delete from HoraHotelEntity;
delete from PaseadorEntity;
delete from ClienteEntity;


insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (100,'1007861956', 'j.perez@correo.com', 'Juan Perez', '0377272519', 'https://pbs.twimg.com/profile_images/1227369142921158656/f7eWMj3L_400x400.jpg');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (101,'1007861957', 'f.martinez@correo.com', 'Felipe Martínez', '0377272511', 'https://www.grupoeducativogalileo.com/media/k2/items/cache/867519228d1d5325856fc61d710ded0e_XL.jpg');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (102,'1007861958', 'm.rodriguez@correo.com', 'María Rodríguez', '0377272512', 'https://colombia.unir.net/wp-content/uploads/claustro/maria-rodriguez-rabadan-benito-1251624.jpg');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (103,'1007861959', 'm.melendez@correo.com', 'Mario Meléndez', '0377272513', 'https://pbs.twimg.com/profile_images/1177086492478517253/xsb366H2_400x400.jpg');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (104,'1007861950', 'd.monsalve@correo.com', 'Daniel Monsalve', '0377272514', 'https://pbs.twimg.com/profile_images/555623502779150336/vqDvHnEK.png');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (105,'1007861956', 'a.plata@correo.com', 'Aura Plata', '0377272519', 'https://ui-avatars.com/api/?background=e26e2d&color=fff&size=500&bold=true&name=Aura%20Plata');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (106,'1007861957', 'c.mendez@correo.com', 'Christian Méndez', '0377272511', 'https://pbs.twimg.com/profile_images/1183412317263929344/ylYCpfPp_400x400.jpg');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (107,'1007861958', 'j.valdivieso@correo.com', 'Juan Valdivieso', '0377272512', 'https://copublicitarias.com/wp-content/uploads/2018/03/juan-camilo-valdivieso-nuevo-director-creativo-en-alma-ddb.jpg');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (108,'1007861959', 'd.bottia@correo.com', 'Daniela Bottia', '0377272513', 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQr2cJy-7fJoK60Mhh2PKGFOPN6n1HUMbOiSuMj0E_aBz71Ya5E&usqp=CAU');
insert into ClienteEntity (id, identificacion, correo, nombre, telefono, imagen) values (109,'1007861950', 'j.torres@correo.com', 'Jose Torres', '0377272514', 'https://pbs.twimg.com/profile_images/913412757319503872/rUSVd2z0.jpg');

insert into PaseadorEntity (id, arl, calificacionGlobal, correo, imagen, cuentaBancaria, eps, identificacion, nombre, numeroCalificaciones, telefono) values (100, 'MAPFRE Seguros', 0.0, 'jesuseche2541@gmail.com', 'https://blog.uexternado.edu.co/wp-content/uploads/sites/13/2015/02/xavier_cardenas.jpg', '0123457824', 'Compensar EPS', '7543210', 'Jesús Echeverry', 1, '3101012345');
insert into PaseadorEntity (id, arl, calificacionGlobal, correo, imagen, cuentaBancaria, eps, identificacion, nombre, numeroCalificaciones, telefono) values (101, 'Liberty Seguros', 2.0, 'camigom12@yahoo.com', 'https://women.volleybox.net/media/upload/players/1556400114Qi5lW.png', '6789114650', 'Nueva EPS', '100219876', 'Camila Gómez', 1, '3121678910');
insert into PaseadorEntity (id, arl, calificacionGlobal, correo, imagen, cuentaBancaria, eps, identificacion, nombre, numeroCalificaciones, telefono) values (102, 'Colpatria', 3.0, 'danielggg17@outlook.com', 'https://revistamutt.com/wp-content/uploads/2018/08/Daniel-Gonzalez-.jpg', '11121157483', 'Coomeva EPS', '7812111', 'Daniel Gonzalez', 1, '311251213');
insert into PaseadorEntity (id, arl, calificacionGlobal, correo, imagen, cuentaBancaria, eps, identificacion, nombre, numeroCalificaciones, telefono) values (103, 'Seguros Bolivar', 4.0, 'jmarquez8@gmail.com', 'https://pbs.twimg.com/media/EW2bFlPXQAArSDL.jpg', '1415146516', 'Sanitas Internacional', '102514141', 'Julian Marquez', 1, '3211141516');
insert into PaseadorEntity (id, arl, calificacionGlobal, correo, imagen, cuentaBancaria, eps, identificacion, nombre, numeroCalificaciones, telefono) values (104, 'Liberty Seguros', 4.0, 'camigarcia9@gmail.com', 'https://pbs.twimg.com/profile_images/957079837658796032/nIFSmMTI.jpg', '1718125919', 'Medimás', '819871', 'Camilo García', 1, '3152171819');

insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (100, 1, 5, 15000, '2017-11-13 16:30:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (101, 1, 8, 5000, '2020-03-30 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (102, 1, 3, 25000, '2019-11-23 06:30:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (103, 1, 6, 10000, '2020-11-14 14:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (104, 1, 7, 12000, '2020-08-07 16:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (105, 1, 8, 5000, '2020-05-30 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (106, 1, 8, 8000, '2020-05-10 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (107, 1, 8, 7500, '2020-05-15 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (108, 1, 8, 15000, '2020-05-02 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (109, 1, 8, 25000, '2020-05-08 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (110, 1, 8, 20000, '2020-05-22 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (111, 1, 8, 18000, '2020-05-30 17:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (112, 1, 8, 11000, '2020-06-30 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (113, 1, 8, 10000, '2020-06-10 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (114, 1, 8, 10000, '2020-06-15 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (115, 1, 8, 5000, '2020-06-02 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (116, 1, 8, 8000, '2020-06-08 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (117, 1, 8, 8500, '2020-06-22 11:00:00');
insert into HoraHotelEntity(id, disponible, cupoMaximo, costoBase, dia) values (118, 1, 8, 6000, '2020-06-30 17:00:00');

insert into RecorridoEntity (id, calificacionGlobal, numeroCalificaciones) values (100, 0.1, 1);
insert into RecorridoEntity (id, calificacionGlobal, numeroCalificaciones) values (101, 4.5, 1);
insert into RecorridoEntity (id, calificacionGlobal, numeroCalificaciones) values (102, 2.0, 1);
insert into RecorridoEntity (id, calificacionGlobal, numeroCalificaciones) values (103, 4.8, 1);
insert into RecorridoEntity (id, calificacionGlobal, numeroCalificaciones) values (104, 1.8, 1);

insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (200, 15000, '012345', 100, 'EFECTIVO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (201, 30000, '567890', 101, 'PSE');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (202, 35000, '101112', 102, 'CRÉDITO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (203, 10000, '131415', 103, 'EFECTIVO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (204, 22000, '161718', 104, 'PSE');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (205, 20500, '192021', 105, 'EFECTIVO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (206, 15000, '222324', 106, 'PSE');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (207, 52500, '252627', 107, 'CRÉDITO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (208, 48000, '282930', 108, 'EFECTIVO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (209, 34000, '313233', 109, 'PSE');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (210, 20000, '343536', 100, 'EFECTIVO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (211, 30000, '373839', 101, 'PSE');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (212, 26000, '404142', 102, 'CRÉDITO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (213, 52000, '434445', 103, 'EFECTIVO');
insert into PagoClienteEntity (id, monto, referencia, cliente_id, mediopago) values (214, 20500, '464748', 104, 'PSE');

insert into PagoPaseadorEntity(id, fechaLimite, monto, referencia, paseador_id) values (100, '2020-12-23 00:00:00', 150000, '012345', 100);
insert into PagoPaseadorEntity(id, fechaLimite, monto, referencia, paseador_id) values (101, '2020-01-24 00:00:00', 250000, '678910', 101);
insert into PagoPaseadorEntity(id, fechaLimite, monto, referencia, paseador_id) values (102, '2020-02-25 00:00:00', 350000, '111213', 104);
insert into PagoPaseadorEntity(id, fechaLimite, monto, referencia, paseador_id) values (103, '2020-03-26 00:00:00', 450000, '141516', 104);
insert into PagoPaseadorEntity(id, fechaLimite, monto, referencia, paseador_id) values (104, '2020-04-27 00:00:00', 550000, '171819', 104);

insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (400, 3, '1234', 'Toby', 'Pastor Aleman',100, 'https://www.hola.com/imagenes/estar-bien/20191004150785/pastor-aleman-raza-de-perro-caracteristicas/0-728-57/raza-de-perro-pastor-aleman-m.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (401, 5, '1235', 'Luna', 'Bulldog',101, 'https://www.hola.com/imagenes/estar-bien/20200117158387/razas-de-perro-bulldog/0-770-345/bulldog-m.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (402, 2, '1236', 'Sasha', 'Husky Siberiano',101, 'https://vignette.wikia.nocookie.net/reinoanimalia/images/a/a9/Husky_siberiano_20.png/revision/latest?cb=20150513044750&path-prefix=es');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (403, 4, '1237', 'Brenda', 'Salchicha',102, 'https://i.pinimg.com/736x/94/18/50/941850566a8c1dac3f304f25e3e8fa05.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (404, 3, '1238', 'Chester', 'Golden Retriever',102, 'https://t2.ea.ltmcdn.com/es/images/8/9/4/img_cuidados_del_pelo_del_golden_retriever_20498_600_square.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (405, 6, '1239', 'Maddy', 'Pumeranian',103, 'https://www.espree.com/sites/default/files/2019-10/Pomeranian.png');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (406, 3, '1240', 'Max', 'Labrador Dorado',104, 'https://vignette.wikia.nocookie.net/wikideanimales/images/3/36/Labrador_03.jpg/revision/latest/scale-to-width-down/340?cb=20110422155543&path-prefix=es');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (407, 3, '1241', 'Jack', 'Lobo Siberiano',104, 'https://live.hsmob.io/storage/images/wakyma.com/wakyma.com_diferencias-entre-el-alaskan-malamute-y-el-husky-siberiano1-1024x885.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (408, 1, '1242', 'Bony', 'French Poodle',105, 'https://miro.medium.com/max/1080/1*YmhrhHaHY3B3ugjKemZ6cQ.png');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (409, 8, '1243', 'Lucas', 'Dalmata',105, 'https://t2.ea.ltmcdn.com/es/razas/8/5/0/img_58_dalmata_0_orig.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (410, 1, '1244', 'Mateo', 'Labrador Negro',105, 'https://i.pinimg.com/474x/8d/5c/91/8d5c91b33d0bdb337353a4256b50e887.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (411, 1, '1245', 'Simba', 'Gran Danés',106, 'https://i.pinimg.com/originals/b8/1c/06/b81c06329a721121e6f85040638a1b6b.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (412, 7, '1246', 'Bongo', 'Labrador Chocolate',107, 'https://image.freepik.com/foto-gratis/retrato-estudio-labrador-retriever_105506-7.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (413, 5, '1247', 'Kira', 'Boxer',107, 'https://www.petfanmx.com/imageHandler/g.ashx?s=repo&ID=1847');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (414, 5, '1248', 'Rocky', 'Dóberman',107, 'https://www.mividaporunperro.com/wp-content/uploads/2019/03/doberman-raza-de-perros-grandes.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (415, 7, '1249', 'Nala', 'Shih Tzu',108, 'https://t2.ea.ltmcdn.com/es/images/7/7/6/img_5_tipos_de_cortes_de_pelo_para_un_shih_tzu_22677_600_square.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (416, 2, '1250', 'Atos', 'Border Collie',108, 'https://www.clinicaraza.com/hubfs/border-collie-501992_640.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (417, 6, '1251', 'Tomas', 'Chow Chow',108, 'https://perros.review/wp-content/uploads/2017/09/raza-de-perros-chow-chow_opt-compressor-1-1-compressor.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (418, 6, '1252', 'Dante', 'Rottweiler',109, 'https://arneses-para-perros.es/images/stories/virtuemart/product/arnes-rottweiler-h7-0.jpg');
insert into PerroEntity (id, edad, idPerro, nombre, raza, propietario_id, imagen) values (419, 2, '1253', 'Cassie', 'Chihuahua',109, 'https://cdn2.actitudfem.com/media/files/styles/gallerie_carousel/public/images/2015/11/notaloschihuahuas.jpg');

insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (100, 'No consumir chocolate', 15000, 100, 400, 200);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (101, 'No dejar que le de frio', 30000, 101, 401, 201);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (102, 'No dejar solo', 35000, 102, 403, 202);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (103, 'Hacer ejercicio', 10000, 103, 405, 203);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (104, 'Solo comer 2 veces al dia', 22000, 104, 407, 204);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (105, 'No consumir carnes', 20500, 105, 410, 205);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (106, 'No pasear si hace calor', 15000, 106, 411, 206);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (107, 'Ninguno', 52500, 107, 414, 207);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (108, 'Revisar la herida', 48000, 108, 417, 208);
insert into ContratoHotelEntity(id, cuidadoEspecial, costo, cliente_id, perro_id, pago_id) values (109, 'Solo comer concentrado', 34000, 109, 419, 209);

insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (100,100);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (101,101);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (101,102);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (102,102);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (102,103);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (103,103);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (104,103);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (104,104);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (105,105);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (105,106);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (105,107);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (106,108);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (107,107);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (107,109);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (107,110);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (108,110);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (108,111);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (108,114);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (109,112);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (109,113);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (109,115);
insert into HoraHotelEntity_ContratoHotelEntity(contratoshotel_id, horashotel_id) values (109,116);

insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (1, 20000, 7, 1, 60,'2020-11-15 10:00:00', 100, 100, 100);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (2, 20000, 10, 0, 90,'2020-11-15 11:00:00', 101, 101, 101);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (3, 50000, 3, 0, 30,'2020-12-20 09:00:00', 102, 102, 102);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (4, 10000, 5, 1, 30,'2020-10-05 18:00:00', 103, 103, 103);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (5, 60000, 1, 1, 90,'2020-09-10 11:00:00', 104, 104, 104);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (6, 45000, 2, 1, 60,'2020-11-15 10:00:00', 100, 100, 100);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (7, 25000, 8, 0, 90,'2020-12-24 17:00:00', 101, 101, 101);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (8, 55000, 10, 0, 30,'2020-12-20 09:00:00', 102, 102, 102);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (9, 15000, 10, 1, 30,'2020-10-05 18:00:00', 103, 103, 103);
insert into PaseoEntity (id, costo, cupoMaximo, disponible, duracion ,horaInicio, pagopaseador_id, paseador_id, recorrido_id) values (10, 15000, 6, 1, 9045,'2020-10-05 19:00:00', 104, 104, 104);

insert into HorarioEntity (id, dia, duracion, ocupado, paseo1_id, paseo2_id) values (500, '2020-12-24 00:00:00', 60, 1, 1, 2);
insert into HorarioEntity (id, dia, duracion, ocupado, paseo1_id, paseo2_id ) values (501, '2020-11-23 00:00:00', 30, 0, 9, 10);
insert into HorarioEntity (id, dia, duracion, ocupado, paseo1_id) values (502, '2020-11-29 00:00:00', 30, 0, 3);
insert into HorarioEntity (id, dia, duracion, ocupado, paseo1_id) values (503, '2020-10-23 00:00:00', 90, 1, 4);
insert into HorarioEntity (id, dia, duracion, ocupado, paseo1_id) values (504, '2020-12-20 00:00:00', 60, 1, 5);

insert into ContratoPaseoEntity (id, horaEncuentro, cliente_id, paseo_id, perro_id,  pago_id) values(300, '2017-09-13 16:30:00', 100, 1, 400, 200 );
insert into ContratoPaseoEntity (id, horaEncuentro, cliente_id, paseo_id, perro_id,  pago_id) values(301, '2017-07-13 17:30:00', 101, 2, 401, 201 );
insert into ContratoPaseoEntity (id, horaEncuentro, cliente_id, paseo_id, perro_id,  pago_id) values(302, '2015-12-13 06:30:00', 102, 3, 402, 202 );
insert into ContratoPaseoEntity (id, horaEncuentro, cliente_id, paseo_id, perro_id,  pago_id) values(303, '2015-12-10 06:30:00', 103, 4, 403, 203 );
insert into ContratoPaseoEntity (id, horaEncuentro, cliente_id, paseo_id, perro_id,  pago_id) values(304, '2018-01-13 10:00:00', 104, 5, 404, 204 );

insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (100, -74.094731, 4.660708, 1, 100, 300);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (101, -74.094487, 4.661673, 2, 100, 300);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (102, -74.093173, 4.661587, 3, 100, 300);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (103, -74.093092, 4.659921, 4, 100, 300);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (104, -74.093121, 4.653384,  5, 100, 300);

insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (105, -74.050679, 4.670334, 1, 101, 301);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (106, -74.050540, 4.670575, 2, 101, 301);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (107, -74.050306, 4.670530, 3, 101, 301);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (108, -74.050134, 4.670447, 4, 101, 301);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (109, -74.050008, 4.670353, 5, 101, 301);

insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (110, -74.056381, 4.674292, 1, 102, 302);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (111, -74.055911, 4.674214, 2, 102, 302);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (112, -74.055426, 4.674086, 3, 102, 302);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (113, -74.054603, 4.673672, 4, 102, 302);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (114, -74.054088, 4.673261, 5, 102, 302);

insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (115, -74.062439, 4.622010,  1, 103, 303);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (116,  -74.063152, 4.621053, 2, 103, 303);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (117,  -74.063721,  4.622015, 3, 103, 303);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (118, -74.064472, 4.622614, 4, 103, 303);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (119, -74.065894,  4.623128,  5, 103, 303);

insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (120, -74.081475, 4.597680, 1, 104, 304);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (121, -74.081711, 4.597373, 2, 104, 304);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (122, -74.081805, 4.597193, 3, 104, 304);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (123,  -74.081901, 4.597033,4, 104, 304);
insert into PuntoEntity (id, longitud, latitud, posicion, recorrido_id, contratopaseo_id) values (124,  -74.082009,4.596922, 5, 104, 304);


insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id ) values (100, 'Pésimo hotel, mal manejo los perros por parte de este', 0.0,100,null,null );
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (101, 'Los encargados no mostraron interes en mi perro', 1.0, 101,null,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (102, 'El sitio se veía inseguro', 2.0, 102,null,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (103, 'Pudo haber sido peor el sitio', 0.7, 103,null,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (104, 'No lo volvería a llevar a ese hotel a mi perro', 1.5, 104,null,null);

insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (105, 'Mi perro llegó herido porque el sitio parece ser 0 acogedor', 0.3, null,300,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (106, 'El sitio es peligroso y mi mascota llegó muy estresada', 2.0, null,301,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (107, 'Pueden mejorar, mejores sitios para pasear existen en Bogotá', 3.9, null,302,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (108, 'Muy bien, su servicio es muy bueno, los volvería a contratar sin pensarlo', 4.2, null,303,null);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (109, 'Muy buen servicio, mi madcota parece que lo disfrutó mucho', 4.2, null,304,null);

insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (110, 'No me gustó que el sitio estuviese enlodado, mi perro llegó muy sucio', 3.0, null,null,300);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (111, 'El paseo realizado estuvo estupendo, 100% recomendado', 4.9, null,null,301);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (112, 'Llegó tarde el paseador a la hora acordada', 2.5, null,null,302);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (113, 'Una completa catastrófe, nunca volvería', 0.4, null,null,303);
insert into CalificacionEntity (id, comentario, valoracion, contratohotel_id, contratopaseador_id, contratorecorrido_id) values (114, 'Muy bien todo, solo no me gustó que se hayan pasado de la hora acordada y lo lejos del lugar', 3.8, null,null,304);

insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (100, 500);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (101, 500);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (101, 501);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (102, 500);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (102, 501);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (102, 502);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (103, 500);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (103, 501);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (103, 502);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (103, 503);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (104, 500);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (104, 501);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (104, 502);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (104, 503);
insert into PaseadorEntity_HorarioEntity (paseador_id, horariosDisponibles_id) values (104, 504);
