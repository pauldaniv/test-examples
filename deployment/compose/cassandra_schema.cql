create keyspace aura
            WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TABLE aura.statistic
(
    user_id             text,
    engagement_name     text,
    consultations_count bigint,
    date                date,
    primary key ( user_id, engagement_name )
);
