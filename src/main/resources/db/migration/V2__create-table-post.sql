CREATE TABLE post(
         id uuid primary key,
         title varchar not null,
         description varchar not null,
         creation_date date,
         update_date date,
         post_type varchar not null,
         image_name varchar,
         user_registration varchar REFERENCES users(username),
         user_update varchar REFERENCES users(username)

);