--liquibase formatted sql

--changeset agraf:1
CREATE INDEX students_name_index
    ON student (name);

--changeset agraf:2
CREATE INDEX faculty_name_color_index
    ON faculty (name, color);