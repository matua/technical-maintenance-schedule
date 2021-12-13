INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (6, 'ADMINISTRATOR', 'Jonathan', 'Matua', 'matuajonathan@gmail.com',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (7, 'ADMINISTRATOR', 'Georgii', 'Matua', 'matuageorge@gmail.com',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (9, 'TECHNICIAN', 'Apuke', 'Cosmas', 'cosmas@payway.ug ',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (8, 'TECHNICIAN', 'Isaac', 'Sserunjogi', 'sserunjogi.isaac@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (13, 'TECHNICIAN', 'Kato', 'Kugonza', 'kugonza.kato@payway.ug ',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (14, 'TECHNICIAN', 'Samuel', 'Muwanguzi', 'samuel.muwanguzi@payway.ug ',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (15, 'TECHNICIAN', 'Edgar', 'Sserwadda', 'sserwadda@payway.ug ',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (16, 'TECHNICIAN', 'Nicholas', 'Mbuga', 'mbuga@payway.ug ',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (17, 'TECHNICIAN', 'Anoch', 'Turyasingura', 'turyasingura.anoch@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);


insert into tasks (description, priority, frequency)
values ('Blow out dust from kiosks', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values ('Clean top and main screens of kiosks', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values ('Clean the touch screen and check mas-touch', 'COMMON', 30);
insert into tasks (description, priority, frequency)
values ('Clean printer in the kiosks', 'COMMON', 90);
