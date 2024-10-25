create table if not exists records (
    id integer primary key autoincrement,
    boss_id varchar(255),
    time timestamp not null default current_timestamp,
    data json not null
);