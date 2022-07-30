/*Creando la db*/

create database dbColegio2;
use dbColegio2;

create table Alumnos
(
    IdAlumno int unsigned auto_increment not null primary key,
    Nombres varchar(70) not null,
    Apellidos varchar(80) not null,
    DNI varchar(8) not null,
    Sexo char(1) not null,
    FechaNac date not null,
    NomPadre varchar(150) not null,
    NomMadre varchar(150) not null,
    NomApoderado varchar(150) not null,
    Telefono varchar(11) null,
    Celular varchar(9) null,
    Direccion varchar(180) not null,
    Distrito varchar(90) not null,
    Estado char(1)not null
)engine=innodb;
create table Empleados
(
    IdEmpleado int unsigned auto_increment not null primary key,
    Nombres varchar(70) not null,
    Apellidos varchar(80) not null,
    DNI varchar(8) not null,
    Sexo char(1) not null,
    FechaNac date not null,
    Telefono varchar(11) null,
    Celular varchar(9) null,
    Direccion varchar(180) not null,
    Distrito varchar(90) not null,
    Cargo varchar(60) not null,
    Especialidad varchar(90) null,
    Estado char(1)not null
)engine=innodb;
create table Usuarios
(
    IdUsuario smallint unsigned auto_increment not null primary key,
    IdEmpleado int unsigned not null,
    Nick varchar(8) not null,
    Clave varchar(8)not null,
    Tipo varchar(30) not null,
    index fk_idemp_usu(IdEmpleado asc),
    constraint fk_idemp_usu foreign key(IdEmpleado) references Empleados(IdEmpleado)
)engine=innodb;
create table Cursos
(
    IdCurso smallint unsigned auto_increment not null primary key,
    Nombre varchar(70) not null,
    Estado char(1)not null
    
)engine=innodb;
create table Nivel
(
    IdNivel smallint unsigned auto_increment not null primary key,
    Tutor int unsigned not null,
    CoTutor int unsigned not null,
    Grado varchar(60) not null,
    Orden varchar(10) not null,
    NroVacantes smallint not null,
    Estado char(1)not null,
    index fk_tut_niv(Tutor asc),
    index fk_cot_niv(CoTutor asc),
    constraint fk_tut_niv foreign key(Tutor) references Empleados(IdEmpleado), 
    constraint fk_cot_niv foreign key(CoTutor) references Empleados(IdEmpleado)
)engine=innodb;

create table Horarios
(
    IdHorario int unsigned not null auto_increment primary key,
    IdNivel smallint unsigned not null,
    IdCurso smallint unsigned not null,
    Dia varchar(20) not null,
    HoraIni varchar(10) not null,
    HoraFin varchar(10)not null,
    Profesor int unsigned not null,
    index fk_idniv_horario(IdNivel asc),
    index fk_idcur_horario(IdCurso asc),
    index fk_profe_horario(Profesor asc),
    constraint fk_idniv_horario foreign key(IdNivel) references Nivel(IdNivel),
    constraint fk_idcur_horario foreign key(IdCurso) references Cursos(IdCurso),
    constraint fk_profe_horario foreign key(Profesor) references Empleados(IdEmpleado)
)engine=innodb;
create table Matriculas
(
    IdMatricula int unsigned auto_increment not null primary key,
    IdAlumno int unsigned not null,
    IdNivel smallint unsigned not null,
    Periodo varchar(6) not null,
    Seccion char(1) null,
    Fecha date not null,
    Hora varchar(10) null,
    index fk_idalu_mat(IdAlumno asc),
    index fk_idniv_mat(IdNivel asc),
    constraint fk_idalu_mat foreign key(IdAlumno) references Alumnos(IdAlumno),
    constraint fk_idniv_mat foreign key(IdNivel) references Nivel(IdNivel)
)engine=innodb;
create table Pagos
(
    IdPago int unsigned auto_increment not null primary key,
    IdMatricula int unsigned not null,
    Fecha date not null,
    Hora varchar(10) not null,
    Total decimal(6,2) not null,
    index fl_idmat_pag(IdMatricula asc),
    constraint fk_idmat_pag foreign key(IdMatricula) references Matriculas(IdMatricula)
)engine=innodb;
create table Conceptos
(
    IdConcepto smallint unsigned auto_increment not null primary key,
    Nombre varchar(50) not null,
    FechaVencimiento date not null,
    Precio decimal(6,2)not null,
    Estado char(1)not null
)engine=innodb;
create table DetallePagos
(
    IdPago int unsigned not null,
    IdConcepto smallint unsigned not null,
    index fk_idpag_detallepagos(IdPago asc),
    index fk_idcon_detallepagos(IdConcepto asc),
    constraint fk_idpag_detallepagos foreign key(IdPago) references Pagos(IdPago),
    constraint fk_idcon_detallepagos foreign key(IdConcepto) references Conceptos(IdConcepto)
)engine=innodb;

/*OK-10 TABLAS*/


insert into empleados values(null,'Maximo','Sinchi','43191796','M','2022/06/10','-','968352698',' Av. Nicolás Ayllón #15487','Ate','Administrativo','-','1');
insert into usuarios values(null,1,'admin','admin','Administrador');

