INSERT INTO users (phone, username, password, ENABLED) VALUES
    ('996 (777) 12-34-56', 'user1', 'password1', true),
    ('996 (554) 23-45-67', 'user2', 'password2', true),
    ('996 (783) 34-56-78', 'user3', 'password3', true),
    ('12345', '12345', 'qwerty', true);

INSERT INTO accounts (user_phone, currency, balance) VALUES
((SELECT phone from USERS where USERNAME like 'user1'), 'USD', 1000.00),
((SELECT phone from USERS where USERNAME like 'user2'), 'EUR', 1500.00),
((SELECT phone from USERS where USERNAME like 'user3'), 'KGS', 2000.00);

INSERT INTO transactions (sender_account_id, receiver_account_id, amount, currency, status, transaction_time) VALUES
((select id from ACCOUNTS where USER_PHONE like '996 (777) 12-34-56'), (select id from ACCOUNTS where USER_PHONE like '996 (554) 23-45-67'), 100.00, 'USD', 'pending', '2023-01-01 10:00:00'),
((select id from ACCOUNTS where USER_PHONE like '996 (554) 23-45-67'), (select id from ACCOUNTS where USER_PHONE like '996 (783) 34-56-78'), 200.00, 'EUR', 'completed', '2023-01-02 11:00:00'),
((select id from ACCOUNTS where USER_PHONE like '996 (783) 34-56-78'), (select id from ACCOUNTS where USER_PHONE like '996 (777) 12-34-56'), 50.00, 'USD', 'pending', '2023-01-03 12:00:00');

INSERT INTO approvals (transaction_id, is_approved, approval_time) VALUES
((select id from TRANSACTIONS where RECEIVER_ACCOUNT_ID = (select id from ACCOUNTS where USER_PHONE like '996 (783) 34-56-78')), TRUE, '2023-01-01 10:05:00'),
((select id from TRANSACTIONS where RECEIVER_ACCOUNT_ID = (select id from ACCOUNTS where USER_PHONE like '996 (554) 23-45-67')), FALSE, '2023-01-01 10:05:00'),
((select id from TRANSACTIONS where RECEIVER_ACCOUNT_ID = (select id from ACCOUNTS where USER_PHONE like '996 (777) 12-34-56')), FALSE, '2023-01-01 10:05:00');

INSERT INTO rollbacks (transaction_id, rollback_time, reason) VALUES
((select id from TRANSACTIONS where RECEIVER_ACCOUNT_ID = (select id from ACCOUNTS where USER_PHONE like '996 (554) 23-45-67')), '2023-01-02 11:05:00', 'Error in transaction');

INSERT INTO AUTHORITIES (ROLE)
VALUES
    ('DEFAULT'),
    ('ADMIN');

INSERT INTO USER_AUTHORITY (USER_PHONE, AUTHORITY_ID) values
('996 (777) 12-34-56', (select id from AUTHORITIES where role like 'DEFAULT')),
('996 (554) 23-45-67', (select id from AUTHORITIES where role like 'DEFAULT')),
('996 (783) 34-56-78', (select id from AUTHORITIES where role like 'DEFAULT')),
('12345', (select id from AUTHORITIES where role like 'ADMIN'));
