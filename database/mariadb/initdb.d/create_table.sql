CREATE TABLE `store_review_info` (
    `id` bigint	AUTO_INCREMENT NOT NULL,
    `average_rating`	float default 0.0	NOT NULL,
    `review_count` int default 0 NOT NULL,
    PRIMARY KEY (`id`),
    constraint average_rating_range check (average_rating between 0 and 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Store Review Information';

CREATE TABLE `store` (
    `id` bigint	AUTO_INCREMENT NOT NULL,
    `store_review_info_id` bigint NOT NULL,
    `name` varchar(255) NOT NULL,
    `store_type` varchar(10) NOT NULL,
    `start_time` time NOT NULL,
    `last_time` time NOT NULL,
    `interval_time` int NOT NULL,
    `distance` float NOT NULL,
    PRIMARY KEY (`id`),
    INDEX store_name_idx (name),
    FOREIGN KEY (`store_review_info_id`) REFERENCES `store_review_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `member` (
    `id` bigint	AUTO_INCREMENT NOT NULL,
    `store_id`	bigint,
    `username`	varchar(255)	NOT NULL,
    `password`	varchar(255)	NOT NULL,
    `nickname`	varchar(255)	NOT NULL,
    `member_role`	varchar(10)  NOT NULL,
    `detail` varchar(50) NULL,
    `street` varchar(100) NULL,
    `zipcode` varchar(10) NULL,
    `phone` varchar(11) NULL UNIQUE,
    PRIMARY KEY (`id`),
    UNIQUE INDEX username_idx (username),
    UNIQUE INDEX nickname_idx (nickname),
    INDEX username_store_id (username, store_id),
    FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User';

CREATE TABLE `review` (
    `id`	bigint	AUTO_INCREMENT NOT NULL,
    `store_id`	bigint NOT NULL,
    `content`	varchar(50)	NOT NULL,
    `rating` int NOT NULL,
    `created_at`	datetime	DEFAULT CURRENT_TIMESTAMP   NOT NULL,
    `modified_at`	datetime	NOT NULL,
    `created_by`	varchar(255)	NOT NULL,
    `modified_by`	varchar(255)	NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
    constraint rating_range check (rating between 0 and 5),
    INDEX id_and_created_by (id, created_by),
    INDEX store_id_and_created_by (store_id, created_by),
    INDEX store_id_idx (store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `booking` (
    `id`	bigint	AUTO_INCREMENT NOT NULL,
    `store_id`	bigint NOT NULL,
    `phone`	varchar(11) NOT NULL,
    `approve`	bit	NOT NULL,
    `created_at`	datetime	DEFAULT CURRENT_TIMESTAMP   NOT NULL,
    `created_by`	varchar(255)	NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
    INDEX booking_id_and_created_by (id, created_by),
    INDEX created_at_idx (created_at),
    INDEX store_id_and_created_at (store_id, created_at),
    INDEX store_id_and_created_by (store_id, created_by),
    INDEX store_id_and_created_at_and_approve (store_id, created_at, approve)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
