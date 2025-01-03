INSERT INTO hotel_addresses (city, street, postal_code, building_number)
VALUES
	('Warszawa', 'Mokotowska', '00-021', '15'),
	('Kraków', 'Grunwaldzka', '30-134', '21A'),
	('Gdańsk', 'Morska','80-011', '2'),
	('Białystok', 'Podlaska', '15-109', '78B'),
	('Wrocław', 'Techniczna','50-007', '6');

INSERT INTO hotels (name, description, hotel_address_id)
VALUES
	('Babilon', 'Hotel Babilon ma bardzo dobrą lokalizacje.', 1),
	('Akropol', 'Hotel Akropol jest najlepiej ocenianym hotelem w Krakowie.', 2),
	('Neptun', 'Hotel Neptun znajduje się tylko 200m od morza.', 3),
	('Kapitol', 'Hotel Kapitol jest najwyższym hotelem w kraju.', 4),
	('Olimp', 'Hotel Olimp posiada piękny widok na panoramę Odry.', 5);

INSERT INTO hotel_rooms (guest_capacity, hotel_id)
VALUES
	(2, 1), (3, 1), (3, 1), (4, 1),
	(1, 2), (2, 2), (3, 2), (3, 2), (5, 2),
	(3, 3), (3, 3), (3, 3), (4, 3), (5, 3),
	(1, 4), (2, 4), (3, 4), (4, 4),
	(1, 5), (2, 5), (2, 5), (2, 5), (3, 5), (3, 5), (4, 5), (5, 5);

INSERT INTO hotel_amenities (name, hotel_id)
VALUES
	('Wi-FI', 1), ('Basen', 1),
	('Sauna', 2), ('Siłownia', 2),
	('Siłownia', 3), ('Plaża hotelowa', 3), ('Basen', 3),
	('Basen', 4), ('Sauna', 4), ('Wi-Fi', 4),
	('Wi-Fi', 5), ('Park hotelowy', 5), ('Plac zabaw', 5);

INSERT INTO roles (name)
VALUES
    ('USER'),
    ('ADMIN');

INSERT INTO users (user_id, password, role_id)
VALUES
	('adrian16', 'piesPimpek12', 1),
	('PawelOrzel', 'polska1', 1),
	('szpaku', 'pokemon99', 1),
	('admin1', 'appadmin1', 2);

INSERT INTO reservations (reservation_date, creation_datetime, user_id, hotel_room_id)
VALUES
    -- Room 1 (1 reservation)
    ('2024-12-20', '2024-12-15 10:00:00', 'adrian16', 1),

    -- Room 2 ( reservations)
    ('2024-12-20', '2024-12-15 11:00:00', 'PawelOrzel', 2),
    ('2024-12-21', '2024-12-15 12:00:00', 'szpaku', 2),

    -- Room 4 (3 reservations)
    ('2024-12-20', '2024-12-15 16:00:00', 'szpaku', 4),
    ('2024-12-21', '2024-12-15 17:00:00', 'adrian16', 4),
    ('2024-12-22', '2024-12-15 18:00:00', 'PawelOrzel', 4),

    -- Room 5 (1 reservations)
    ('2024-12-20', '2024-12-15 20:00:00', 'szpaku', 5),

    -- Room 7 (3 reservations)
    ('2024-12-20', '2024-12-16 10:00:00', 'adrian16', 7),
    ('2024-12-21', '2024-12-16 11:00:00', 'szpaku', 7),
    ('2024-12-22', '2024-12-16 12:00:00', 'PawelOrzel', 7),

    -- Room 8 (1 reservations)
    ('2024-12-22', '2024-12-16 15:00:00', 'adrian16', 8),

    -- Room 9 (2 reservations)
    ('2024-12-20', '2024-12-16 17:00:00', 'PawelOrzel', 9),
    ('2024-12-21', '2024-12-16 18:00:00', 'szpaku', 9),

    -- Room 10 (1 reservation)
    ('2024-12-20', '2024-12-16 20:00:00', 'adrian16', 10),

    -- Room 11 (1 reservations)
    ('2024-12-21', '2024-12-16 22:00:00', 'szpaku', 11),

    -- Room 12 (1 reservations)
    ('2024-12-20', '2024-12-16 23:00:00', 'PawelOrzel', 12),

    -- Room 13 (2 reservations)
    ('2024-12-20', '2024-12-17 03:00:00', 'adrian16', 13),
    ('2024-12-21', '2024-12-17 04:00:00', 'PawelOrzel', 13),

    -- Room 14 (1 reservations)
    ('2024-12-20', '2024-12-17 05:00:00', 'szpaku', 14),

    -- Room 15 (3 reservations)
    ('2024-12-21', '2024-12-17 09:00:00', 'adrian16', 15),
    ('2024-12-22', '2024-12-17 10:00:00', 'PawelOrzel', 15),
    ('2024-12-23', '2024-12-17 11:00:00', 'szpaku', 15),

    -- Room 17 (1 reservations)
    ('2024-12-22', '2024-12-17 15:00:00', 'szpaku', 17),

    -- Room 18 (2 reservations)
    ('2024-12-20', '2024-12-17 16:00:00', 'adrian16', 18),
    ('2024-12-21', '2024-12-17 17:00:00', 'PawelOrzel', 18),

    -- Room 19 (2 reservations)
    ('2024-12-22', '2024-12-17 20:00:00', 'adrian16', 19),
    ('2024-12-23', '2024-12-17 21:00:00', 'szpaku', 19),

    -- Room 20 (1 reservations)
    ('2024-12-20', '2024-12-17 22:00:00', 'PawelOrzel', 20),

    -- Room 21 (1 reservations)
    ('2024-12-20', '2024-12-18 01:00:00', 'adrian16', 21),

    -- Room 22 (2 reservations)
    ('2024-12-20', '2024-12-18 03:00:00', 'PawelOrzel', 22),
    ('2024-12-21', '2024-12-18 04:00:00', 'szpaku', 22),

    -- Room 24 (3 reservations)
    ('2024-12-20', '2024-12-18 08:00:00', 'adrian16', 24),
    ('2024-12-21', '2024-12-18 09:00:00', 'PawelOrzel', 24),
    ('2024-12-22', '2024-12-18 10:00:00', 'szpaku', 24),

    -- Room 26 (3 reservations)
    ('2024-12-21', '2024-12-18 14:00:00', 'adrian16', 26),
    ('2024-12-22', '2024-12-18 15:00:00', 'PawelOrzel', 26),
    ('2024-12-23', '2024-12-18 16:00:00', 'szpaku', 26);
