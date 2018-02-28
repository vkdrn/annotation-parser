DROP TABLE IF EXISTS version;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1;

CREATE TABLE version
(
  id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  author  VARCHAR NOT NULL,
  comment VARCHAR NOT NULL,
  date    VARCHAR NOT NULL,
  name    VARCHAR NOT NULL,
  type    VARCHAR NOT NULL
);
CREATE UNIQUE INDEX version_unique_idx
  ON version (id);