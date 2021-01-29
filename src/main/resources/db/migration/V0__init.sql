CREATE TABLE IF NOT EXISTS request_count (
  id SERIAL PRIMARY KEY,
  login VARCHAR(255),
  request_count INT NOT NULL DEFAULT 0,
  CONSTRAINT unique_login_constrain UNIQUE (login)
);
