CREATE TABLE hotels
(
    id       serial primary key,
    name     varchar(100),
    status  varchar(50),
    quantity varchar(20),
    price    integer,
    views    int not null default 0
);

INSERT INTO hotels (id, name, status, quantity,  price, views)
VALUES
    (default, 'Наслаждение', '1', 'Vip', 1550,0),
    (default, 'Комфорт', '2', 'Люкс',  2220,0),
    (default, 'Семейный отдых', '4', 'Эконом',  2330,0);
