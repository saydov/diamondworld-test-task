create table if not exists records (
    id int(8) primary key auto_increment,
    boss_id varchar(255) primary key,
    time timestamp not null default current_timestamp,
    data json not null
);