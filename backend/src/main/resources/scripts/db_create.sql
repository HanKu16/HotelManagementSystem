CREATE TABLE hotel_addresses (
	hotel_address_id SERIAL CONSTRAINT hotel_addresses_pk PRIMARY KEY,
	city VARCHAR(60) NOT NULL,
	street VARCHAR(100) NOT NULL,
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

CREATE TABLE users (
	user_id VARCHAR(16) CONSTRAINT users_pk PRIMARY KEY,
	password VARCHAR(20) NOT NULL,
	creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	role_id INT NOT NULL,
	CONSTRAINT users_roles_fk
	FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE reservations (
    reservation_id SERIAL CONSTRAINT reservations_pk PRIMARY KEY,
    reservation_date DATE NOT NULL,
    creation_datetime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(16) NOT NULL,
    hotel_room_id INT NOT NULL,
    CONSTRAINT reservations_users_fk
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT reservations_rooms_fk
    FOREIGN KEY (hotel_room_id) REFERENCES hotel_rooms(hotel_room_id)
);