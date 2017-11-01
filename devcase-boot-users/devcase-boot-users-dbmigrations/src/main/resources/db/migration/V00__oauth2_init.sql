CREATE TABLE oauth_client_token (
  token_id          VARCHAR(256),
  token             LONGVARBINARY,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256)
);

CREATE TABLE oauth_access_token (
  token_id          VARCHAR(256),
  token             LONGVARBINARY,
  authentication_id VARCHAR(256),
  user_name         VARCHAR(256),
  client_id         VARCHAR(256),
  authentication    LONGVARBINARY,
  refresh_token     VARCHAR(256)
);

CREATE TABLE oauth_refresh_token (
  token_id       VARCHAR(256),
  token          LONGVARBINARY,
  authentication LONGVARBINARY
);

CREATE TABLE oauth_code (
  code           VARCHAR(256),
  authentication LONGVARBINARY
);

CREATE TABLE oauth_approvals (
  userid         VARCHAR(256),
  clientid       VARCHAR(256),
  scope          VARCHAR(256),
  status         VARCHAR(10),
  expiresat      TIMESTAMP,
  lastmodifiedat TIMESTAMP
);