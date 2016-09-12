CREATE TABLE public.sms_message
(
  id            BIGINT AUTO_INCREMENT PRIMARY KEY ,
  registered_on TIMESTAMP NOT NULL,
  sender_info   VARCHAR(32) NOT NULL,
  receiver_info VARCHAR(32) NOT NULL,
  body          VARCHAR(160) NOT NULL,
  expires_on    TIMESTAMP NULL
)
