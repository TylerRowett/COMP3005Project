create table program_user
	(user_id	serial,
	 username	varchar(20),
	 password	varchar(20),
	 first_name	varchar(20),
	 last_name	varchar(20),
	 role		varchar(20),
	 primary key (user_id)
	);

create table placed_order
	(order_num			serial,
	 user_id			serial,
	 first_name			varchar(20),
	 last_name			varchar(20),
	 card_num			numeric(16),
	 billing_street		varchar(20),
	 billing_city		varchar(20),
	 billing_province	varchar(20),
	 billing_postal		varchar(20),
	 shipping_street	varchar(20),
	 shipping_city		varchar(20),
	 shipping_province	varchar(20),
	 shipping_postal	varchar(20),
	 date_placed		date,
	 primary key (order_num),
	 foreign key (user_id) references program_user 
	);
	
create table publisher
	(name				varchar(20),
	 street				varchar(20),
	 city				varchar(20),
	 province			varchar(20),
	 postal_code		varchar(20),
	 email				varchar(20),
	 banking_account	numeric(12,0),
	 primary key (name)
	);
	
create table book
	(ISBN			numeric(13,0),
	 publisher_name	varchar(20),
	 title			varchar(20),
	 genre			varchar(20),
	 num_pages		numeric(5,0),
	 price			numeric(5,0),
	 publisher_sales_percent numeric(2,0),
	 stock			numeric(5,0),
	 primary key (ISBN),
	 foreign key (publisher_name) references publisher
	);
	
create table book_author
	(ISBN		numeric(13,0),
	 first_name	varchar(20),
	 last_name	varchar(20),
	 primary key (ISBN, first_name, last_name),
	 foreign key (ISBN) references book
	);
	 
create table order_book
	(ISBN		numeric(13,0),
	 order_num	serial,
	 amount		numeric(5,0),
	 primary key (ISBN, order_num),
	 foreign key (ISBN) references book,
	 foreign key (order_num) references placed_order
	);
	
create table publisher_phone
	(name			varchar(20),
	 phone_number	numeric(10,0),
	 primary key (name, phone_number),
	 foreign key (name) references publisher
	);
