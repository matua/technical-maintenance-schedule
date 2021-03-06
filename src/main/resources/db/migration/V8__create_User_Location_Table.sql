create table user_location
(
    id        bigint generated by default as identity primary key,
    user_id   bigint not null
        constraint user_id___fk
            references users,
    latitude  double precision,
    longitude double precision
);

create unique index user_location_id_uindex
    on user_location (id);