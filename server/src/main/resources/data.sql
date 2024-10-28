INSERT INTO `users` (username, email, encrypted_password, role, timestamp)
VALUES ('Spongebob', 'spongebob@gmail.com', 'a', 'USER', 1),
       ('Patrick', 'patrick@gmail.com', 'b', 'USER', 200099),
       ('Squidward', 'squidward@gmail.com', 'c', 'USER', 3),
       ('Mr. Krabs', 'krabs@gmail.com', 'd', 'USER', 4),
       ('Plankton', 'plankton@gmail.com', 'e', 'USER', 5),
       ('Bob', 'bob@gmail.com', 'f', 'USER', 60),
       ('Donald', 'donald@gmail.com', 'g', 'USER', 700),
       ('Tom', 'tom@yahoo.com', 'h', 'USER', 8000),
       ('Edwin', 'edwin@gmail.com', 'i', 'USER', 9001),
       ('Rick', 'rick@yahoo.com', 'j', 'USER', 102223);

INSERT INTO `posts` (user_id, content, timestamp)
VALUES (1, 'step 1 use a stick', 3846834),
       (1, 'look at this picture', 4274935),
       (3, 'fantastic kazoo player', 999999),
       (5, 'money', 9283874),
       (8, '2x1 + 5x2 + 2x3 = -28', 8374385),
       (2, 'got an A in computer', 2372647),
       (6, 'help', 7483652),
       (3, '3981749821', 8273864),
       (10, 'can I have a congrats', 8739463),
       (1, 'iudshfjdsd', 4873264);

INSERT INTO `post_likes` (user_id, post_id, timestamp)
VALUES (1, 1, 84093274),
       (1, 2, 84932787),
       (2, 1, 84937484),
       (4, 2, 372749832),
       (10, 10, 43274832),
       (9, 3, 83473298),
       (7, 2, 83497204),
       (6, 6, 382732132),
       (2, 10, 29832492),
       (7, 8, 283874982);

INSERT INTO `comments` (user_id, post_id, content, timestamp)
VALUES (1, 1, 'my own comment', 487328748),
       (2, 1, 'im writing on spongebob', 4728974932),
       (3, 3, 'comment3', 482743297),
       (7, 10, '?????? what', 84739874),
       (9, 2, 'hi spongebob', 83982798),
       (5, 9, 'welcome!', 384982749),
       (6, 3, 'i dont like you squidward', 38472949),
       (8, 4, 'money money money', 87721939),
       (4, 5, 'the answer is 3 -8 -2', 89738621),
       (10, 10, 'fkdsjnfkds', 87236287);

INSERT INTO `comment_likes` (user_id, comment_id, timestamp)
VALUES (2, 1, 840932374),
       (2, 2, 8493232787),
       (3, 1, 849373484),
       (7, 2, 37274459832),
       (10, 10, 435274832),
       (6, 3, 834723298),
       (6, 2, 834937204),
       (6, 6, 38272432132),
       (2, 10, 2943832492),
       (5, 8, 2838274982);

INSERT INTO `friends` (from_id, to_id, status, timestamp)
VALUES (1, 2, 'PENDING', 87846329),
       (2, 3, 'FRIEND', 123871498),
       (3, 2, 'FRIEND', 123871498),
       (6, 7, 'PENDING', 4328749827),
       (8, 7, 'FRIEND', 748736287432),
       (7, 8, 'FRIEND', 748736287432),
       (1, 10, 'PENDING', 987483249),
       (5, 6, 'PENDING', 748238420),
       (2, 5, 'FRIEND', 87438653),
       (5, 2, 'FRIEND', 87438653);

INSERT INTO `messages` (from_id, to_id, content, timestamp)
VALUES (1, 2, 'hello', 483297432),
       (2, 1, 'hello again', 8379213987),
       (1, 3, 'hi', 4),
       (1, 2, 'patrick what doing', 78239821),
       (1, 2, 'patrick where are you', 34872644),
       (1, 2, 'PATRICK', 372987483264),
       (10, 8, 'hi im rick', 7432687432),
       (8, 10, 'hi rick', 74326424),
       (5, 4, 'where is the secret formula', 74362874),
       (4, 5, 'who are you', 874873242);