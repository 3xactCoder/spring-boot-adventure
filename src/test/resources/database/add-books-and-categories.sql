INSERT INTO categories (id, name, description, is_deleted)
VALUES (100, 'Fantasy', 'Fantasy books', FALSE);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (100, 'The Hobbit', 'J.R.R. Tolkien', '978-0-261-10221-7', 19.99, 'Fantasy book', 'image.jpg', FALSE);

INSERT INTO books_categories (book_id, category_id)
VALUES (100, 100);