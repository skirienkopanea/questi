create table lecture
(
    id             bigint  not null
        constraint lecture_pkey
            primary key,
    is_ongoing     boolean not null,
    moderator_link varchar(255),
    name           varchar(255),
    public_link    varchar(255),
    end_time       timestamp,
    start_time     timestamp
);

alter table lecture
    owner to dbadmin;

