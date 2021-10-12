create table users
(
    id                 bigint generated by default as identity primary key,
    role               varchar(20)  not null,
    first_name         varchar(20)  not null,
    last_name          varchar(20)  not null,
    email              varchar(50)  not null unique,
    encrypted_password varchar(500) not null,
    active             boolean      not null,
    on_duty            boolean      not null,
    field_hours        int          not null
);

create table terminals
(
    id        bigint generated by default as identity primary key,
    type      varchar(20)  not null,
    name      varchar(20)  not null unique,
    location  varchar(200) not null,
    disabled  boolean      not null,
    longitude double precision,
    latitude  double precision
);

create table tasks
(
    id          bigint generated by default as identity primary key,
    description varchar(300) not null,
    priority    varchar(10),
    frequency   int          not null
);

create table schedules
(
    id                        bigint generated by default as identity primary key,
    terminal                  bigint not null,
    task                      bigint not null,
    status                    varchar(20),
    user_id                   bigint,
    start_execution_date_time timetz,
    end_execution_date_time   timetz,
    foreign key (terminal) references terminals (id),
    foreign key (task) references tasks (id),
    foreign key (user_id) references users (id)
);