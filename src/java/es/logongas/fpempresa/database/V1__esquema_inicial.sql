
    create table Centro (
        idCentro integer not null auto_increment,
        descripcion varchar(255),
        fpempresa boolean,
        tipoVia integer,
        nombreVia varchar(255),
        otrosDireccion varchar(255),
        codigoPostal varchar(255),
        idProvincia integer,
        idMunicipio integer,
        primary key (idCentro)
    );

    create table Ciclo (
        idCiclo integer not null auto_increment,
        descripcion varchar(255),
        idFamilia integer,
        primary key (idCiclo)
    );

    create table ExperienciaLaboral (
        idExperienciaLaboral integer not null auto_increment,
        nombreEmpresa varchar(255),
        fechaInicio datetime,
        fechaFin datetime,
        puestoTrabajo varchar(255),
        descripcion varchar(255),
        idTitulado integer,
        primary key (idExperienciaLaboral)
    );

    create table Familia (
        idFamilia integer not null auto_increment,
        descripcion varchar(255),
        primary key (idFamilia)
    );

    create table FormacionAcademica (
        idFormacionAcademica integer not null auto_increment,
        tipoFormacionAcademica integer,
        otroCentro varchar(255),
        otroTitulo varchar(255),
        idCentro integer,
        idFamilia integer,
        idCiclo integer,
        idTitulado integer,
        primary key (idFormacionAcademica)
    );

    create table Municipio (
        idMunicipio integer not null auto_increment,
        descripcion varchar(255),
        codigo varchar(5),
        idProvincia integer,
        primary key (idMunicipio)
    );

    create table Provincia (
        idProvincia integer not null auto_increment,
        descripcion varchar(255),
        primary key (idProvincia)
    );

    create table Titulado (
        idTitulado integer not null auto_increment,
        idIdentity integer,
        fechaNacimiento datetime,
        tipoVia integer,
        nombreVia varchar(255),
        otrosDireccion varchar(255),
        codigoPostal varchar(255),
        idProvincia integer,
        idMunicipio integer,
        telefono varchar(255),
        telefonoAlternativo varchar(255),
        tipoDocumento integer,
        numeroDocumento varchar(255),
        primary key (idTitulado)
    );

    create table TituloIdioma (
        idTituloIdioma integer not null auto_increment,
        fecha datetime,
        idioma integer,
        nivelIdioma integer,
        idTitulado integer,
        primary key (idTituloIdioma)
    );

    create table Usuario (
        idIdentity integer not null auto_increment,
        eMail varchar(255),
        nombre varchar(255),
        ape1 varchar(255),
        ape2 varchar(255),
        password varchar(255),
        tipoUsuario INT(11),
        foto tinyblob,
        primary key (idIdentity),
        UNIQUE INDEX `eMail` (`eMail`)
    );

    

    alter table Centro  add index FK7817BA05DA49940A (idProvincia), add constraint FK7817BA05DA49940A foreign key (idProvincia) references Provincia (idProvincia);
    alter table Centro add index FK7817BA05391E4C8A (idMunicipio), add constraint FK7817BA05391E4C8A foreign key (idMunicipio) references Municipio (idMunicipio);
    alter table Ciclo add index FK3E162E041ECA7CA (idFamilia), add constraint FK3E162E041ECA7CA foreign key (idFamilia) references Familia (idFamilia);
    alter table ExperienciaLaboral add index FK5E73A0484C28477E (idTitulado), add constraint FK5E73A0484C28477E foreign key (idTitulado) references Titulado (idTitulado);
    alter table FormacionAcademica add index FK7C4E0018EE23894D (idCentro), add constraint FK7C4E0018EE23894D foreign key (idCentro) references Centro (idCentro);
    alter table FormacionAcademica add index FK7C4E001841ECA7CA (idFamilia), add constraint FK7C4E001841ECA7CA foreign key (idFamilia) references Familia (idFamilia);
    alter table FormacionAcademica add index FK7C4E0018E6B99AB0 (idCiclo), add constraint FK7C4E0018E6B99AB0 foreign key (idCiclo) references Ciclo (idCiclo);
    alter table FormacionAcademica add index FK7C4E00184C28477E (idTitulado), add constraint FK7C4E00184C28477E foreign key (idTitulado) references Titulado (idTitulado);
    alter table Municipio add index FK863DB7CDDA49940A (idProvincia), add constraint FK863DB7CDDA49940A foreign key (idProvincia) references Provincia (idProvincia);
    alter table Titulado add index FK94E67D96B3306F0C (idIdentity), add constraint FK94E67D96B3306F0C foreign key (idIdentity) references Usuario (idIdentity);
    alter table Titulado add index FK94E67D96DA49940A (idProvincia), add constraint FK94E67D96DA49940A foreign key (idProvincia) references Provincia (idProvincia);
    alter table Titulado add index FK94E67D96391E4C8A (idMunicipio), add constraint FK94E67D96391E4C8A foreign key (idMunicipio) references Municipio (idMunicipio);
    alter table TituloIdioma add index FK51BC8E0E4C28477E (idTitulado), add constraint FK51BC8E0E4C28477E foreign key (idTitulado) references Titulado (idTitulado);
