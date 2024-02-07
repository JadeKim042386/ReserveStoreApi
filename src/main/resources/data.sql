insert into store_review_info(average_rating, review_count) values
(5.0, 1),
(4.0, 1);

insert into store(store_review_info_id, name, store_type, start_time, last_time, interval_time, distance) values
(1, 'caffe', 'CAFFE', '09:00', '18:00', 30, 31.1),
(2, 'bar', 'BAR', '09:00', '18:00', 30, 22.1);

insert into member(store_id, username, password, nickname, member_role, zipcode, street, detail, phone) values
(null, 'admin', '$2a$10$0o2ZARKB8g0zDfRoZlUmvuWn.V38RoOWjd6Hp4jEyeaGK8GP18aq6', 'admin', 'ADMIN', '12345', 'street', 'detail', '01011111112'),
(1, 'store', '$2a$10$0o2ZARKB8g0zDfRoZlUmvuWn.V38RoOWjd6Hp4jEyeaGK8GP18aq6', 'store', 'STORE', '12345', 'street', 'detail', '01011111113'),
(2, 'bar', '$2a$10$0o2ZARKB8g0zDfRoZlUmvuWn.V38RoOWjd6Hp4jEyeaGK8GP18aq6', 'bar', 'STORE', '12345', 'street', 'detail', '01011111114');

insert into review(store_id, content, rating, created_at, modified_at, created_by, modified_by) values
(1, 'content', 5, now(), now(), 'admin', 'admin'),
(2, 'content', 4, now(), now(), 'admin', 'admin');

insert into booking(store_id, phone, approve, created_at, created_by) values
(1, '01011111111', TRUE, now(), 'admin'),
(2, '01011111111', TRUE, '2024-02-02T00:00:00', 'admin');
