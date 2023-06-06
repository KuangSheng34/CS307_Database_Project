create table if not exists public.Authors
(
    Author_name varchar(50)  not null
        primary key ,
    Author_ID   varchar(20)
        unique,
    Author_Registration_Time timestamp(0),
    Author_Phone  varchar(20)
);

create table if not exists public.Followers
(
    Followers varchar(50) not null
        references authors(author_name),
    Followed  varchar(50) not null
        references authors(author_name),
    primary key (Followers, Followed)
);

create table if not exists public.births
(
    author_name  varchar(50) not null
    primary key references authors(author_name),
    birth_year numeric(4),
    birth_month numeric(2),
    birth_day numeric(2)
);

create table if not exists public.posts
(
    post_id  int not null
        primary key ,
    title    text not null,
    content  text not null,
    posting_time  timestamp(0) not null
);

create table if not exists public.categories
(
    post_id  int not null
       references posts(post_id),
    category varchar(50) not null ,
    primary key (post_id, category)
);

create table if not exists public.areas
(
    post_id int not null
       primary key references posts(post_id),
    posting_city   varchar(50) not null ,
    posting_country  varchar(50) not null
);

create table if not exists public.post_author_relation
(
    post_id int not null
       references posts(post_id),
    author_name varchar(50) not null
       references authors(author_name),
    relation varchar(10) not null,
    primary key (post_id, author_name, relation)
);

create table if not exists public.replies
(
    reply_id int not null
       primary key,
    floor int not null,
    post_id int not null
       references posts(post_id),
    reply_author varchar(50) not null
        references authors(author_name),
    reply_star int not null,
    reply_content text not null,
    unique (post_id, floor, reply_author, reply_star, reply_content)
);

create table if not exists public.upper_replies
(
    reply_id int not null
    references replies(reply_id),
    upper_reply_id int
    references replies(reply_id),
    primary key (reply_id, upper_reply_id)
);