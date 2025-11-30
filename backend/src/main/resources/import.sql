INSERT INTO user_customer (id, name, email) VALUES (1, 'Alice', 'alice@example.com');
INSERT INTO user_customer (id, name, email) VALUES (2, 'Bob', 'bob@example.com');
INSERT INTO user_customer (id, name, email) VALUES (3, 'Charlie', 'charlie@example.com');

INSERT INTO vhs (id, title, genre, release_year) VALUES (1, 'The Matrix', 'Sci-Fi', 1999);
INSERT INTO vhs (id, title, genre, release_year) VALUES (2, 'Titanic', 'Romance', 1997);
INSERT INTO vhs (id, title, genre, release_year) VALUES (3, 'Jurassic Park', 'Adventure', 1993);
INSERT INTO vhs (id, title, genre, release_year) VALUES (4, 'The Matrix Reloaded', 'Sci-Fi', 2003);
INSERT INTO vhs (id, title, genre, release_year) VALUES (5, 'The Matrix Revolutions', 'Sci-Fi', 2003);
INSERT INTO vhs (id, title, genre, release_year) VALUES (6, 'Romeo + Juliet', 'Romance', 1996);
INSERT INTO vhs (id, title, genre, release_year) VALUES (7, 'Titanic 2', 'Romance', 1997);
INSERT INTO vhs (id, title, genre, release_year) VALUES (8, 'Jurassic Park 2', 'Adventure', 1997);
INSERT INTO vhs (id, title, genre, release_year) VALUES (9, 'Jurassic Park 3', 'Adventure', 2001);
INSERT INTO vhs (id, title, genre, release_year) VALUES (10, 'Interstellar', 'Sci-Fi', 2014);

-- Rentals
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (1, '2025-11-01', '2025-11-10', NULL, 1, 1);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (2, '2025-11-05', '2025-11-12', '2025-11-11', 2, 2);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (3, '2025-11-10', '2025-11-17', NULL, 3, 1);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (4, '2025-11-12', '2025-11-19', NULL, 4, 3);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (5, '2025-11-14', '2025-11-21', NULL, 5, 2);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (6, '2025-11-15', '2025-11-22', NULL, 6, 1);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (7, '2025-11-16', '2025-11-23', NULL, 7, 3);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (8, '2025-11-17', '2025-11-24', NULL, 8, 2);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (9, '2025-11-18', '2025-11-25', NULL, 9, 1);
INSERT INTO rental (id, rental_date, due_date, return_date, vhs_id, user_id) VALUES (10, '2025-11-20', '2025-11-27', NULL, 10, 3);

SELECT setval('user_customer_id_seq', (SELECT MAX(id) FROM user_customer));
SELECT setval('vhs_id_seq', (SELECT MAX(id) FROM vhs));
SELECT setval('rental_id_seq', (SELECT MAX(id) FROM rental));