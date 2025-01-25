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
	('admin1', 'appadmin1', 2),
	('User21', 'haslo1', 1);

INSERT INTO reservations (reservation_date, creation_datetime, user_id, hotel_room_id)
VALUES
    ('2024-12-20', '2024-12-15 10:00:00', 'adrian16', 1),
    ('2024-12-27', '2024-12-16 16:00:00', 'szpaku', 4),
    ('2025-01-05', '2024-12-20 17:00:00', 'adrian16', 4),
    ('2025-01-05', '2024-12-21 17:00:00', 'User21', 5),
    ('2025-01-08', '2024-12-31 18:00:00', 'PawelOrzel', 4),
    ('2025-01-22', '2024-01-15 12:00:00', 'PawelOrzel', 7),
    ('2025-01-20', '2025-01-15 17:00:00', 'User21', 5),
    ('2025-01-23', '2025-01-15 17:00:00', 'User21', 17),
    ('2025-01-21', '2025-01-15 20:00:00', 'adrian16', 10),
    ('2025-01-31', '2025-01-16 22:00:00', 'szpaku', 11),
    ('2025-01-31', '2025-01-17 23:00:00', 'PawelOrzel', 12),
    ('2025-02-01', '2025-01-17 03:00:00', 'adrian16', 13),
    ('2025-02-05', '2025-01-17 04:00:00', 'PawelOrzel', 13),
    ('2025-01-30', '2025-01-15 17:00:00', 'User21', 15),
    ('2025-02-07', '2025-01-18 05:00:00', 'szpaku', 14),
    ('2025-02-09', '2025-01-19 09:00:00', 'adrian16', 15),
    ('2025-02-05', '2025-01-15 17:00:00', 'User21', 2),
    ('2025-02-11', '2025-01-22 03:00:00', 'PawelOrzel', 22),
    ('2025-02-05', '2025-01-24 04:00:00', 'szpaku', 22);
