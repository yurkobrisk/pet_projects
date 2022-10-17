--liquibase formatted sql

--changeset y.korziuk:1
create table t_quick_references (
    qr_id int not null primary key auto_increment,
    qr_description varchar(255),
    qr_image blob
);
--
----changeset y.korziuk:2
--alter table t_quick_references change column qr_id id_qr int;
