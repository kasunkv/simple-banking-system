-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.60-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema bank_system
--

CREATE DATABASE IF NOT EXISTS bank_system;
USE bank_system;

--
-- Definition of table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `accNo` varchar(10) NOT NULL DEFAULT '',
  `name` varchar(80) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `intDeposite` float DEFAULT NULL,
  `dateOpened` date DEFAULT NULL,
  `dateColsed` date DEFAULT NULL,
  `accStatus` varchar(10) DEFAULT NULL,
  `authoBy` varchar(40) DEFAULT NULL,
  `accType` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`accNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accounts`
--

/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` (`accNo`,`name`,`balance`,`intDeposite`,`dateOpened`,`dateColsed`,`accStatus`,`authoBy`,`accType`) VALUES 
 ('1020001','Kasun Kodagoda',43490.8,25000,'2012-03-13',NULL,'Active','gbk_opr','sav'),
 ('1020002','Gayan Buddika',76000,30000,'2012-03-13',NULL,'Active','kvk_admin','cur'),
 ('1020003','Susil Manjula Perumbulli',0,50000,'2012-03-13','2012-03-14','Deactive','nur','cur'),
 ('1020004','Gayan Manjula',0,40000,'2012-03-13','2012-03-14','Deactive','kvk_admin','sav'),
 ('1020005','Thilini Oreliya',101500,50000,'2012-03-14',NULL,'Active','kvk_admin','sav'),
 ('1020006','Achini Jayathilake',51500,50000,'2012-03-14',NULL,'Active','kvk_admin','cur');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;


--
-- Definition of table `current`
--

DROP TABLE IF EXISTS `current`;
CREATE TABLE `current` (
  `accNo` varchar(10) NOT NULL DEFAULT '',
  `odLimit` float DEFAULT NULL,
  `odInt` float DEFAULT NULL,
  `introBy` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`accNo`),
  CONSTRAINT `F_KEY_CUR` FOREIGN KEY (`accNo`) REFERENCES `accounts` (`accNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `current`
--

/*!40000 ALTER TABLE `current` DISABLE KEYS */;
INSERT INTO `current` (`accNo`,`odLimit`,`odInt`,`introBy`) VALUES 
 ('1020002',10000,0.156,'Kasun Kodagoda'),
 ('1020003',15000,0.156,'Kasun Kodagoda'),
 ('1020006',20000,0.75,'Kasun Kodagoda');
/*!40000 ALTER TABLE `current` ENABLE KEYS */;


--
-- Definition of table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `cusNIC` varchar(10) NOT NULL DEFAULT '',
  `name` varchar(80) DEFAULT NULL,
  `address` varchar(120) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `accNo` varchar(10) DEFAULT NULL,
  `pin` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`cusNIC`),
  KEY `F_KEY_CUS` (`accNo`),
  CONSTRAINT `F_KEY_CUS` FOREIGN KEY (`accNo`) REFERENCES `accounts` (`accNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`cusNIC`,`name`,`address`,`phone`,`dob`,`accNo`,`pin`) VALUES 
 ('882214117V','Kasun Kodagoda','153/C, Ihalayagoda, Gampaha','0717673839','1988-08-08','1020001','78263'),
 ('882315450V','Gayan Buddika','190/F, Ihalayagoda, Gampaha','0712361548','1988-11-30','1020002','27253'),
 ('882651487V','Gayan Manjula','Ihalayagoda, Imbulgoda.','0778568456','1988-05-25','1020004','52401'),
 ('886312571V','Susil Manjula Perumbulli','285/A, Pahalayagoda, Gampaha.','0712551252','1988-02-25','1020003','73328'),
 ('892615714V','Achini Jayathilake','Negombo','0771256984','1989-10-31','1020006','63932'),
 ('901256142V','Thilini Oreliya','Negombo','0711256245','1990-03-16','1020005','19171');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `empNo` varchar(10) NOT NULL DEFAULT '',
  `empNIC` varchar(10) DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  `address` varchar(120) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `grade` varchar(3) DEFAULT NULL,
  `authLimit` varchar(6) DEFAULT NULL,
  `designation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`empNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`empNo`,`empNIC`,`name`,`address`,`phone`,`dob`,`grade`,`authLimit`,`designation`) VALUES 
 ('EMP001','882214117V','Kasun Kodagoda','153/C, Ihalayagoda, Gampaha','0717673839','1988-08-08','1','admin','System Administrator'),
 ('EMP002','882365124V','Gayan Buddika','190/F, Ihalayagoda, Imbulgoda.','0712651423','1988-11-04','1','oper','System Operator'),
 ('EMP003','882365124V','Nayana Udesh Ranathunga','256/D, Pahalayagoda, Gampaha.','0781236598','1988-08-18','1','admin','System Administrator'),
 ('EMP004','882645784V','Susil Manjula','75/D, Pahalayagoda, Gampaha.','0772658958','1988-10-15','2','oper','System Operator'),
 ('EMP005','902361251V','Thilini Fernando','Negombo','0712361595','1990-03-16','1','admin','System Administrator');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


--
-- Definition of table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `empNo` varchar(10) NOT NULL DEFAULT '',
  `userName` varchar(20) DEFAULT NULL,
  `passW` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`empNo`),
  CONSTRAINT `F_KEY_LOG` FOREIGN KEY (`empNo`) REFERENCES `employee` (`empNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` (`empNo`,`userName`,`passW`) VALUES 
 ('EMP001','admin','123'),
 ('EMP002','oper','456'),
 ('EMP003','nur','789'),
 ('EMP004','pas','147');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;


--
-- Definition of table `savings`
--

DROP TABLE IF EXISTS `savings`;
CREATE TABLE `savings` (
  `accNo` varchar(10) NOT NULL DEFAULT '',
  `intRate` float DEFAULT NULL,
  `minBalance` float DEFAULT NULL,
  PRIMARY KEY (`accNo`),
  CONSTRAINT `F_KEY_SAV` FOREIGN KEY (`accNo`) REFERENCES `accounts` (`accNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `savings`
--

/*!40000 ALTER TABLE `savings` DISABLE KEYS */;
INSERT INTO `savings` (`accNo`,`intRate`,`minBalance`) VALUES 
 ('1020001',0.04,1000),
 ('1020004',0.04,20000),
 ('1020005',0.5,500);
/*!40000 ALTER TABLE `savings` ENABLE KEYS */;


--
-- Definition of table `trans`
--

DROP TABLE IF EXISTS `trans`;
CREATE TABLE `trans` (
  `TID` varchar(10) NOT NULL DEFAULT '',
  `timeStp` datetime DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `transType` varchar(10) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `accNo` varchar(10) DEFAULT NULL,
  `empNo` varchar(10) DEFAULT NULL,
  `destiAccNo` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TID`),
  KEY `F_KEY_TRANS1` (`accNo`),
  KEY `F_KEY_TRANS2` (`empNo`),
  CONSTRAINT `F_KEY_TRANS1` FOREIGN KEY (`accNo`) REFERENCES `accounts` (`accNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `F_KEY_TRANS2` FOREIGN KEY (`empNo`) REFERENCES `employee` (`empNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trans`
--

/*!40000 ALTER TABLE `trans` DISABLE KEYS */;
/*!40000 ALTER TABLE `trans` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
