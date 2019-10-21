delete from meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

insert into meals (user_id,date_time,description,calories)
select t.user_id, t.date_time, t.description, t.calories from
(select 1 ord, 100000 user_id, to_timestamp('21/10/2019 10:00','dd/mm/yyyy hh24:mi') date_time, 'Завтрак' description, 500 calories
union
select 2 ord, 100000 user_id, to_timestamp('21/10/2019 13:00','dd/mm/yyyy hh24:mi') date_time, 'Обед' description, 1000 calories
union
select 3 ord, 100000 user_id, to_timestamp('21/10/2019 18:00','dd/mm/yyyy hh24:mi') date_time, 'Ужин' description, 500 calories
union
select 4 ord, 100000 user_id, to_timestamp('22/10/2019 10:00','dd/mm/yyyy hh24:mi') date_time, 'Завтрак' description, 500 calories
union
select 5 ord, 100000 user_id, to_timestamp('22/10/2019 13:00','dd/mm/yyyy hh24:mi') date_time, 'Обед' description, 1000 calories
union
select 6 ord, 100000 user_id, to_timestamp('22/10/2019 18:00','dd/mm/yyyy hh24:mi') date_time, 'Ужин' description, 510 calories
union
select 7 ord, 100001 user_id, to_timestamp('21/10/2019 10:00','dd/mm/yyyy hh24:mi') date_time, 'Завтрак' description, 500 calories
union
select 8 ord, 100001 user_id, to_timestamp('21/10/2019 13:00','dd/mm/yyyy hh24:mi') date_time, 'Обед' description, 1000 calories
union
select 9 ord, 100001 user_id, to_timestamp('21/10/2019 18:00','dd/mm/yyyy hh24:mi') date_time, 'Ужин' description, 500 calories
union
select 10 ord, 100001 user_id, to_timestamp('22/10/2019 10:00','dd/mm/yyyy hh24:mi') date_time, 'Завтрак' description, 500 calories
union
select 11 ord, 100001 user_id, to_timestamp('22/10/2019 13:00','dd/mm/yyyy hh24:mi') date_time, 'Обед' description, 1000 calories
union
select 12 ord, 100001 user_id, to_timestamp('22/10/2019 18:00','dd/mm/yyyy hh24:mi') date_time, 'Ужин' description, 510 calories) t
order by t.ord


