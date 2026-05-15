CREATE SEQUENCE member_seq START WITH 1000 INCREMENT BY 1;

CREATE TABLE member
(
    id         BIGINT    PRIMARY KEY DEFAULT nextval('member_seq'),
    user_id    BIGINT    NOT NULL UNIQUE REFERENCES app_user (id),
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL CHECK (email ~* '^[^@]+@[^@]+\.[^@]+$'),
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_member_user_id ON member (user_id);
