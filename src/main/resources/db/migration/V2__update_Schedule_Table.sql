alter table schedules
    add date_time_created TIMESTAMPTZ(6) not null default now();
