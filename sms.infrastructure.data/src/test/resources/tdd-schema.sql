-- POSTGRE

-- SET TIME ZONE 'UTC';

CREATE TABLE public.sms_message
(
  id            BIGSERIAL PRIMARY KEY,
  registered_on TIMESTAMP NOT NULL,
  sender_info   VARCHAR(32) NOT NULL,
  addressee_info VARCHAR(32) NOT NULL,
  body          VARCHAR(160) NOT NULL,
  expires_on    TIMESTAMP NULL
)
