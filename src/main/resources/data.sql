insert into member(username, password, nickname, member_role, zipcode, street, detail, phone) values
('admin', '$2a$10$0o2ZARKB8g0zDfRoZlUmvuWn.V38RoOWjd6Hp4jEyeaGK8GP18aq6', 'admin', 'ADMIN', '12345', 'street', 'detail', '01011111111'),
('store', '$2a$10$0o2ZARKB8g0zDfRoZlUmvuWn.V38RoOWjd6Hp4jEyeaGK8GP18aq6', 'store', 'STORE', '12345', 'street', 'detail', '01011111111'),
('bar', '$2a$10$0o2ZARKB8g0zDfRoZlUmvuWn.V38RoOWjd6Hp4jEyeaGK8GP18aq6', 'bar', 'STORE', '12345', 'street', 'detail', '01011111111');

insert into store(member_id, name, store_type, start_time, last_time, interval_time) values
(2, 'caffe', 'CAFFE', '09:00', '18:00', 30),
(3, 'bar', 'BAR', '09:00', '18:00', 30);

insert into store_review_info(store_id, average_rating, review_count) values
(1, 5.0, 1);

insert into review(store_id, content, rating, created_at, modified_at, created_by, modified_by) values
(1, 'content', 5, now(), now(), 'admin', 'admin');

insert into booking(store_id, approve, created_at, created_by) values
(1, TRUE, now(), 'admin'),
(2, TRUE, '2024-02-02T00:00:00', 'admin');
