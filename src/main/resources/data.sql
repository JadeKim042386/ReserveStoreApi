insert into member(username, password, nickname, zipcode, street, detail, phone) values
('admin', 'pw', 'admin', '12345', 'street', 'detail', '01011111111'),
('store', 'pw', 'store', '12345', 'street', 'detail', '01011111111');

insert into store(member_id, name, store_type, start_time, last_time, interval_time) values
(2, 'caffe', 'CAFFE', '09:00', '18:00', 30);

insert into review(store_id, content, rating, created_at, modified_at, created_by, modified_by) values
(1, 'content', 5, now(), now(), 'admin', 'admin');

insert into booking(store_id, approve, created_at, modified_at, created_by, modified_by) values
(1, TRUE, now(), now(), 'admin', 'admin');
