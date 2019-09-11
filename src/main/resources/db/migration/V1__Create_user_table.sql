create table USER
(
    ID           bigint  auto_increment primary key not null,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        varchar(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
);
