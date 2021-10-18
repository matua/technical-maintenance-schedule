alter table tasks
    add schedule_id bigint;

alter table tasks
    add constraint tasks_tasks_id_fk
        foreign key (schedule_id) references tasks (id);