-- Inserts para T_RAREZA
INSERT INTO T_RAREZA (NOMBRE_RAREZA) VALUES
                                         ('Common'),
                                         ('Uncommon'),
                                         ('Rare'),
                                         ('Mythic Rare');

-- Inserts para T_SET
INSERT INTO T_SET (SET_NAME) VALUES
                                 ('Alpha'),
                                 ('Beta'),
                                 ('Unlimited'),
                                 ('Revised Edition'),
                                 ('Zendikar Rising'),
                                 ('Dominaria United'),
                                 ('March of the Machine');

-- Inserts para T_TYPE
INSERT INTO T_TYPE (TYPE_NAME) VALUES
                                   ('Creature'),
                                   ('Instant'),
                                   ('Sorcery'),
                                   ('Artifact'),
                                   ('Enchantment'),
                                   ('Land'),
                                   ('Planeswalker');

-- Inserts para T_CARDS
INSERT INTO T_CARDS (NOMBRE, MANA_COST, CMC, COLOR, COLOR_IDENTITY, PODER, RESISTENCIA, TIPO, RAREZA, CARD_SET, PRECIO) VALUES
                                                                                                                            ('Lightning Bolt', '{R}', 1, 'Red', 'R', 0, 0, 2, 1, 1, 2.5), -- Lightning Bolt, un instantáneo común.
                                                                                                                            ('Black Lotus', '{0}', 0, 'Colorless', '', 0, 0, 4, 3, 1, 15000.0), -- Black Lotus, un artefacto mítico.
                                                                                                                            ('Serra Angel', '{3}{W}{W}', 5, 'White', 'W', 4, 4, 1, 2, 3, 0.8), -- Una criatura rara.
                                                                                                                            ('Brainstorm', '{U}', 1, 'Blue', 'U', 0, 0, 2, 1, 5, 0.25), -- Un instantáneo común.
                                                                                                                            ('Jace, the Mind Sculptor', '{2}{U}{U}', 4, 'Blue', 'U', 0, 0, 7, 4, 4, 50.0), -- Un planeswalker mítico.
                                                                                                                            ('Goblin Guide', '{R}', 1, 'Red', 'R', 2, 2, 1, 3, 4, 15.0), -- Una criatura rara.
                                                                                                                            ('Path to Exile', '{W}', 1, 'White', 'W', 0, 0, 2, 2, 6, 3.0), -- Un instantáneo poco común.
                                                                                                                            ('Tarmogoyf', '{1}{G}', 2, 'Green', 'G', 0, 0, 1, 4, 7, 45.0); -- Una criatura mítica.


-- Inserts adicionales para T_SET
INSERT INTO T_SET (SET_NAME) VALUES
                                 ('Throne of Eldraine'),
                                 ('Ikoria: Lair of Behemoths'),
                                 ('Kaldheim'),
                                 ('Strixhaven: School of Mages'),
                                 ('Modern Horizons'),
                                 ('Phyrexia: All Will Be One'),
                                 ('The Brothers\' War'),
('Shadows over Innistrad');

-- Inserts adicionales para T_CARDS
INSERT INTO T_CARDS (NOMBRE, MANA_COST, CMC, COLOR, COLOR_IDENTITY, PODER, RESISTENCIA, TIPO, RAREZA, CARD_SET, PRECIO) VALUES
('Uro, Titan of Nature\'s Wrath', '{1}{G}{U}', 3, 'Green, Blue', 'GU', 6, 6, 1, 4, 8, 20.0), -- Criatura mítica.
                                 ('Teferi, Time Raveler', '{1}{W}{U}', 3, 'White, Blue', 'WU', 0, 0, 7, 4, 8, 12.0), -- Planeswalker mítico.
                                 ('Evolving Wilds', '', 0, 'Colorless', '', 0, 0, 6, 1, 9, 0.1), -- Tierras comunes.
                                 ('Embercleave', '{4}{R}{R}', 6, 'Red', 'R', 0, 0, 5, 4, 10, 25.0), -- Artefacto mítico.
                                 ('Questing Beast', '{2}{G}{G}', 4, 'Green', 'G', 4, 4, 1, 4, 9, 35.0), -- Criatura mítica.
                                 ('Wrath of God', '{2}{W}{W}', 4, 'White', 'W', 0, 0, 3, 3, 8, 4.0), -- Hechizo raro.
                                 ('Fabled Passage', '', 0, 'Colorless', '', 0, 0, 6, 2, 10, 8.0), -- Tierras poco comunes.
                                 ('Brazen Borrower', '{1}{U}{U}', 3, 'Blue', 'U', 3, 1, 1, 4, 10, 15.0), -- Criatura mítica.
                                 ('Liliana of the Veil', '{1}{B}{B}', 3, 'Black', 'B', 0, 0, 7, 4, 8, 60.0), -- Planeswalker mítico.
                                 ('Blood Moon', '{2}{R}', 3, 'Red', 'R', 0, 0, 5, 3, 7, 15.0); -- Encantamiento raro.

