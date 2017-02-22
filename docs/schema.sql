-- phpMyAdmin SQL Dump
-- version 3.5.5
-- http://www.phpmyadmin.net
--
-- Host: us-cdbr-iron-east-04.cleardb.net
-- Generation Time: Feb 22, 2017 at 02:16 PM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=92 ;

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
(82, 32, 8, 'Red wine', '', 80, '', 0);

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE IF NOT EXISTS `menu` (
  `MenuID` int(11) NOT NULL AUTO_INCREMENT,
  `VenueID` int(11) NOT NULL,
  `Description` text NOT NULL,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`MenuID`),
  UNIQUE KEY `MenuID` (`MenuID`),
  KEY `VenueID` (`VenueID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=102 ;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`MenuID`, `VenueID`, `Description`, `Name`) VALUES
(72, 22, '', 'Winter menu'),
(82, 12, '', 'Menu'),
(92, 1, '', 'Menu');

-- --------------------------------------------------------

--
-- Table structure for table `section`
--

CREATE TABLE IF NOT EXISTS `section` (
  `SectionID` int(11) NOT NULL AUTO_INCREMENT,
  `MenuID` int(11) NOT NULL,
  `Description` text NOT NULL,
  PRIMARY KEY (`SectionID`),
  UNIQUE KEY `SectionID` (`SectionID`),
  KEY `MenuID` (`MenuID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=112 ;

--
-- Dumping data for table `section`
--

INSERT INTO `section` (`SectionID`, `MenuID`, `Description`) VALUES
(2, 72, 'Vegetarian pizza'),
(12, 72, 'Traditional pizza'),
(22, 72, 'Alcohol-free drinks'),
(32, 72, 'Alcoholic drinks'),
(42, 92, 'Hot drinks'),
(52, 92, 'Fizzy drinks'),
(62, 92, 'Breakfast'),
(72, 92, 'Dinner'),
(82, 82, 'Vegetarian-rolls'),
(92, 82, 'Sausageroll'),
(102, 82, 'Bottled drinks');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `TransactionID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `VenueID` int(11) NOT NULL,
  `TotalPrice` float NOT NULL,
  `Time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 - confirmed, 1 - processed, 2 - ready, 3 - collected',
  `CollectionTime` date NOT NULL DEFAULT '0000-00-00',
  PRIMARY KEY (`TransactionID`),
  UNIQUE KEY `TransactionID` (`TransactionID`),
  KEY `UserID` (`UserID`),
  KEY `VenueID` (`VenueID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=42 ;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionID`, `UserID`, `VenueID`, `TotalPrice`, `Time`, `Status`, `CollectionTime`) VALUES
(1, 1, 1, 32, '2017-02-10 21:39:35', 0, '0000-00-00'),
(2, 2, 22, 22.3, '2017-02-22 00:08:48', 0, '0000-00-00'),
(12, 1, 22, 22.3, '2017-02-22 00:51:06', 0, '0000-00-00'),
(32, 1, 22, 22.3, '2017-02-22 00:55:02', 0, '0000-00-00');

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
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Username` (`Username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `UserID` (`UserID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `Fname`, `Lname`, `email`, `Username`, `Password`) VALUES
(1, 'Matthew', 'Brighty', 'matthew.brighty@gmail.com', 'matt', 'test'),
(2, 'test', 'test', 'test', 'test', 'test'),
(3, 'Grant', 'Christie', 'grant@test.com', 'grantusername', 'grantpass');

-- --------------------------------------------------------

--
-- Table structure for table `venue`
--

CREATE TABLE IF NOT EXISTS `venue` (
  `VenueID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Address` text NOT NULL,
  `PhoneNumber` varchar(12) DEFAULT NULL,
  `AcceptingOrders` tinyint(1) NOT NULL,
  `APIpass` varchar(255) NOT NULL,
  PRIMARY KEY (`VenueID`),
  UNIQUE KEY `VenueID` (`VenueID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

--
-- Dumping data for table `venue`
--

INSERT INTO `venue` (`VenueID`, `Name`, `Address`, `PhoneNumber`, `AcceptingOrders`, `APIpass`) VALUES
(1, 'Cafe ', 'foo', '023910925293', 1, ''),
(12, 'Sausageroll bar', '45 Union Street\r\nAberdeen', '07243-331-85', 1, 'sausage'),
(22, 'Zizzi', 'Block A\r\nUnion Square\r\nAberdeen', '07248-601-55', 1, 'zizzi');

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
-- Constraints for table `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `Menu_ibfk_1` FOREIGN KEY (`VenueID`) REFERENCES `venue` (`VenueID`);

--
-- Constraints for table `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `Section_ibfk_1` FOREIGN KEY (`MenuID`) REFERENCES `menu` (`MenuID`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `Transaction_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `Transaction_ibfk_2` FOREIGN KEY (`VenueID`) REFERENCES `venue` (`VenueID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
