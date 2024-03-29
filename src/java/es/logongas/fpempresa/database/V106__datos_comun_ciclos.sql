INSERT INTO `leyeducativa` (`idLeyEducativa`, `descripcion`) VALUES
	(1, 'LOGSE'),
	(2, 'LOE');

INSERT INTO `familia` (`idFamilia`, `descripcion`) VALUES
	(1, 'Actividades Físicas y Deportivas'),
	(2, 'Administración y Gestión'),
	(3, 'Agraria'),
	(4, 'Artes Gráficas'),
	(5, 'Comercio y Marketing'),
	(6, 'Edificación y Obra Civil'),
	(7, 'Electricidad y Electrónica'),
	(8, 'Fabricación Mecánica'),
	(9, 'Hostelería y Turismo'),
	(10, 'Imagen Personal'),
	(11, 'Imagen y Sonido'),
	(12, 'Industrias Alimentarias'),
	(13, 'Industrias Extractivas'),
	(14, 'Informática y Comunicaciones'),
	(15, 'Instalación y Mantenimiento'),
	(16, 'Madera, Mueble y Corcho'),
	(17, 'Marítimo - Pesquera'),
	(18, 'Química'),
	(19, 'Sanidad'),
	(20, 'Seguridad y Medio Ambiente'),
	(21, 'Servicios Socioculturales y a la Comunidad'),
	(22, 'Textil, Confección y Piel'),
	(23, 'Transporte y Mantenimiento de Vehículos'),
	(24, 'Vidrio y Cerámica'),
	(25, 'Artes y Artesanías'),
	(26, 'Energía y Agua');


INSERT INTO `grado` (`idGrado`, `descripcion`) VALUES
	(1, 'FP Básica'),
	(2, 'Grado medio'),
	(3, 'Grado superior');

INSERT INTO `ciclo` (`idCiclo`, `descripcion`, `idFamilia`, `idGrado`, `idLeyEducativa`) VALUES
	(1, 'Conducción de Actividades Físico-deportivas en el Medio Natural', 1, 2, 1),
	(2, 'Gestión Administrativa LOGSE', 2, 2, 1),
	(3, 'Gestión Administrativa', 2, 2, 2),
	(4, 'Explotaciones Agrarias Extensivas', 3, 2, 1),
	(5, 'Explotaciones Agrícolas Intensivas', 3, 2, 1),
	(6, 'Explotaciones Ganaderas', 3, 2, 1),
	(7, 'Jardinería', 3, 2, 1),
	(8, 'Trabajos Forestales y Conservación del Medio Natural', 3, 2, 1),
	(9, 'Aprovechamiento y Conservación del Medio Natural', 3, 2, 2),
	(10, 'Jardinería y Floristería', 3, 2, 2),
	(11, 'Producción Agroecológica', 3, 2, 2),
	(12, 'Producción Agropecuaria', 3, 2, 2),
	(13, 'Encuadernación y Manipulados de Papel y Cartón', 4, 2, 1),
	(14, 'Impresión en Artes Gráficas', 4, 2, 1),
	(15, 'Preimpresión en Artes Gráficas', 4, 2, 1),
	(16, 'Impresión Gráfica', 4, 2, 2),
	(17, 'Postimpresión y Acabados Gráficos', 4, 2, 2),
	(18, 'Preimpresión Digital', 4, 2, 2),
	(19, 'Comercio', 5, 2, 1),
	(20, 'Actividades Comerciales', 5, 2, 2),
	(21, 'Acabados de Construcción', 6, 2, 1),
	(22, 'Obras de Albañilería', 6, 2, 1),
	(23, 'Obras de Hormigón', 6, 2, 1),
	(24, 'Operación y Mantenimiento de Maquinaria de Construcción', 6, 2, 1),
	(25, 'Construccion', 6, 2, 2),
	(26, 'Obras de Interior, Decoración y Rehabilitación', 6, 2, 2),
	(27, 'Equipos Electrónicos de Consumo', 7, 2, 1),
	(28, 'Equipos e Instalaciones Electrotécnicas', 7, 2, 1),
	(29, 'Instalaciones Eléctricas y Automáticas', 7, 2, 2),
	(30, 'Instalaciones de Telecomunicaciones', 7, 2, 2),
	(31, 'Fundición', 8, 2, 1),
	(32, 'Joyería', 8, 2, 1),
	(33, 'Mecanizado LOGSE', 8, 2, 1),
	(34, 'Mecanizado', 8, 2, 2),
	(35, 'Soldadura y Caldereria LOGSE', 8, 2, 1),
	(36, 'Soldadura y Caldereria', 8, 2, 2),
	(37, 'Tratamientos Superficiales y Térmicos', 8, 2, 1),
	(38, 'Conformado por moldeo de metales y polímeros', 8, 2, 2),
	(39, 'Pastelería y Panadería', 9, 2, 1),
	(40, 'Servicios de Restaurante y Bar', 9, 2, 1),
	(41, 'Cocina y Gastronomía', 9, 2, 2),
	(42, 'Servicios en Restauración', 9, 2, 2),
	(43, 'Cocina', 9, 2, 1),
	(44, 'Peluquería', 10, 2, 1),
	(45, 'Estética y Belleza', 10, 2, 2),
	(46, 'Estética Personal Decorativa', 10, 2, 2),
	(47, 'Caracterización', 10, 2, 1),
	(48, 'Peluquería y Cosmética Capilar', 10, 2, 2),
	(49, 'Laboratorio de Imagen', 11, 2, 1),
	(50, 'Video Disc-Jockey y Sonido', 11, 2, 2),
	(51, 'Conservería Vegetal, Cárnica y de Pescado', 12, 2, 1),
	(52, 'Elaboración de Productos Lácteos', 12, 2, 1),
	(53, 'Elaboración de Aceites y jugos', 12, 2, 1),
	(54, 'Elaboración de Productos Alimenticios', 12, 2, 2),
	(55, 'Matadero y Carnicería-Charcutería', 12, 2, 1),
	(56, 'Molinería e Industrias Cerealistas', 12, 2, 1),
	(57, 'Panificación y Repostería', 12, 2, 1),
	(58, 'Aceites de Oliva y Vinos', 12, 2, 2),
	(59, 'Elaboración de Vinos y Otras Bebidas', 12, 2, 1),
	(60, 'Panadería, Repostería y Confitería', 12, 2, 2),
	(61, 'Excavaciones y Sondeos', 13, 2, 2),
	(62, 'Piedra Natural', 13, 2, 2),
	(63, 'Explotación de Sistemas Informáticos', 14, 2, 1),
	(64, 'Sistemas Microinformáticos y Redes', 14, 2, 2),
	(65, 'Mantenimiento Electromecánico', 15, 2, 2),
	(66, 'Instalación y Mantenimiento Electromecánico de Maquinaria y Conducción de Líneas', 15, 2, 1),
	(67, 'Mantenimiento Ferroviario', 15, 2, 1),
	(68, 'Montaje y Mantenimiento de Instalaciones de Frío, Climatización y Producción de Calor', 15, 2, 1),
	(69, 'Instalaciones Frigoríficas y de Climatización', 15, 2, 2),
	(70, 'Instalaciones de Producción de Calor', 15, 2, 2),
	(71, 'Fabricación Industrial de Carpintería y Mueble', 16, 2, 1),
	(72, 'Fabricación a Medida e Instalación de Carpintería y Mueble', 16, 2, 1),
	(73, 'Transformación de Madera y Corcho', 16, 2, 1),
	(74, 'Carpintería y Mueble', 16, 2, 2),
	(75, 'Instalación y Amueblamiento', 16, 2, 2),
	(76, 'Operación, Control y Mantenimiento de Máquinas e Instalaciones del Buque', 17, 2, 1),
	(77, 'Mantenimiento y Control de la Maquinaria de Buques y Embarcaciones', 17, 2, 2),
	(78, 'Navegación y Pesca de Litoral', 17, 2, 2),
	(79, 'Operaciones Subacuáticas e Hiperbáricas', 17, 2, 2),
	(80, 'Buceo de Media Profundidad', 17, 2, 1),
	(81, 'Pesca y Transporte Marítimo', 17, 2, 1),
	(82, 'Cultivos Acuícolas', 17, 2, 2),
	(83, 'Operaciones de Cultivo Acuícola', 17, 2, 1),
	(84, 'Operaciones de Transformación de Plásticos y Caucho', 18, 2, 1),
	(85, 'Operaciones de Laboratorio', 18, 2, 2),
	(86, 'Planta Química', 18, 2, 2),
	(87, 'Laboratorio', 18, 2, 1),
	(88, 'Operaciones de Fabricación de Productos Farmacéuticos', 18, 2, 1),
	(89, 'Operaciones de Proceso de Pasta y Papel', 18, 2, 1),
	(90, 'Operaciones de Proceso de Planta Química', 18, 2, 1),
	(91, 'Cuidados Auxiliares de Enfermería', 19, 2, 1),
	(92, 'Farmacia', 19, 2, 1),
	(93, 'Farmacia y Parafarmacia', 19, 2, 2),
	(94, 'Emergencias Sanitarias', 19, 2, 2),
	(95, 'Emergencias y Protección Civil', 20, 2, 2),
	(96, 'Atención a Personas en Situación de Dependencia', 21, 2, 2),
	(97, 'Atención Sociosanitaria', 21, 2, 1),
	(98, 'Operaciones de Ennoblecimiento Textil', 22, 2, 1),
	(99, 'Fabricación y Ennoblecimiento de Productos Textiles', 22, 2, 2),
	(100, 'Calzado y Marroquinería', 22, 2, 1),
	(101, 'Confección', 22, 2, 1),
	(102, 'Confección y Moda', 22, 2, 2),
	(103, 'Producción de Tejidos de Punto', 22, 2, 1),
	(104, 'Calzado y Complementos de Moda', 22, 2, 2),
	(105, 'Producción de Hiladura y Tejeduría de Calada', 22, 2, 1),
	(106, 'Electromecánica de Vehículos', 23, 2, 1),
	(107, 'Conducción de Vehículos de Transporte por Carretera', 23, 2, 2),
	(108, 'Electromecánica de Maquinaria', 23, 2, 2),
	(109, 'Electromecánica de Vehículos Automóviles', 23, 2, 2),
	(110, 'Mantenimiento de Material Rodante Ferroviario', 23, 2, 2),
	(111, 'Carrocería LOGSE', 23, 2, 1),
	(112, 'Carrocería', 23, 2, 2),
	(113, 'Operaciones de Fabricación de Productos Cerámicos', 24, 2, 1),
	(114, 'Operaciones de Fabricación de Vidrio y Transformados', 24, 2, 1),
	(115, 'Fabricación de Productos Cerámicos', 24, 2, 2),
	(116, 'Animación de Actividades Físicas y Deportivas', 1, 3, 1),
	(117, 'Administración y Finanzas LOGSE', 2, 3, 1),
	(118, 'Administración y Finanzas', 2, 3, 2),
	(119, 'Secretariado', 2, 3, 1),
	(120, 'Asistencia a la Dirección', 2, 3, 2),
	(121, 'Gestión y Organización de Empresas Agropecuarias', 3, 3, 1),
	(122, 'Gestión y Organización de los Recursos Naturales y Paisajísticos', 3, 3, 1),
	(123, 'Ganadería y Asistencia en Sanidad Animal', 3, 3, 2),
	(124, 'Gestión Forestal y del Medio Natural', 3, 3, 2),
	(125, 'Paisajismo y Medio Rural', 3, 3, 2),
	(126, 'Diseño y Producción Editorial', 4, 3, 1),
	(127, 'Producción en Industrias de Artes Gráficas', 4, 3, 1),
	(128, 'Diseño y Edición de Publicaciones Impresas y Multimedia', 4, 3, 2),
	(129, 'Diseño y Gestión de la Producción Gráfica', 4, 3, 2),
	(130, 'Artista Fallero y Construcción de Escenografías', 25, 3, 2),
	(131, 'Comercio Internacional LOGSE', 5, 3, 1),
	(132, 'Comercio Internacional', 5, 3, 2),
	(133, 'Gestión Comercial y Marketing', 5, 3, 1),
	(134, 'Gestión del Transporte', 5, 3, 1),
	(135, 'Servicios al Consumidor', 5, 3, 1),
	(136, 'Gestión de Ventas y Espacios Comerciales', 5, 3, 2),
	(137, 'Marketing y Publicidad', 5, 3, 2),
	(138, 'Transporte y Logística', 5, 3, 2),
	(139, 'Desarrollo de Proyectos Urbanísticos y Operaciones Topográficas', 6, 3, 1),
	(140, 'Desarrollo y Aplicación de Proyectos de Construcción', 6, 3, 1),
	(141, 'Realización y Planes de Obra', 6, 3, 1),
	(142, 'Proyectos de Edificación', 6, 3, 2),
	(143, 'Proyectos de Obra Civil', 6, 3, 2),
	(144, 'Instalaciones Electrotécnicas', 7, 3, 1),
	(145, 'Desarrollo de Productos Electrónicos', 7, 3, 1),
	(146, 'Sistemas de Regulación y Control Automáticos', 7, 3, 1),
	(147, 'Sistemas de Telecomunicación e Informáticos', 7, 3, 1),
	(148, 'Automatización y Robótica Industrial', 7, 3, 2),
	(149, 'Mantenimiento Electrónico', 7, 3, 2),
	(150, 'Sistemas Electrotécnicos y Automatizados', 7, 3, 2),
	(151, 'Sistemas de Telecomunicaciones e Informáticos', 7, 3, 2),
	(152, 'Centrales Eléctricas', 26, 3, 2),
	(153, 'Eficiencia Energética y Energía Solar Térmica', 26, 3, 2),
	(154, 'Energías Renovables', 26, 3, 2),
	(155, 'Construcciones Metálicas LOGSE', 8, 3, 1),
	(156, 'Construcciones Metálicas', 8, 3, 2),
	(157, 'Desarrollo de Proyectos Mecánicos', 8, 3, 1),
	(158, 'Óptica de Anteojería', 8, 3, 1),
	(159, 'Producción por Fundición y Pulvimetalurgia', 8, 3, 1),
	(160, 'Producción por Mecanizado', 8, 3, 1),
	(161, 'Diseño en Fabricación Mecánica', 8, 3, 2),
	(162, 'Programación de la Producción en Fabricación Mecánica', 8, 3, 2),
	(163, 'Programación de la Producción en Moldeo de Metales y Polímeros', 8, 3, 2),
	(164, 'Agencias de Viajes', 9, 3, 1),
	(165, 'Alojamiento', 9, 3, 1),
	(166, 'Animación Turística', 9, 3, 1),
	(167, 'Información y Comercialización Turísticas', 9, 3, 1),
	(168, 'Restauración', 9, 3, 1),
	(169, 'Agencias de Viajes y Gestión de Eventos', 9, 3, 2),
	(170, 'Dirección de Cocina', 9, 3, 2),
	(171, 'Dirección de Servicios de Restauración', 9, 3, 2),
	(172, 'Gestión de Alojamientos Turísticos', 9, 3, 2),
	(173, 'Guía, Información y Asistencias Turísticas', 9, 3, 2),
	(174, 'Asesoría de Imagen Personal', 10, 3, 1),
	(175, 'Estética', 10, 3, 1),
	(176, 'Asesoría de Imagen Personal y Corporativa', 10, 3, 2),
	(177, 'Caracterización y Maquillaje Profesional', 10, 3, 2),
	(178, 'Estética Integral y Bienestar', 10, 3, 2),
	(179, 'Estilismo y Dirección de Peluquería', 10, 3, 2),
	(180, 'Imagen', 11, 3, 1),
	(181, 'Producción de Audiovisuales, Radio y Espectáculos', 11, 3, 1),
	(182, 'Realización de Audiovisuales y Espectáculos', 11, 3, 1),
	(183, 'Sonido', 11, 3, 1),
	(184, 'Animaciones 3D, Juegos y Entornos Interactivos', 11, 3, 2),
	(185, 'Iluminación, Captación y Tratamiento de la Imagen', 11, 3, 2),
	(186, 'Producción de Audiovisuales y Espectáculos', 11, 3, 2),
	(187, 'Realización de Proyectos Audiovisuales y Espectáculos', 11, 3, 2),
	(188, 'Sonido para Audiovisuales y Espectáculos', 11, 3, 2),
	(189, 'Industria Alimentaria', 12, 3, 1),
	(190, 'Procesos y Calidad en la Industria Alimentaria', 12, 3, 2),
	(191, 'Vitivinicultura', 12, 3, 2),
	(192, 'Desarrollo de Aplicaciones Informáticas', 14, 3, 1),
	(193, 'Administración de Sistemas Informáticos', 14, 3, 1),
	(194, 'Administración de Sistemas Informáticos en Red', 14, 3, 2),
	(195, 'Desarrollo de Aplicaciones Multiplataforma', 14, 3, 2),
	(196, 'Desarrollo de Aplicaciones Web', 14, 3, 2),
	(197, 'Desarrollo de Proyectos de Instalaciones de Fluídos, Térmicas y de Manutención', 15, 3, 1),
	(198, 'Mantenimiento de Equipo Industrial', 15, 3, 1),
	(199, 'Mantenimiento y Montaje de Instalaciones de Edificio y Proceso', 15, 3, 1),
	(200, 'Prevención de Riesgos Profesionales', 15, 3, 1),
	(201, 'Desarrollo de Proyectos de Instalaciones Térmicas y de Fluidos', 15, 3, 2),
	(202, 'Mantenimiento de Instalaciones Térmicas y de Fluidos', 15, 3, 2),
	(203, 'Mecatrónica Industrial', 15, 3, 2),
	(204, 'Desarrollo de Productos en Carpintería y Mueble', 16, 3, 1),
	(205, 'Producción de Madera y Mueble', 16, 3, 1),
	(206, 'Diseño y Amueblamiento', 16, 3, 2),
	(207, 'Navegación, Pesca y Transporte Marítimo', 17, 3, 1),
	(208, 'Producción Acuícola', 17, 3, 1),
	(209, 'Supervisión y Control de Máquinas e Instalaciones del Buque', 17, 3, 1),
	(210, 'Acuicultura', 17, 3, 2),
	(211, 'Organización del Mantenimiento de Maquinaria de Buques y Embarcaciones', 17, 3, 2),
	(212, 'Transporte Marítimo y Pesca de Altura', 17, 3, 2),
	(213, 'Análisis y Control', 18, 3, 1),
	(214, 'Fabricación de Productos Farmacéuticos y Afines', 18, 3, 1),
	(215, 'Industrias de Proceso de Pasta y Papel', 18, 3, 1),
	(216, 'Industrias de Proceso Químico', 18, 3, 1),
	(217, 'Plásticos y Caucho', 18, 3, 1),
	(218, 'Química Ambiental', 18, 3, 1),
	(219, 'Laboratorio de Análisis y de Control de Calidad', 18, 3, 2),
	(220, 'Química Industrial', 18, 3, 2),
	(221, 'Audioprótesis', 19, 3, 1),
	(222, 'Audiología Protésica', 19, 3, 2),
	(223, 'Anatomía Patológica y Citología', 19, 3, 1),
	(224, 'Dietética', 19, 3, 1),
	(225, 'Documentación Sanitaria', 19, 3, 1),
	(226, 'Higiene Bucodental', 19, 3, 1),
	(227, 'Imagen para el Diagnóstico', 19, 3, 1),
	(228, 'Laboratorio de Diagnóstico Clínico', 19, 3, 1),
	(229, 'Ortoprotésica', 19, 3, 1),
	(230, 'Ortoprótesis y Productos de Apoyo', 19, 3, 2),
	(231, 'Prótesis Dentales LOGSE', 19, 3, 1),
	(232, 'Prótesis Dentales', 19, 3, 2),
	(233, 'Radioterapia', 19, 3, 1),
	(234, 'Salud Ambiental', 19, 3, 1),
	(235, 'Educación y Control Ambiental', 20, 3, 2),
	(236, 'Coordinación de Emergencias y Protección Civil', 20, 3, 2),
	(237, 'Animación Sociocultural', 21, 3, 1),
	(238, 'Animación Sociocultural y Turística', 21, 3, 2),
	(239, 'Educación Infantil LOGSE', 21, 3, 1),
	(240, 'Educación Infantil', 21, 3, 2),
	(241, 'Integración Social LOGSE', 21, 3, 1),
	(242, 'Promoción de Igualdad de Género', 21, 3, 2),
	(243, 'Integración Social', 21, 3, 2),
	(244, 'Interpretación de la Lengua de Signos', 21, 3, 1),
	(245, 'Curtidos', 22, 3, 1),
	(246, 'Patronaje', 22, 3, 1),
	(247, 'Procesos de Confección Industrial', 22, 3, 1),
	(248, 'Procesos de Ennoblecimiento Textil', 22, 3, 1),
	(249, 'Procesos Textiles de Hilatura y Tejeduría de Calada', 22, 3, 1),
	(250, 'Procesos Textiles de Tejeduría de Punto', 22, 3, 1),
	(251, 'Diseño y Producción de Calzado y Complementos', 22, 3, 2),
	(252, 'Diseño Técnico en Textil y Piel', 22, 3, 2),
	(253, 'Patronaje y Moda', 22, 3, 2),
	(254, 'Vestuario a Medida y de Espectáculos', 22, 3, 2),
	(255, 'Automoción LOGSE', 23, 3, 1),
	(256, 'Automoción', 23, 3, 2),
	(257, 'Mantenimiento Aeromecánico', 23, 3, 1),
	(258, 'Mantenimiento de Aviónica', 23, 3, 1),
	(259, 'Desarrollo y Fabricación de Productos Cerámicos LOGSE', 24, 3, 1),
	(260, 'Desarrollo y Fabricación de Productos Cerámicos', 24, 3, 2),
	(261, 'Fabricación y Transformación de Productos de Vidrio', 24, 3, 1);