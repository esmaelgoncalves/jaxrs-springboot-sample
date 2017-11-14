DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS image;


create table product (
	id identity primary key, 
	name varchar(30) not null,
	description varchar(50), 
	parent_id long,
	CONSTRAINT FK_PARENT_PROD_ID FOREIGN KEY(parent_id) REFERENCES product(id));

create table image (
	id identity primary key, 
	path varchar(255) not null, 
	product_id long,
	CONSTRAINT FK_PROD_ID FOREIGN KEY(product_id) REFERENCES product(id));