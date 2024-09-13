INSERT INTO
    `users` (username, email, encrypted_pass, timestamp)
VALUES
    ('Spongebob Squarepants', 'spongebob@gmail.com', 'a', 1000),
    ('Patrick Star', 'patrick@gmail.com', 'b', 1001),
    ('Squidward Tentacles', 'squidward@gmail.com', 'c', 1002);

INSERT INTO
    `posts` (user_id, title, content, timestamp)
VALUES
    (1, 'title1', 'content1', 1000),
    (1, 'title2', 'content2', 1001),
    (3, 'title3', 'content3', 1002);

INSERT INTO
    `likes` (user_id, post_id, timestamp)
VALUES
    (1, 1, 1400),
    (1, 2, 1500),
    (2, 1, 1600);

INSERT INTO
    `comments` (user_id, post_id, content, timestamp)
VALUES
    (1, 1, 'comment1', 1401),
    (2, 1, 'comment2', 1501),
    (3, 3, 'comment3', 1601);