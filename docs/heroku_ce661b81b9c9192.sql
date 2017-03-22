-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Host: us-cdbr-iron-east-04.cleardb.net
-- Generation Time: Mar 21, 2017 at 09:48 PM
-- Server version: 5.5.46-log
-- PHP Version: 5.3.28

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `heroku_ce661b81b9c9192`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE IF NOT EXISTS `item` (
  `ItemID` int(11) NOT NULL AUTO_INCREMENT,
  `SectionID` int(11) NOT NULL,
  `Price` float NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` text NOT NULL,
  `Stock` int(11) NOT NULL,
  `Allergens` text NOT NULL,
  `PreparationTime` int(2) NOT NULL COMMENT 'Preparation time in minutes.',
  PRIMARY KEY (`ItemID`),
  UNIQUE KEY `ItemID` (`ItemID`),
  KEY `ItemID_2` (`ItemID`),
  KEY `SectionID` (`SectionID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=112 ;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`ItemID`, `SectionID`, `Price`, `Name`, `Description`, `Stock`, `Allergens`, `PreparationTime`) VALUES
(2, 2, 5, 'Margherita', '', 30, '', 20),
(12, 2, 10, 'Margherita with pineapple', '', 30, '', 20),
(22, 2, 11, 'Margherita on cauliflower base', '', 30, '', 20),
(32, 12, 20, 'Vesuvio', '', 30, '', 20),
(42, 12, 20, 'Capricciosa', '', 30, '', 0),
(52, 22, 2, 'Coke', '', 100, '', 0),
(62, 22, 1, 'Water', '', 120, '', 0),
(72, 32, 7, 'White wine', '', 80, '', 0),
(82, 32, 8, 'Red wine', '', 80, '', 0),
(102, 42, 123, 'Something', '123', 31, '', 123);

-- --------------------------------------------------------

--
-- Table structure for table `itemquantity`
--

CREATE TABLE IF NOT EXISTS `itemquantity` (
  `ListID` int(11) NOT NULL AUTO_INCREMENT,
  `ItemID` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `TransactionID` int(11) NOT NULL,
  PRIMARY KEY (`ListID`),
  UNIQUE KEY `ListID` (`ListID`),
  KEY `ItemID` (`ItemID`),
  KEY `TransactionID` (`TransactionID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1632 ;

--
-- Dumping data for table `itemquantity`
--

INSERT INTO `itemquantity` (`ListID`, `ItemID`, `Quantity`, `TransactionID`) VALUES
(602, 32, 2, 372),
(612, 2, 1, 372),
(622, 72, 2, 382),
(632, 2, 1, 382),
(642, 82, 12, 392),
(652, 12, 2, 402),
(662, 2, 1, 412),
(672, 72, 3, 412),
(682, 32, 2, 412),
(692, 12, 2, 422),
(702, 2, 1, 432),
(712, 62, 2, 442),
(722, 12, 2, 442),
(732, 2, 1, 452),
(742, 12, 2, 462),
(752, 22, 2, 472),
(762, 42, 3, 482),
(772, 12, 2, 482),
(782, 12, 2, 492),
(792, 12, 2, 502),
(802, 2, 1, 512),
(812, 72, 2, 512),
(822, 72, 1, 522),
(832, 82, 2, 522),
(842, 2, 2, 532),
(852, 72, 3, 532),
(862, 2, 3, 542),
(872, 72, 1, 552),
(882, 42, 1, 562),
(892, 22, 2, 562),
(902, 12, 2, 572),
(912, 2, 1, 572),
(922, 12, 2, 582),
(932, 62, 1, 592),
(942, 52, 1, 592),
(952, 2, 2, 602),
(962, 2, 2, 612),
(972, 22, 2, 622),
(982, 2, 2, 632),
(992, 2, 3, 642),
(1002, 72, 3, 652),
(1012, 2, 2, 652),
(1022, 2, 2, 662),
(1032, 82, 3, 662),
(1042, 32, 2, 672),
(1052, 12, 1, 682),
(1062, 2, 12, 682),
(1072, 12, 3, 692),
(1082, 32, 2, 692),
(1092, 2, 1, 702),
(1102, 2, 1, 712),
(1112, 2, 3, 722),
(1122, 2, 3, 732),
(1132, 2, 1, 742),
(1142, 52, 2, 742),
(1152, 2, 2, 752),
(1162, 52, 1, 762),
(1172, 62, 2, 762),
(1182, 2, 1, 772),
(1192, 2, 1, 782),
(1202, 2, 1, 792),
(1212, 2, 1, 802),
(1222, 2, 1, 812),
(1232, 2, 1, 822),
(1242, 52, 2, 832),
(1252, 2, 1, 832),
(1262, 2, 2, 842),
(1272, 22, 2, 852),
(1282, 2, 2, 862),
(1292, 82, 3, 872),
(1302, 42, 2, 882),
(1312, 2, 3, 892),
(1322, 2, 3, 902),
(1332, 2, 3, 912),
(1342, 2, 1, 922),
(1352, 12, 2, 922),
(1362, 2, 2, 932),
(1372, 2, 3, 942),
(1382, 12, 3, 952),
(1392, 2, 3, 962),
(1402, 42, 2, 972),
(1412, 2, 2, 982),
(1422, 2, 1, 992),
(1432, 72, 1, 1002),
(1442, 72, 1000, 1012),
(1452, 72, 349, 1022),
(1462, 2, 2, 1032),
(1472, 52, 1, 1032),
(1482, 12, 2, 1042),
(1492, 2, 1, 1042),
(1502, 2, 1, 1052),
(1512, 12, 2, 1052),
(1522, 12, 2, 1062),
(1532, 2, 1, 1062),
(1542, 2, 1, 1072),
(1552, 12, 2, 1072),
(1562, 52, 2, 1082),
(1572, 2, 1, 1082),
(1582, 62, 1, 1092),
(1592, 2, 2, 1092),
(1602, 2, 1, 1102),
(1612, 2, 1, 1112),
(1622, 2, 2, 1122);

-- --------------------------------------------------------

--
-- Table structure for table `section`
--

CREATE TABLE IF NOT EXISTS `section` (
  `SectionID` int(11) NOT NULL AUTO_INCREMENT,
  `VenueID` int(11) NOT NULL,
  `MenuID` int(11) NOT NULL,
  `Description` text NOT NULL,
  PRIMARY KEY (`SectionID`),
  UNIQUE KEY `SectionID` (`SectionID`),
  KEY `MenuID` (`MenuID`),
  KEY `VenueID` (`VenueID`),
  KEY `MenuID_2` (`MenuID`),
  KEY `VenueID_2` (`VenueID`),
  KEY `VenueID_3` (`VenueID`),
  KEY `VenueID_4` (`VenueID`),
  KEY `SectionID_2` (`SectionID`),
  KEY `MenuID_3` (`MenuID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=112 ;

--
-- Dumping data for table `section`
--

INSERT INTO `section` (`SectionID`, `VenueID`, `MenuID`, `Description`) VALUES
(2, 22, 72, 'Vegetarian pizza'),
(12, 22, 72, 'Traditional pizza'),
(22, 22, 72, 'Soft drinks'),
(32, 22, 72, 'Alcoholic drinks'),
(42, 1, 92, 'Hot drinks'),
(52, 1, 92, 'Fizzy drinks'),
(62, 1, 92, 'Breakfast'),
(72, 1, 92, 'Dinner'),
(82, 12, 82, 'Vegetarian-rolls'),
(92, 12, 82, 'Sausageroll'),
(102, 12, 82, 'Bottled drinks');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `TransactionID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `VenueID` int(11) NOT NULL,
  `TotalPrice` float(6,2) NOT NULL,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 - confirmed, 1 - processed, 2 - ready, 3 - collected',
  `CollectionTime` time NOT NULL DEFAULT '00:00:00',
  `Keywords` varchar(300) NOT NULL DEFAULT '',
  PRIMARY KEY (`TransactionID`),
  UNIQUE KEY `TransactionID` (`TransactionID`),
  KEY `UserID` (`UserID`),
  KEY `VenueID` (`VenueID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1132 ;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionID`, `UserID`, `VenueID`, `TotalPrice`, `Time`, `Status`, `CollectionTime`, `Keywords`) VALUES
(322, 1, 22, 5.00, '2017-02-22 19:30:14', 0, '10:25:00', 'ample name'),
(332, 1, 22, 5.00, '2017-02-22 19:31:54', 0, '10:25:00', 'near-law'),
(342, 1, 22, 7.00, '2017-02-22 20:22:03', 0, '02:35:00', 'assured government'),
(352, 1, 22, 31.00, '2017-02-22 20:22:57', 0, '10:15:00', 'teeming state'),
(362, 1, 22, 7.00, '2017-02-22 20:24:10', 0, '10:20:00', 'idealistic number'),
(372, 1, 22, 25.00, '2017-02-22 20:26:21', 0, '02:40:00', 'stupendous girl'),
(382, 1, 22, 12.00, '2017-02-22 20:28:11', 0, '04:50:00', 'chilly air'),
(392, 1, 22, 8.00, '2017-02-22 20:30:01', 0, '11:15:00', 'ample hour'),
(402, 1, 22, 10.00, '2017-02-22 20:30:28', 0, '00:00:00', ''),
(412, 1, 22, 32.00, '2017-02-22 21:09:15', 0, '00:00:00', ''),
(422, 1, 22, 10.00, '2017-02-22 21:11:29', 0, '00:00:00', ''),
(432, 1, 22, 5.00, '2017-02-22 21:11:54', 0, '00:00:00', ''),
(442, 1, 22, 11.00, '2017-02-22 21:13:37', 0, '00:00:00', ''),
(452, 1, 22, 5.00, '2017-02-22 21:17:43', 0, '02:10:00', 'grimy information'),
(462, 1, 22, 10.00, '2017-02-22 21:18:46', 0, '00:00:00', ''),
(472, 1, 22, 11.00, '2017-02-22 21:19:08', 0, '00:00:00', ''),
(482, 1, 22, 30.00, '2017-02-22 21:27:20', 0, '00:00:00', ''),
(492, 1, 22, 10.00, '2017-02-22 21:29:25', 0, '12:20:00', 'calm minute'),
(502, 1, 22, 10.00, '2017-02-22 21:32:30', 0, '01:25:00', 'fearful friend'),
(512, 1, 22, 12.00, '2017-02-23 10:32:28', 0, '12:25:00', 'defensive life'),
(522, 1, 22, 15.00, '2017-02-23 12:15:18', 0, '01:25:00', 'devoted program'),
(532, 1, 22, 12.00, '2017-02-23 13:04:21', 0, '00:00:00', ''),
(542, 1, 22, 5.00, '2017-02-23 13:05:12', 0, '00:00:00', 'distant story'),
(552, 1, 22, 7.00, '2017-02-25 10:51:52', 0, '00:00:00', ''),
(562, 1, 22, 31.00, '2017-02-26 10:54:21', 0, '06:35:00', 'obese education'),
(572, 1, 22, 15.00, '2017-02-26 10:56:16', 0, '02:30:00', 'mammoth program'),
(582, 1, 22, 10.00, '2017-02-26 10:59:24', 0, '01:25:00', 'those minute'),
(592, 1, 22, 3.00, '2017-02-26 11:05:10', 0, '05:35:00', 'flippant business'),
(602, 1, 22, 5.00, '2017-02-26 11:25:35', 0, '11:20:00', 'ideal government'),
(612, 1, 22, 5.00, '2017-03-02 11:16:29', 0, '13:05:00', 'unfinished group'),
(622, 1, 22, 11.00, '2017-03-01 22:25:15', 0, '23:30:00', 'informal body'),
(632, 1, 22, 5.00, '2017-03-02 10:23:59', 0, '10:35:00', 'self-assured right'),
(642, 1, 22, 5.00, '2017-03-02 11:17:04', 0, '13:40:00', 'dim work'),
(652, 1, 22, 12.00, '2017-03-08 21:21:42', 0, '22:00:00', 'required country'),
(662, 3, 22, 13.00, '2017-03-09 12:46:39', 0, '13:00:00', 'darling right'),
(672, 3, 22, 20.00, '2017-03-09 12:47:01', 0, '13:10:00', 'minor air'),
(682, 3, 22, 15.00, '2017-03-11 09:33:28', 0, '00:00:00', ''),
(692, 3, 22, 70.00, '2017-03-11 14:42:07', 0, '00:00:00', ''),
(702, 3, 22, 5.00, '2017-03-11 18:28:40', 0, '00:00:00', ''),
(712, 3, 22, 5.00, '2017-03-11 18:45:37', 0, '00:00:00', ''),
(722, 3, 22, 15.00, '2017-03-16 11:24:50', 2, '00:00:00', ''),
(732, 3, 22, 15.00, '2017-03-11 19:58:04', 0, '00:00:00', ''),
(742, 3, 22, 7.00, '2017-03-15 12:49:11', 0, '00:00:00', 'wet area'),
(752, 3, 22, 5.00, '2017-03-15 16:44:19', 0, '12:25:00', 'rare child'),
(762, 3, 22, 4.00, '2017-03-16 10:08:33', 0, '01:15:00', 'sleepy system'),
(772, 3, 22, 5.00, '2017-03-17 18:29:39', 0, '00:00:00', ''),
(782, 3, 22, 5.00, '2017-03-17 18:31:05', 0, '00:00:00', ''),
(792, 3, 22, 5.00, '2017-03-17 18:31:31', 0, '00:00:00', ''),
(802, 3, 22, 5.00, '2017-03-17 18:37:27', 0, '00:00:00', ''),
(812, 3, 22, 5.00, '2017-03-17 18:38:21', 0, '00:00:00', ''),
(822, 3, 22, 5.00, '2017-03-17 18:40:20', 0, '00:00:00', ''),
(832, 3, 22, 9.00, '2017-03-18 22:06:29', 0, '00:00:00', ''),
(842, 402, 22, 10.00, '2017-03-20 17:14:23', 0, '00:00:00', ''),
(852, 402, 22, 22.00, '2017-03-20 17:15:04', 0, '12:12:00', 'glamorous law'),
(862, 402, 22, 10.00, '2017-03-20 17:15:22', 0, '08:08:00', 'scornful point'),
(872, 402, 22, 24.00, '2017-03-20 17:15:43', 0, '19:19:00', 'square teacher'),
(882, 402, 22, 40.00, '2017-03-20 17:15:58', 0, '09:09:00', 'repulsive others'),
(892, 402, 22, 15.00, '2017-03-20 17:28:09', 0, '00:00:00', ''),
(902, 402, 22, 15.00, '2017-03-20 17:28:58', 0, '12:12:00', 'misguided place'),
(912, 402, 22, 15.00, '2017-03-20 17:30:21', 0, '00:00:00', ''),
(922, 3, 22, 25.00, '2017-03-20 17:50:55', 0, '00:00:00', ''),
(932, 402, 22, 10.00, '2017-03-20 20:13:40', 0, '00:00:00', ''),
(942, 402, 22, 15.00, '2017-03-20 20:33:04', 0, '12:12:00', 'aggressive business'),
(952, 402, 22, 30.00, '2017-03-20 20:33:25', 0, '00:00:00', ''),
(962, 402, 22, 15.00, '2017-03-20 20:41:05', 0, '10:10:00', 'functional back'),
(972, 402, 22, 40.00, '2017-03-20 20:41:45', 0, '13:13:00', 'excited company'),
(982, 402, 22, 10.00, '2017-03-20 20:55:08', 0, '11:11:00', 'quixotic lot'),
(992, 3, 22, 5.00, '2017-03-20 23:36:47', 0, '00:00:00', ''),
(1002, 3, 22, 7.00, '2017-03-20 23:42:27', 0, '00:00:00', ''),
(1012, 3, 22, 7000.00, '2017-03-20 23:44:59', 0, '00:00:00', ''),
(1022, 3, 22, 2443.00, '2017-03-20 23:50:50', 0, '00:00:00', ''),
(1032, 3, 22, 12.00, '2017-03-21 08:52:41', 0, '00:00:00', ''),
(1042, 3, 22, 25.00, '2017-03-21 09:25:46', 0, '00:00:00', ''),
(1052, 3, 22, 25.00, '2017-03-21 09:41:38', 0, '00:00:00', ''),
(1062, 3, 22, 25.00, '2017-03-21 09:44:13', 0, '00:00:00', ''),
(1072, 3, 22, 25.00, '2017-03-21 09:54:19', 0, '00:00:00', ''),
(1082, 3, 22, 9.00, '2017-03-21 10:29:20', 0, '00:00:00', ''),
(1092, 3, 22, 11.00, '2017-03-21 10:50:49', 0, '00:00:00', ''),
(1102, 3, 22, 5.00, '2017-03-21 12:56:50', 0, '00:00:00', ''),
(1112, 3, 22, 5.00, '2017-03-21 12:59:25', 0, '00:00:00', ''),
(1122, 3, 22, 10.00, '2017-03-21 13:01:21', 0, '00:00:00', '');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Fname` varchar(35) NOT NULL,
  `Lname` varchar(35) NOT NULL,
  `email` varchar(40) NOT NULL,
  `Username` varchar(40) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=Customer,1=Admin,2=Venue',
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `UserID` (`UserID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=442 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Fname`, `Lname`, `email`, `Username`, `Password`, `Type`) VALUES
(1, 'Matt', 'Brighty', 'matthew.brighty@gmail.com', 'matt', 'test', 1),
(3, 'hash', 'test1', 'hello@test.com', 'hash', '$2a$12$VeO2cO3nDinF50O52YQU5.r6szzV6AIZyYw3ThhvNvcPI/.rtk3e.', 1),
(32, 'pls', 'work', 'admin@test.com', 'admintest', 'test', 1),
(42, 'TestFname', 'TestLname', 'TestEmail', 'TestUsername', 'TestPass', 0),
(382, 'test', '1111', 'test1111@gmail.com', 'test1111', '$2a$12$jEmKLFMmgC8jCDQrdmBu1.S6yCojkvdldFyD57e9/mxX3Zj4yhbXG', 0),
(392, 'aa', 'aa', 'app@po.com', 'lol', '$2a$12$/0qEarHzaSs5I6z0tVEc/euXj2YNEGDYUqqPgNiRDAZ7UiwXQIBoq', 0),
(402, 'a', 'a', 'a@a.com', 'pozdro', '$2a$12$eZFsq3sSKJ4YHM3tUbvv0uzcGbmEmek02H/rMWtW/.BLbI5fjasIC', 0),
(412, 'not', 'admin', 'notadmin@gmail.com', 'notAdmin', '$2a$12$bR1Iz8KzMEqGHp7JZbCY5ulcObEibD4uiRujmceFj3Ywk6Im2v/TG', 0),
(422, 'Pizzi', 'Owner', 'pizzi@test.com', 'PizziOwner', '$2a$12$I7JMqmOu65B.4LuW/aX9WuvWXwgu/xikNMc.OpGsw9H9yPUzrBWGy', 2);

-- --------------------------------------------------------

--
-- Table structure for table `venue`
--

CREATE TABLE IF NOT EXISTS `venue` (
  `VenueID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Address` text NOT NULL,
  `PhoneNumber` varchar(12) DEFAULT NULL,
  `AcceptingOrders` tinyint(1) NOT NULL DEFAULT '1',
  `APIpass` varchar(255) NOT NULL,
  `UserID` int(11) NOT NULL,
  PRIMARY KEY (`VenueID`),
  UNIQUE KEY `VenueID` (`VenueID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=82 ;

--
-- Dumping data for table `venue`
--

INSERT INTO `venue` (`VenueID`, `Name`, `Address`, `PhoneNumber`, `AcceptingOrders`, `APIpass`, `UserID`) VALUES
(1, 'Cafe ', 'foo', '023910925293', 1, '', 0),
(12, 'Sausageroll bar', '45 Union Street\r\nAberdeen', '07243-331-85', 1, 'sausage', 0),
(22, 'Pizzi', 'Block A\r\nUnion Square\r\nAberdeen', '07248-601-55', 1, 'pizzi', 0),
(62, 'PLSWORK', 'PLEASE STREET', '101', 1, 'PLSWORKpass', 0),
(72, 'test2', 'test2 street', '1234567', 1, 'test2pass', 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `Item_ibfk_1` FOREIGN KEY (`SectionID`) REFERENCES `section` (`SectionID`);

--
-- Constraints for table `itemquantity`
--
ALTER TABLE `itemquantity`
  ADD CONSTRAINT `ItemQuantity_ibfk_2` FOREIGN KEY (`TransactionID`) REFERENCES `transaction` (`TransactionID`),
  ADD CONSTRAINT `ItemQuantity_ibfk_1` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`);

--
-- Constraints for table `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `section_ibfk_1` FOREIGN KEY (`VenueID`) REFERENCES `venue` (`VenueID`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `Transaction_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `Transaction_ibfk_2` FOREIGN KEY (`VenueID`) REFERENCES `venue` (`VenueID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
