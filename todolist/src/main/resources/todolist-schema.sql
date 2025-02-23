DROP DATABASE IF EXISTS todolist;

CREATE DATABASE todolist;
USE todolist;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
     `id` int NOT NULL AUTO_INCREMENT,
     `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `UK_ob8kqyqqgmefl0aco34akdtpe`(`email` ASC) USING BTREE
);

DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo`  (
     `id` int NOT NULL AUTO_INCREMENT,
     `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `star` int NOT NULL,
     `start_date` datetime(6) NULL DEFAULT NULL,
     `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
     `visibility` bit(1) NULL DEFAULT NULL,
     `user_id` int NULL DEFAULT NULL,
     `finish` bit(1) NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
);