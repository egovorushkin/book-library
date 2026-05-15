CREATE SEQUENCE loan_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE loan
(
    id          BIGINT    PRIMARY KEY DEFAULT nextval('loan_seq'),
    member_id   BIGINT    NOT NULL REFERENCES member (id),
    book_id     BIGINT    NOT NULL REFERENCES book (id),
    borrowed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    returned_at TIMESTAMP,
    status      VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'RETURNED')),
    CONSTRAINT chk_loan_returned_after_borrowed CHECK (returned_at IS NULL OR returned_at > borrowed_at),
    CONSTRAINT chk_loan_status_matches_returned CHECK (
        (status = 'ACTIVE'   AND returned_at IS NULL) OR
        (status = 'RETURNED' AND returned_at IS NOT NULL)
    )
);

CREATE INDEX idx_loan_member_id ON loan (member_id);
CREATE INDEX idx_loan_book_id   ON loan (book_id);
CREATE INDEX idx_loan_status    ON loan (status);
