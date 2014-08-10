delimiter $$

CREATE DATABASE `intel` /*!40100 DEFAULT CHARACTER SET utf8 */$$

delimiter $$

CREATE TABLE `intel`.`session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `charId` int(11) NOT NULL,
  `charName` text NOT NULL,
  `sessionId` text NOT NULL,
  `createdAt` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `intel`.`settings` (
  `charId` int(11) NOT NULL,
  `charName` text NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `intel`.`uploader` (
  `charId` int(11) NOT NULL,
  `charName` text NOT NULL,
  `sessionId` text NOT NULL,
  `createdAt` int(11) NOT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$
