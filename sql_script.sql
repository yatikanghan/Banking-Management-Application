
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


select * from transactions;
select t_id, transaction_id, sender_account, receiver_account, transaction_amount, transaction_type, transaction_date, transaction_remark from transactions;

SELECT account_id, customer_id, account_number, account_type, account_balance, account_status, account_created_at FROM account WHERE account_number = "1000000001";

select * from support;

INSERT INTO transactions (transaction_id,receiver_account,transaction_amount,transaction_type,transaction_remark) VALUES("1","100000001",100,"Debit","this is remark");


-- INSERT INTO support (customer_id,account_id,support_title,support_desc,support_status)
-- values(1,1,"account","reset password","Pending");

use bankingmanagement;
select support_id, customer_id, account_id, support_title, support_desc, support_created_at, admin_id, support_status from support;

Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values("admin@gmail.com","admin123","Yatik Anghan","Admin","Active");

insert into customers(customer_firstname,customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country) 
values("Yatik","Anghan","yatikanghan01@gmail.com","123456789","+4917663116950", "08-08-2003","Bornimer Str. 09","10711","Germany"); 

insert into account(customer_id, account_number, account_type, account_balance, account_status)
values("1","1000000001", "Saving Account","0", "Active");

select * from admin;
update admin set admin_name="Yatik", admin_role="Admin", admin_status="Active" where admin_id=1;


-- update account set account_status="Pending" where account_id=1;
-- select * from customers;
-- select * from account;
-- select * from admin;

SELECT * FROM account WHERE account_id = 1;

-- SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country FROM customers WHERE customer_emailid = "meet@gmail.com";

-- SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country from customers;
