INSERT INTO hotel_addresses (city, postal_code, building_number)
VALUES
	('Warszawa', '00-021', '15'),
	('Kraków', '30-134', '21A'),
	('Gdańsk', '80-011', '2'),
	('Białystok', '15-109', '78B'),
	('Wrocław', '50-007', '6');

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
