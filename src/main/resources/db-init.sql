create table if not exists USERS
(
    ID       UUID unique not null,
    USERNAME TEXT        not null,
    PASSWORD TEXT        not null,
    ENABLED  BOOLEAN     not null,
    constraint USERS_PK
        primary key (ID)
);

create unique index if not exists USERS_ID_UINDEX
    on USERS (ID);

create table if not exists ARTIFACTS
(
    ID          UUID unique not null,
    CREATED     DATETIME    not null,
    USER_ID     TEXT        not null,
    CATEGORY    TEXT        not null,
    DESCRIPTION TEXT,
    constraint ARTIFACTS_PK
        primary key (ID)
);

create table if not exists COMMENTS
(
    ID          UUID unique not null,
    ARTIFACT_ID UUID        not null,
    USER_ID     TEXT        not null,
    CONTENT     TEXT        not null,
    constraint COMMENTS_PK
        primary key (ID),
    constraint COMMENTS_ARTIFACTS_ID_FK
        foreign key (ARTIFACT_ID) references ARTIFACTS (ID)
);

create unique index if not exists COMMENTS_ID_UINDEX
    on COMMENTS (ID);

create unique index if not exists ARTIFACTS_ID_UINDEX
    on ARTIFACTS (ID);

