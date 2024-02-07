CREATE TABLE `store_review_info` (
    `id` bigint	AUTO_INCREMENT NOT NULL,
    `average_rating`	float default 0.0	NOT NULL,
    `review_count` bigint default 0 NOT NULL,
    PRIMARY KEY (`id`)
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
    `phone` varchar(11) NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`username`),
    UNIQUE (`nickname`),
    UNIQUE (`phone`),
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
    constraint rating_range check (rating between 0 and 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `booking` (
    `id`	bigint	AUTO_INCREMENT NOT NULL,
    `store_id`	bigint NOT NULL,
    `phone`	varchar(11) NOT NULL,
    `approve`	bit	NOT NULL,
    `created_at`	datetime	DEFAULT CURRENT_TIMESTAMP   NOT NULL,
    `created_by`	varchar(255)	NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
