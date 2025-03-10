
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


Insert into admin (admin_emailid,admin_password,admin_name,admin_role,admin_status) values("admin@gmail.com","admin123","Yatik Anghan","Admin","Active");

insert into customers(customer_firstname,customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country) 
values("Yatik","Anghan","yatikanghan01@gmail.com","123456789","+4917663116950", "08-08-2003","Bornimer Str. 09","10711","Germany"); 

insert into account(customer_id, account_number, account_type, account_balance, account_status)
values("1","1000000001", "Saving Account","0", "Active");

select * from customers;


update account set account_status="Pending" where account_id=1;
select * from account;
select * from admin;

SELECT * FROM account WHERE account_id = 1;

SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country FROM customers WHERE customer_emailid = "meet@gmail.com";

SELECT customer_id, customer_firstname, customer_lastname, customer_emailid, customer_password, customer_mobile, customer_dob, customer_address, customer_postcode, customer_country from customers;
