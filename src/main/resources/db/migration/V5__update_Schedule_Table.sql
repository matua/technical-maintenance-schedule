alter table schedules
    add grabbed_execution_date_time TIMESTAMPTZ(6);
alter table schedules
    add released_execution_date_time TIMESTAMPTZ(6);
