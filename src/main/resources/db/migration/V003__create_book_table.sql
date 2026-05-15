CREATE SEQUENCE book_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE book
(
    id               BIGINT       PRIMARY KEY DEFAULT nextval('book_seq'),
    title            VARCHAR(200) NOT NULL,
    author           VARCHAR(150) NOT NULL,
    isbn             VARCHAR(20),
    total_copies     INTEGER      NOT NULL CHECK (total_copies >= 1),
    available_copies INTEGER      NOT NULL CHECK (available_copies >= 0),
    CONSTRAINT chk_book_copies CHECK (available_copies <= total_copies)
);
