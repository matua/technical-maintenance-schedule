insert into users (role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours)
values ('ADMINISTRATOR', 'Jonathan', 'Matua', 'matuajonathan@gmail.com',
        '$2a$10$9WQyW16YLmpi/cDhdWTvd.wGRsRwYHw8cGrkgpxOERP4F4XzDO9CS', true, true, 8);
insert into users (role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours)
values ('ADMINISTRATOR', 'Georgii', 'Matua', 'matuageorge@gmail.com',
        '$2a$10$9WQyW16YLmpi/cDhdWTvd.wGRsRwYHw8cGrkgpxOERP4F4XzDO9CS', true, true, 8);
insert into users (role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours)
values ('TECHNICIAN', 'Ahmed', 'Kulumba', 'kulmba@payway.ug',
        '$2a$10$9WQyW16YLmpi/cDhdWTvd.wGRsRwYHw8cGrkgpxOERP4F4XzDO9CS', true, true, 8);
insert into users (role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours)
values ('TECHNICIAN', 'Isaac', 'Bolumba', 'bolumba@payway.ug',
        '$2a$10$9WQyW16YLmpi/cDhdWTvd.wGRsRwYHw8cGrkgpxOERP4F4XzDO9CS', true, true, 8);

insert into tasks (description, priority, frequency)
values ('Blow out dust from kiosks', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values ('Clean top and main screens of kiosks', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values ('Clean the touch screen and check mas-touch', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values ('Analysing kiosk monthly performance identifying common faults and problems.', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values
    ('Change validator balls', 'COMMON', 90);
insert into tasks (description, priority, frequency) values
    ('Change CMOS battery', 'COMMON', 90);
insert into tasks (description, priority, frequency) values
    ('Clean printer in the kiosks', 'COMMON', 90);
insert into tasks (description, priority, frequency) values
    ('Check hard disks performance, windows updates and reinstallations.', 'COMMON', 90);
