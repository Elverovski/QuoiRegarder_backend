
-- User data
insert into users (id, nom, prenom, email, genre) values (1, 'Stainer', 'Peirce', 'pstainer0@earthlink.net', 'Male');
insert into users (id, nom, prenom, email, genre) values (2, 'Lazer', 'Fifine', 'flazer1@google.co.jp', 'Female');
insert into users (id, nom, prenom, email, genre) values (3, 'Hanley', 'Aguie', 'ahanley2@yandex.ru', 'Male');
insert into users (id, nom, prenom, email, genre) values (4, 'Tenney', 'Elle', 'etenney3@nsw.gov.au', 'Female');
insert into users (id, nom, prenom, email, genre) values (5, 'McKeachie', 'Arty', 'amckeachie4@feedburner.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (6, 'Spurr', 'Marc', 'mspurr5@smh.com.au', 'Male');
insert into users (id, nom, prenom, email, genre) values (7, 'Jewess', 'Eddy', 'ejewess6@sciencedirect.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (8, 'Scrimshaw', 'Merrie', 'mscrimshaw7@creativecommons.org', 'Female');
insert into users (id, nom, prenom, email, genre) values (9, 'Broom', 'Wallie', 'wbroom8@buzzfeed.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (10, 'Alben', 'Dallis', 'dalben9@odnoklassniki.ru', 'Male');
insert into users (id, nom, prenom, email, genre) values (11, 'Thyng', 'Lek', 'lthynga@macromedia.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (12, 'Wyse', 'Salim', 'swyseb@goo.gl', 'Male');
insert into users (id, nom, prenom, email, genre) values (13, 'Wapol', 'Eamon', 'ewapolc@github.io', 'Male');
insert into users (id, nom, prenom, email, genre) values (14, 'Agar', 'Lanae', 'lagard@jimdo.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (15, 'Moine', 'Georgiana', 'gmoinee@cdc.gov', 'Female');
insert into users (id, nom, prenom, email, genre) values (16, 'Levine', 'Holt', 'hlevinef@pinterest.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (17, 'Markovich', 'Ali', 'amarkovichg@delicious.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (18, 'Squeers', 'Corbie', 'csqueersh@chron.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (19, 'Crathern', 'Em', 'ecratherni@gizmodo.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (20, 'Hallock', 'Retha', 'rhallockj@com.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (21, 'Scamal', 'Evie', 'escamalk@naver.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (22, 'Crickmore', 'Amandi', 'acrickmorel@mit.edu', 'Female');
insert into users (id, nom, prenom, email, genre) values (23, 'Bleackley', 'Hinze', 'hbleackleym@merriam-webster.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (24, 'Fairham', 'Charles', 'cfairhamn@slate.com', 'Male');
insert into users (id, nom, prenom, email, genre) values (25, 'Roizn', 'Aurel', 'aroizno@msn.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (26, 'Perl', 'Ernesta', 'eperlp@smugmug.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (27, 'Ashelford', 'Meghann', 'mashelfordq@nationalgeographic.com', 'Female');
insert into users (id, nom, prenom, email, genre) values (28, 'Tremblot', 'Jasen', 'jtremblotr@senate.gov', 'Male');
insert into users (id, nom, prenom, email, genre) values (29, 'Burgess', 'Drusie', 'dburgesss@cam.ac.uk', 'Female');
insert into users (id, nom, prenom, email, genre) values (30, 'McGourty', 'Nicolina', 'nmcgourtyt@google.co.uk', 'Female');

--------------------------------------------------------------------------------------------
-- Serie Data

INSERT INTO serie (id, titre, genre, nbepisodes, note) VALUES
(1, 'Breaking Bad', 'Crime', 62, 9.5),
(2, 'Better Call Saul', 'Crime', 62, 8.9),
(3, 'Narcos', 'Crime', 30, 8.8),
(4, 'Peaky Blinders', 'Crime', 36, 8.7),
(5, 'Money Heist', 'Thriller', 41, 8.3),
(6, 'Lupin', 'Thriller', 10, 7.6),
(7, 'Stranger Things', 'Science-Fiction', 34, 8.7),
(8, 'Dark', 'Science-Fiction', 34, 8.8),
(9, 'The Mandalorian', 'Science-Fiction', 24, 8.6),
(10, 'Black Mirror', 'Science-Fiction', 27, 8.8),
(11, 'The Crown', 'Historique', 50, 8.6),
(12, 'Vikings', 'Historique', 50, 8.6),
(13, 'The Office', 'Comédie', 201, 9.0),
(14, 'Friends', 'Comédie', 236, 8.9),
(15, 'Brooklyn Nine-Nine', 'Comédie', 153, 8.4),
(16, 'How I Met Your Mother', 'Comédie', 153, 8.3),
(17, 'The Simpsons', 'Animation', 750, 8.5),
(18, 'South Park', 'Animation', 750, 8.5),
(19, 'Rick and Morty', 'Animation', 70, 9.0),
(20, 'One Piece', 'Anime', 1100, 9.1),
(21, 'Attack on Titan', 'Anime', 87, 9.0),
(22, 'Death Note', 'Anime', 37, 9.0),
(23, 'Fullmetal Alchemist: Brotherhood', 'Anime', 64, 9.2),
(24, 'Demon Slayer', 'Anime', 64, 8.8),
(25, 'Game of Thrones', 'Fantasy', 73, 9.2),
(26, 'The Witcher', 'Fantasy', 24, 8.2),
(27, 'House of the Dragon', 'Fantasy', 24, 8.6),
(28, 'The Boys', 'Super-Héros', 24, 8.7),
(29, 'The Walking Dead', 'Horreur', 177, 8.1),
(30, 'Fear the Walking Dead', 'Horreur', 177, 7.9);

---------------------------------------------------------------------------------------------------------
-- Episode Data
INSERT INTO episode (title, episode_number, season_number, serie_id) VALUES
-- Breaking Bad
('Pilot', 1, 1, 1),
('Crazy Chemistry', 2, 1, 1),
('The Heist', 3, 1, 1),

-- Better Call Saul
('Slippin Jimmy', 1, 1, 2),
('Lawyer Up', 2, 1, 2),

-- Narcos
('First Deal', 1, 1, 3),

-- Peaky Blinders
('The Razor', 1, 1, 4),
('Gangs of Birmingham', 2, 1, 4),

-- Money Heist
('The Plan', 1, 1, 5),
('Masks On', 2, 1, 5),
('Breaking In', 3, 1, 5),

-- Lupin
('Gentleman Thief', 1, 1, 6),

-- Stranger Things
('Upside Down', 1, 1, 7),
('The Vanishing', 2, 1, 7),

-- Dark
('time Loop', 1, 1, 8),

-- The Mandalorian
('The Child', 1, 1, 9),
('Bounty Hunt', 2, 1, 9),

-- Black Mirror
('Nosedive', 1, 1, 10),

-- The Crown
('Kings Speech', 1, 1, 11),
('Royal Duty', 2, 1, 11),

-- Vikings
('Ragnar Returns', 1, 1, 12),

-- The Office
('Pilot', 1, 1, 13),
('The Dundies', 2, 1, 13),

-- Friends
('The One with the Meeting', 1, 1, 14),

-- Brooklyn Nine-Nine
('Hot Case', 1, 1, 15),
('Jake vs Amy', 2, 1, 15),

-- How I Met Your Mother
('First Date', 1, 1, 16),

-- The Simpsons
('Barts Prank', 1, 1, 17),
('Homers Dilemma', 2, 1, 17),

-- South Park
('Cartman Rules', 1, 1, 18),

-- Rick and Morty
('Portal Madness', 1, 1, 19),
('Pickle Rick', 2, 1, 19),
('Interdimensional', 3, 1, 19),

-- One Piece
('The Beginning', 1, 1, 20),
('Treasure Hunt', 2, 1, 20),
('Grand Adventure', 3, 1, 20),

-- Attack on Titan
('Wall Breach', 1, 1, 21),

-- Death Note
('The Notebook', 1, 1, 22),
('Mind Games', 2, 1, 22),

-- Fullmetal Alchemist: Brotherhood
('Alchemy 101', 1, 1, 23),
('Brotherhood', 2, 1, 23),

-- Demon Slayer
('Blade Dance', 1, 1, 24),

-- Game of Thrones
('Winter is Coming', 1, 1, 25),
('Kings and Queens', 2, 1, 25),

-- The Witcher
('Monster Hunt', 1, 1, 26),

-- House of the Dragon
('Dragon Blood', 1, 1, 27),
('Court Intrigue', 2, 1, 27),

-- The Boys
('Super Secrets', 1, 1, 28),

-- The Walking Dead
('Outbreak', 1, 1, 29),
('Survivors', 2, 1, 29),

-- Fear the Walking Dead
('The End Begins', 1, 1, 30);

