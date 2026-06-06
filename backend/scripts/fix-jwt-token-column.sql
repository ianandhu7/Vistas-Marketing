-- Run once on local MySQL if login returns HTTP 500 with:
-- "Data too long for column 'jwt_token'"
USE vlearning;
ALTER TABLE user_device MODIFY COLUMN JWT_TOKEN VARCHAR(4000);
