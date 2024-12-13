CREATE TABLE hotel_addresses (
	hotel_address_id SERIAL CONSTRAINT hotel_addresses_pk PRIMARY KEY,
	city VARCHAR(60) NOT NULL,
	postal_code VARCHAR(6) NOT NULL,
	building_number VARCHAR(10) NOT NULL
);

CREATE TABLE hotels (
	hotel_id SERIAL CONSTRAINT hotels_pk PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(1000) NOT NULL,
	hotel_address_id INT UNIQUE NOT NULL,
	CONSTRAINT hotels_hotel_addresses_fk
	FOREIGN KEY (hotel_address_id) REFERENCES hotel_addresses(hotel_address_id)
);

CREATE TABLE hotel_rooms (
	hotel_room_id SERIAL CONSTRAINT hotel_rooms_pk PRIMARY KEY,
	guest_capacity INT NOT NULL,
	hotel_id INT NOT NULL,
	FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id)
);

CREATE TABLE hotel_amenities (
	hotel_amenity_id SERIAL CONSTRAINT hotel_amenities_pk PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	hotel_id INT NOT NULL,
	CONSTRAINT hotel_amenities_hotels_fk
	FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id)
);

CREATE TYPE role_name AS ENUM ('USER', 'ADMIN');

CREATE TABLE roles (
	role_id SERIAL CONSTRAINT roles_pk PRIMARY KEY,
	name ROLE_NAME UNIQUE NOT NULL
);
