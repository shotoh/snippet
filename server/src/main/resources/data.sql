INSERT INTO
    `user` (username, email, encrypted_pass, timestamp)
VALUES
    ('Spongebob Squarepants', 'spongebob@gmail.com', 'a', 1000),
    ('Patrick Star', 'patrick@gmail.com', 'b', 1001),
    ('Squidward Tentacles', 'squidward@gmail.com', 'c', 1002);

INSERT INTO
    `post` (user_id, title, content, timestamp)
VALUES
    (1, 'title1', 'content1', 1000),
    (1, 'title2', 'content2', 1001),
    (3, 'title3', 'content3', 1002);