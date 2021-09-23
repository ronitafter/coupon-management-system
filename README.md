# coupon-management-system
coupon management system; java, mysql

Coupon System Project
author: Ronit After
date: 19/09/21

 PROJECT REQUIREMENTS
 
MySQL Workbench version:8.0
user name: "root", 
password:"wNp2Y2"
url: "jdbc:mysql://localhost:3306/coupons_system";
Eclipse IDE for Enterprise Java and Web Developers
Version: 4.19.0.20210311-1200
OS: Windows 10, v.10.0, x86_64 / win32
Java version: 11.0.10

PROJECT DESCRIPTION 

a client-server coupon-system, that is used to manage activities in a company, customer or an admin level:
admin: can add, delete, get or update companies, customers or coupons in the coupon-system.
company: can login, add, delete or update coupons, get all its coupons and get all its Details.
customer: can login, Purchase coupons, get all customer coupons and get all customer details.

TABLES DDL

COUPONS

CREATE TABLE `coupons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_id` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `description` varchar(800) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `company_id_fk_idx` (`company_id`),
  KEY `category_id_fk_idx` (`category_id`),
  CONSTRAINT `category_id_fk` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `company_id_fk` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CUSTOMERS_COUPONS

CREATE TABLE `customers_coupons` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `coupon_id` int NOT NULL,
  KEY `coupon_id_fk_idx` (`coupon_id`),
  KEY `customer_id_fk_idx` (`customer_id`),
  CONSTRAINT `coupon_id_fk` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `customer_id_fk` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 CATEGORIES
 
CREATE TABLE `categories` (_
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

COMPANIES

CREATE TABLE `companies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 CUSTOMERS
 
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
