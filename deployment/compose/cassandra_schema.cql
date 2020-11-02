drop keyspace if exists aura;
create keyspace aura with replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

drop table if exists aura.statistic;
create table aura.statistic
(
    user_id             text,
    engagement_name     text,
    consultations_count bigint,
    date                date,
    primary key ( user_id, engagement_name )
);
