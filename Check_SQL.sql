create schema bankingmanagement;
use bankingmanagement;


create table customers(
customer_id int auto_increment primary key,
customer_firstname varchar(50) not null,
customer_lastname varchar(50) not null,
customer_emailid varchar(60) unique not null,
customer_password varchar(60) not null,
customer_mobile varchar(17) not null,
customer_dob varchar(30) not null,
customer_address varchar(60) not null,
customer_postcode varchar(10) not null,
customer_country varchar(50) not null
);

create table account(
account_id int auto_increment primary key,
customer_id int,
account_number varchar(50) unique not null,
account_type varchar(50) not null,
account_balance varchar(50) not null,
account_status varchar(50) not null,
account_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (customer_id) REFERENCES customers(customer_id));	

create table admin(
admin_id int auto_increment primary key,
admin_emailid varchar(50) unique not null,
admin_password varchar(50) not null,
admin_name varchar(50) not null,
admin_role varchar(50) not null,
admin_status varchar(50) not null,
admin_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values("admin@gmail.com","admin123","Yatik Anghan","Admin","Active");

create table support(
support_id int auto_increment primary key,
customer_id int,
account_id int,
support_title varchar(60) not null,
support_desc varchar(500) not null,
support_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
admin_id int,
support_status varchar(50) not null,
FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
FOREIGN KEY (account_id) REFERENCES account(account_id),
FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);

CREATE TABLE transactions (
    t_id int AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(50) NOT NULL UNIQUE,
    sender_account VARCHAR(20) default "NA",
    receiver_account VARCHAR(20) default "NA",
    transaction_amount int NOT NULL,
    transaction_type varchar(50) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    admin_id int,
    transaction_remark TEXT,
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id)
);

CREATE TABLE fixeddeposit (
	id int auto_increment PRIMARY KEY,
    account_id INT NOT NULL,
    principalAmount DOUBLE NOT NULL,
    interestRate DOUBLE NOT NULL,
    tenure INT NOT NULL,
    startDate DATE NOT NULL,
    maturityDate DATE NOT NULL,
    maturityAmount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

-- Customer Table

-- insert into customers(customer_firstname,customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country) 
-- values("Yatik","Anghan","yatikanghan01@gmail.com","123456789","+4917663116950", "08-08-2003","Bornimer Str. 09","10711","Germany"); 


INSERT INTO customers (customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country)
VALUES ('Yatik', 'Anghan', 'yatik@gmail.com', 'password123', '+4917663116950', '2003-08-08', '09 Bornimer St, Berlin', '10711', 'Germany');

INSERT INTO customers (customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country)
VALUES ('Mayank', 'Dobariya', 'mayank@gmail.com', 'password123', '+4917663116951', '2001-05-08', '456 Park Ave, Munich', '80331', 'Germany');

INSERT INTO customers (customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country)
VALUES ('Ayush', 'Pandav', 'ayush@gmail.com', 'password123', '+4917663116952', '2003-08-07', '789 Elm St, Hamburg', '20095', 'Germany');

INSERT INTO customers (customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country)
VALUES ('Bansi', 'Sonani', 'bansi@gmail.com', 'password123', '+4917663116953', '2001-03-25', '321 Birch St, Frankfurt', '60311', 'Germany');

INSERT INTO customers (customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country)
VALUES ('Pavan', 'Goti', 'pavan@gmail.com', 'password123', '+4917663116954', '2002-01-12', '987 Oak St, Stuttgart', '70173', 'Germany');



-- Account Table


-- insert into account(customer_id, account_number, account_type, account_balance, account_status)
-- values("1","1000000001", "Saving Account","0", "Active");

INSERT INTO account (customer_id, account_number, account_type, account_balance, account_status)
VALUES (1, 'DE1000000001', 'Savings', '5000', 'Active');

INSERT INTO account (customer_id, account_number, account_type, account_balance, account_status)
VALUES (2, 'DE1000000002', 'Current', '10000', 'Active');

INSERT INTO account (customer_id, account_number, account_type, account_balance, account_status)
VALUES (3, 'DE1000000003', 'Savings', '7500', 'Deactive');

INSERT INTO account (customer_id, account_number, account_type, account_balance, account_status)
VALUES (4, 'DE1000000004', 'Current', '2000', 'Pending');

INSERT INTO account (customer_id, account_number, account_type, account_balance, account_status)
VALUES (5, 'DE1000000005', 'Savings', '15000', 'Closed');



-- Admin insert data

Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values("admin@gmail.com","admin123","Yatik Anghan","Admin","Active");
Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values("mayank@gmail.com","admin123","Mayank Dobariya","Casher","Active");
Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values("ayush@gmail.com","admin123","Ayush Pandav","Support","Active");



 -- Support Table
 
 
 
INSERT INTO support (customer_id, account_id, support_title, support_desc, support_status)
VALUES (1, 1, "Login Issue", "Customer is unable to log in to their account.", "Pending");

INSERT INTO support (customer_id, account_id, support_title, support_desc, support_status)
VALUES (2, 2, "Transaction Failure", "A recent transaction failed, and the amount was deducted.", "Resolved");

INSERT INTO support (customer_id, account_id, support_title, support_desc, support_status)
VALUES (3, 3, "Account Reactivation", "Request to reactivate a deactivated savings account.", "Pending");

INSERT INTO support (customer_id, account_id, support_title, support_desc, support_status)
VALUES (4, 4, "Wrong Account Details", "The account details displayed are incorrect.", "Resolved");




-- Transaction Table

INSERT INTO transactions (transaction_id, sender_account, receiver_account, transaction_amount, transaction_type, transaction_remark)
VALUES ('TXN10001', 'DE1000000001', 'DE1000000002', 1500, 'Transfer', 'Fund transfer to Mayank Dobariya');

INSERT INTO transactions (transaction_id, sender_account, receiver_account, transaction_amount, transaction_type, transaction_remark)
VALUES ('TXN10002', 'DE1000000002', 'DE1000000003', 2000, 'Transfer', 'Payment for services');

INSERT INTO transactions (transaction_id, sender_account, receiver_account, transaction_amount, transaction_type, transaction_remark)
VALUES ('TXN10003', 'DE1000000003', 'DE1000000004', 500, 'Transfer', 'Bill payment');

INSERT INTO transactions (transaction_id, sender_account, receiver_account, transaction_amount, transaction_type, transaction_remark)
VALUES ('TXN10004', 'DE1000000004', 'DE1000000005', 10000, 'Deposit', 'Fixed deposit investment');

INSERT INTO transactions (transaction_id, sender_account, receiver_account, transaction_amount, transaction_type, transaction_remark)
VALUES ('TXN10005', 'DE1000000005', 'DE1000000001', 3000, 'Withdrawal', 'Cash withdrawal from savings account');



-- Fixed Deposit

INSERT INTO fixeddeposit (account_id, principalAmount, interestRate, tenure, startDate, maturityDate, maturityAmount, status)
VALUES (1, 5000, 5.5, 12, '2024-03-01', '2025-03-01', 5300, 'Active');

INSERT INTO fixeddeposit (account_id, principalAmount, interestRate, tenure, startDate, maturityDate, maturityAmount, status)
VALUES (2, 10000, 6.0, 24, '2023-06-15', '2025-06-15', 11200, 'Active');

INSERT INTO fixeddeposit (account_id, principalAmount, interestRate, tenure, startDate, maturityDate, maturityAmount, status)
VALUES (3, 7500, 5.8, 18, '2024-01-10', '2025-07-10', 7950, 'Active');

INSERT INTO fixeddeposit (account_id, principalAmount, interestRate, tenure, startDate, maturityDate, maturityAmount, status)
VALUES (4, 2000, 4.5, 12, '2024-05-20', '2025-05-20', 2090, 'Closed');

INSERT INTO fixeddeposit (account_id, principalAmount, interestRate, tenure, startDate, maturityDate, maturityAmount, status)
VALUES (5, 15000, 6.2, 36, '2022-11-01', '2025-11-01', 17900, 'Active');


