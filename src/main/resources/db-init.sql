create sequence if not exists HIBERNATE_SEQUENCE;

create table if not exists USERS
(
    ID       UUID    not null
        unique,
    USERNAME TEXT    not null,
    PASSWORD TEXT    not null,
    ENABLED  BOOLEAN not null,
    constraint USERS_PK
        primary key (ID)
);

create unique index if not exists USERS_ID_UINDEX
    on USERS (ID);

create table if not exists ARTIFACTS
(
    ID          UUID     not null
        unique,
    CREATED     DATETIME not null,
    USER_ID     UUID     not null,
    CATEGORY    TEXT     not null,
    DESCRIPTION TEXT,
    constraint ARTIFACTS_PK
        primary key (ID),
    constraint ARTIFACTS_USERS_ID_FK
        foreign key (USER_ID) references USERS (ID)
            on update cascade on delete cascade
);

create unique index if not exists ARTIFACTS_ID_UINDEX
    on ARTIFACTS (ID);

create table if not exists COMMENTS
(
    ID          UUID not null
        unique,
    ARTIFACT_ID UUID not null,
    USER_ID     UUID not null,
    CONTENT     TEXT not null,
    constraint COMMENTS_PK
        primary key (ID),
    constraint COMMENTS_ARTIFACTS_ID_FK
        foreign key (ARTIFACT_ID) references ARTIFACTS (ID),
    constraint COMMENTS_USERS_ID_FK
        foreign key (USER_ID) references USERS (ID)
            on update cascade on delete cascade
);

create unique index if not exists COMMENTS_ID_UINDEX
    on COMMENTS (ID);

