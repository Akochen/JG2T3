-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_library` DEFAULT CHARACTER SET utf8mb4;
USE `db_library` ;

-- -----------------------------------------------------
-- Table `db_library`.`rentable`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_library`.`rentable` ;

CREATE TABLE IF NOT EXISTS `db_library`.`rentable` (
  `sku` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `isbn` VARCHAR(15) NULL DEFAULT NULL,
  `condition` VARCHAR(15) NULL DEFAULT NULL,
  `genre` VARCHAR(20) NULL DEFAULT NULL,
  `type` VARCHAR(10) NULL DEFAULT NULL,
  `room_number` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`sku`),
  UNIQUE INDEX `isbn_UNIQUE` (`isbn` ASC),
  UNIQUE INDEX `room_number_UNIQUE` (`room_number` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `db_library`.`rental`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_library`.`rental` ;

CREATE TABLE IF NOT EXISTS `db_library`.`rental` (
  `sku` INT(11) NOT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `user_id` VARCHAR(10) NOT NULL,
  `times_renewed` INT(11) NOT NULL,
  PRIMARY KEY (`sku`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `db_library`.`user_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_library`.`user_account` ;

CREATE TABLE IF NOT EXISTS `db_library`.`user_account` (
  `userid` INT(11) NOT NULL AUTO_INCREMENT,
  `balance` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
