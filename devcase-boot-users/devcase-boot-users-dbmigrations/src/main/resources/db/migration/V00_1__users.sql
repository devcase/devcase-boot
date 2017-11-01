
    create table credential (
       dtype varchar(31) not null,
        id varchar(255) not null,
        create_date datetime(6),
        update_date datetime(6),
        valid_until datetime(6),
        password varchar(255),
        user_id varchar(255) not null,
        primary key (id)
    );

    create table group_permission (
       id varchar(255) not null,
        create_date datetime(6),
        update_date datetime(6),
        valid_until datetime(6),
        role varchar(255),
        group_id varchar(255),
        primary key (id)
    );

    create table oauth_client_details (
       id varchar(255) not null,
        access_token_validity integer,
        additional_information varchar(1000),
        authorities varchar(1000),
        authorized_grant_types varchar(1000),
        autoapprove bit,
        client_id varchar(255),
        client_secret varchar(255),
        name varchar(300),
        refresh_token_validity integer,
        resource_ids varchar(1000),
        scope varchar(1000),
        web_server_redirect_uri varchar(1000),
        primary key (id)
    );

    create table user (
       id varchar(255) not null,
        create_date datetime(6),
        update_date datetime(6),
        valid_until datetime(6),
        enabled bit not null,
        locked bit not null,
        username varchar(255),
        primary key (id)
    );

    create table user_groups (
       user_id varchar(255) not null,
        groups_id varchar(255) not null
    );

    create table user_social_connection (
       id varchar(255) not null,
        access_token varchar(2000),
        display_name varchar(255),
        expire_time bigint,
        image_url varchar(255),
        profile_url varchar(255),
        provider_id varchar(255),
        provider_user_id varchar(255),
        rank integer not null,
        refresh_token varchar(2000),
        secret varchar(500),
        user_id varchar(255),
        primary key (id)
    );

    create table user_group (
       id varchar(255) not null,
        create_date datetime(6),
        update_date datetime(6),
        valid_until datetime(6),
        name varchar(255),
        primary key (id)
    );

    create table user_permission (
       id varchar(255) not null,
        create_date datetime(6),
        update_date datetime(6),
        valid_until datetime(6),
        role varchar(255),
        user_id varchar(255) not null,
        primary key (id)
    );

    alter table credential 
       add constraint UK_h1tr0aq20sqa5i1yxrqahlsuv unique (dtype, user_id);

    alter table oauth_client_details 
       add constraint UK_1xrh0ea69lj5eat0l1umx8ukk unique (client_id);

    alter table user 
       add constraint UK_t8tbwelrnviudxdaggwr1kd9b unique (username);

    alter table user_social_connection 
       add constraint UK_r5oe69d8wnxcqcrqj8y7440bf unique (provider_id, provider_user_id, user_id);

    alter table user_group 
       add constraint UK_37hmguxvqsle10tdc3ixjnxdv unique (name);

    alter table credential 
       add constraint FKpg7bdnqxpyhrt7f8soul9y7ne 
       foreign key (user_id) 
       references user (id);

    alter table group_permission 
       add constraint FKcmxi5j3hfoim3tlgwt7vwi6b6 
       foreign key (group_id) 
       references user_group (id);

    alter table user_groups 
       add constraint FK2nhhy7f9gn8ay6bl34e9kex95 
       foreign key (groups_id) 
       references user_group (id);

    alter table user_groups 
       add constraint FKxgk67l5yp8458l39rog6nppe 
       foreign key (user_id) 
       references user (id);

    alter table user_permission 
       add constraint FK7c2x74rinbtf33lhdcyob20sh 
       foreign key (user_id) 
       references user (id);
