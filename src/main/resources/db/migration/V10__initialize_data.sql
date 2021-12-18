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
VALUES (9, 'TECHNICIAN', 'Apuke', 'Cosmas', 'cosmas@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (8, 'TECHNICIAN', 'Isaac', 'Sserunjogi', 'sserunjogi.isaac@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (13, 'TECHNICIAN', 'Kato', 'Kugonza', 'kugonza.kato@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (14, 'TECHNICIAN', 'Samuel', 'Muwanguzi', 'samuel.muwanguzi@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (15, 'TECHNICIAN', 'Edgar', 'Sserwadda', 'sserwadda@payway.ug',
        '$2a$10$E/Eck6r.gHRKNwfaKyMYYueVsIWpOHndMoRS3CYWd5Dg8ZLtYselC', true, true, 8, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.users (id, role, first_name, last_name, email, encrypted_password, active, on_duty, field_hours,
                          base_longitude, base_latitude)
VALUES (16, 'TECHNICIAN', 'Nicholas', 'Mbuga', 'mbuga@payway.ug',
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

INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (22, 'HARDWARE', 'TERM-000', 'Russia, Test TERM', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (1, 'HARDWARE', 'TERM-001', 'Sakarya 5th Street', false, false, 32.6109124, 0.3140424);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (68, 'HARDWARE', 'TERM-003', 'Payway office', false, false, 32.606579, 0.3266208);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (69, 'HARDWARE', 'TERM-004', 'PayWay Office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (70, 'HARDWARE', 'TERM-005', 'Carrefour Oasis Mall', false, false, 32.5924782, 0.3190121);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (71, 'HARDWARE', 'TERM-006', 'Payway office', false, false, 32.6064837, 0.3266094);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (72, 'HARDWARE', 'TERM-007', 'Carrefour Metroplex', false, false, 32.6324364, 0.367441);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (73, 'HARDWARE', 'TERM-008', 'Carrefour Lugogo', false, false, 32.6064567565918, 0.32659271359443665);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (74, 'HARDWARE', 'TERM-009', 'Grand Lisboa Casino', false, false, 32.5956916809082, 0.32402119040489197);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (75, 'HARDWARE', 'TERM-010', 'Akamwesi Mall Gayaza Rd', false, false, 32.5739592, 0.3611655);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (61, 'HARDWARE', 'TERM-0107', 'Costco Supermarket', false, false, 32.5672468, 0.3146371);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (62, 'HARDWARE', 'TERM-0108', 'Payway Office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (63, 'HARDWARE', 'TERM-0109', 'Family Supermarket Kisaasi', false, false, 32.5999921, 0.3694082);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (76, 'HARDWARE', 'TERM-011', 'Container City Entebbe Town', false, false, 32.471826, 0.0617276);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (66, 'HARDWARE', 'TERM-0115', 'Crown Shopping arcade Kabalagala', false, false, 32.601572, 0.2987029);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (77, 'HARDWARE', 'TERM-012', 'Air Gate Supermarket Kitooro', false, false, 32.4627839, 0.0552233);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (65, 'HARDWARE', 'TERM-0128', 'PayWay Office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (78, 'HARDWARE', 'TERM-013', 'PayWay', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (64, 'HARDWARE', 'TERM-0139', 'DFCU Crane Chambers', false, false, 32.578885, 0.3141019);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (67, 'HARDWARE', 'TERM-0140', 'Kenjoy Nansana', false, false, 32.5241496, 0.3667476);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (55, 'HARDWARE', 'TERM-0206', 'Ecomart S/M Kiwatule', false, false, 32.6239564, 0.3645926);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (56, 'HARDWARE', 'TERM-0207', 'Church house', false, false, 32.57941, 0.3136997);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (57, 'HARDWARE', 'TERM-0208', 'Golden tripod Casino', false, false, 32.5808716, 0.315281);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (58, 'HARDWARE', 'TERM-0209', 'Payway Head office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (59, 'HARDWARE', 'TERM-0210', 'Crown supermarket kyanja', false, false, 32.5928663, 0.3889447);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (60, 'HARDWARE', 'TERM-0211', 'Capital Shoppers Nakasero', false, false, 32.5796234, 0.3111651);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (54, 'HARDWARE', 'TERM-0244', 'Capital Shoppers Nakawa', false, false, 32.6150251, 0.3262985);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (45, 'HARDWARE', 'TERM-1500', 'Savers S/M Kira road', false, false, 32.5853803, 0.338087);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (43, 'HARDWARE', 'TERM-1501', 'Village Mall Bugolobi', false, false, 32.6174353, 0.3197797);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (47, 'HARDWARE', 'TERM-1502', 'Multichoice Kololo', false, false, 32.5988944, 0.3279622);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (52, 'HARDWARE', 'TERM-1503', 'Speke Resort Munyonyo', false, false, 32.625436, 0.239498);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (50, 'HARDWARE', 'TERM-1504', 'Kampala Boulevard ', false, false, 32.5807734, 0.3130906);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (46, 'HARDWARE', 'TERM-1505', 'Mega Standard S/M', false, false, 32.5777478, 0.3106486);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (44, 'HARDWARE', 'TERM-1506', 'Kings Gate', false, false, 32.5983451, 0.2984421);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (51, 'HARDWARE', 'TERM-1507', 'Master Super Market- Ntinda', false, false, 32.6116657, 0.3537501);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (48, 'HARDWARE', 'TERM-1508', 'Sakaria Furniture Industrial area', false, false, 32.6082164, 0.3140303);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (49, 'HARDWARE', 'TERM-1509', 'New Life S/M', false, false, 32.5576788, 0.3798369);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (2, 'HARDWARE', 'TERM-1524', 'Da Plaza', false, false, 32.5872635, 0.3124366);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (3, 'HARDWARE', 'TERM-1525', 'Praise Supermarket Bunga', false, false, 32.6178988, 0.2722258);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (4, 'HARDWARE', 'TERM-1526', 'S&S Supermarket Nkumba', false, false, 32.5057626, 0.0975359);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (5, 'HARDWARE', 'TERM-1528', 'Best Buy Bukoto', false, false, 32.5979005, 0.3532347);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (6, 'HARDWARE', 'TERM-1529', 'PayWay Office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (7, 'HARDWARE', 'TERM-1530', 'PayWay Office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (8, 'HARDWARE', 'TERM-1531', 'PayWay', false, false, 32.5640432, 0.3045129);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (9, 'HARDWARE', 'TERM-1532', 'Senana Hyper Market', false, false, 32.5755322, 0.3189956);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (10, 'HARDWARE', 'TERM-1533', 'Crown Supermarket Sonde', false, false, 32.6845274, 0.3954818);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (53, 'HARDWARE', 'TERM-1957', 'Lasesa Solutions Ltd Mbarara', false, false, 32.60611550982706,
        0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (16, 'HARDWARE', 'TERM-3142', 'Garden city Mall DSTV', false, false, 32.591945, 0.3195995);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (12, 'HARDWARE', 'TERM-3143', 'Pearl Shopping Centre Kitooro', false, false, 32.591945, 0.3195995);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (17, 'HARDWARE', 'TERM-3144', 'Mulawa mall Bulindo', false, false, 32.6419833, 0.4063157);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (15, 'HARDWARE', 'TERM-3145', 'BMK House', false, false, 32.5943528, 0.3206409);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (11, 'HARDWARE', 'TERM-3146', 'Portbell Supermarket', false, false, 32.6349659, 0.313973);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (13, 'HARDWARE', 'TERM-3147', 'PayWay Office', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (14, 'HARDWARE', 'TERM-3148', 'Wilsen S/M Nansana', false, false, 32.5235655, 0.3669446);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (83, 'HARDWARE', 'TERM-018', 'PayWay', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (19, 'HARDWARE', 'TERM-3150', 'Fraine Supermarket Ntinda', false, false, 32.6125487, 0.3550793);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (20, 'HARDWARE', 'TERM-3151', 'Mini Supermarket Kajansi', false, false, 32.5416788, 0.21075);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (23, 'HARDWARE', 'TERM-6700', 'Kingdom Kampala', false, false, 32.5907471, 0.3178851);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (24, 'HARDWARE', 'TERM-6701', 'Capital Shoppers Ntinda', false, false, 32.6165898, 0.3492207);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (25, 'HARDWARE', 'TERM-6702', 'Capital Shoppers Garden City', false, false, 32.5918903, 0.319692);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (26, 'HARDWARE', 'TERM-6703', 'Dollar supermarket - Buzinga', false, false, 32.6172341, 0.2622111);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (27, 'HARDWARE', 'TERM-6704', 'Shumuk House', false, false, 32.5839296, 0.3133834);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (28, 'HARDWARE', 'TERM-6705', 'Portbell Supermarket Kireka', false, false, 32.6484695, 0.3466132);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (29, 'HARDWARE', 'TERM-6706', 'Vitamin Cafe Centenary Park', false, false, 32.5940979, 0.3184308);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (30, 'HARDWARE', 'TERM-6707', 'Best Buy S/M', false, false, 32.56401443481445, 0.30440419912338257);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (32, 'HARDWARE', 'TERM-6709', 'URA Entebbe Airport', false, false, 32.44294181744522, 0.045065084636989786);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (42, 'HARDWARE', 'TERM-7826', 'FoodHub', false, false, 32.5851251, 0.3126434);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (33, 'HARDWARE', 'TERM-7827', 'Lollybet', false, false, 32.5851916, 0.3189738);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (35, 'HARDWARE', 'TERM-7829', 'Savers Supermarket Rubaga', false, false, 32.5659617, 0.3140888);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (36, 'HARDWARE', 'TERM-7830', 'Fraine Supermarket Kiwatule Road', false, false, 32.6209483, 0.3616647);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (37, 'HARDWARE', 'TERM-7831', 'Frebo S/m Kisaasi', false, false, 32.5957688, 0.3239794);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (38, 'HARDWARE', 'TERM-7832', 'Crown Supermarket Entebbe road', false, false, 32.5559967, 0.2400013);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (41, 'HARDWARE', 'TERM-7833', 'Freedom City Mall', false, false, 32.5658158, 0.2735825);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (40, 'HARDWARE', 'TERM-7835', 'Kitende Supermarket', false, false, 32.5394197, 0.1921208);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (18, 'HARDWARE', 'TERM-3149', 'Payway Office', false, false, 32.62465, 0.3705366);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (31, 'HARDWARE', 'TERM-6708', 'Savers Supermarket Nakawa', false, false, 32.6210907, 0.3366921);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (34, 'HARDWARE', 'TERM-7828', 'Savers Supermarket Bombo rd', false, false, 32.5736452, 0.325711);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (39, 'HARDWARE', 'TERM-7834', 'Crane Plaza', false, false, 32.5884597, 0.3386813);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (81, 'HARDWARE', 'TERM-016', 'PayWay', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (82, 'HARDWARE', 'TERM-017', 'PayWay', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (80, 'HARDWARE', 'TERM-015', 'PayWay', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (85, 'HARDWARE', 'TERM-020', 'PayWay', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (84, 'HARDWARE', 'TERM-019', 'Payway', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (79, 'HARDWARE', 'TERM-014', 'Taxi Park Kitooro', false, false, 32.60611550982706, 0.3242028450429778);
INSERT INTO public.terminals (id, type, name, location, disabled, deleted, longitude, latitude)
VALUES (21, 'HARDWARE', 'TERM-002', 'Kampala Casino', false, false, 32.60611550982706, 0.3242028450429778);
