-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: evaluation
-- ------------------------------------------------------
-- Server version	5.5.28-0ubuntu0.12.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign` (
  `cid` bigint(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT 'A name for the campain',
  `cendpoint` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT 'the endpoint URL',
  `cgraphs` varchar(512) COLLATE utf8_unicode_ci NOT NULL COMMENT 'for multiple graphs seperate them by '';''',
  `copened` tinyint(1) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,'DBpedia Evaluation Campaign','http://dbpedia.aksw.org:8877/sparql','http://live.dbpedia.org',1);
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `classes` (
  `cid` bigint(20) NOT NULL,
  `curi` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `cname` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `cparent` bigint(20) NOT NULL,
  `count_cache` bigint(20) NOT NULL,
  `is_leaf` tinyint(1) NOT NULL,
  PRIMARY KEY (`cid`),
  KEY `cname` (`cname`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES (0,'http://www.w3.org/2002/07/owl#Thing','owl:Thing',-1,2350906,0),(1,'http://dbpedia.org/ontology/BasketballLeague','BasketballLeague',220,0,1),(2,'http://dbpedia.org/ontology/LunarCrater','LunarCrater',81,0,1),(3,'http://dbpedia.org/ontology/MilitaryPerson','MilitaryPerson',226,0,1),(4,'http://dbpedia.org/ontology/TimePeriod','TimePeriod',0,0,0),(5,'http://dbpedia.org/ontology/AutomobileEngine','AutomobileEngine',106,0,1),(6,'http://dbpedia.org/ontology/Enzyme','Enzyme',315,0,1),(7,'http://dbpedia.org/ontology/University','University',275,0,1),(8,'http://dbpedia.org/ontology/AnatomicalStructure','AnatomicalStructure',0,4211,0),(9,'http://dbpedia.org/ontology/TelevisionShow','TelevisionShow',198,0,1),(10,'http://dbpedia.org/ontology/LaunchPad','LaunchPad',229,0,1),(11,'http://dbpedia.org/ontology/CyclingLeague','CyclingLeague',220,0,1),(12,'http://dbpedia.org/ontology/CurlingLeague','CurlingLeague',220,0,1),(13,'http://dbpedia.org/ontology/MusicFestival','MusicFestival',64,0,1),(14,'http://dbpedia.org/ontology/Tax','Tax',0,0,1),(15,'http://dbpedia.org/ontology/IceHockeyPlayer','IceHockeyPlayer',157,0,1),(16,'http://dbpedia.org/ontology/PublicTransitSystem','PublicTransitSystem',323,0,1),(17,'http://dbpedia.org/ontology/FootballMatch','FootballMatch',282,0,1),(18,'http://dbpedia.org/ontology/MouseGeneLocation','MouseGeneLocation',53,0,1),(19,'http://dbpedia.org/ontology/MilitaryConflict','MilitaryConflict',64,0,1),(20,'http://dbpedia.org/ontology/FilmFestival','FilmFestival',64,0,1),(21,'http://dbpedia.org/ontology/Beverage','Beverage',189,0,0),(22,'http://dbpedia.org/ontology/SpaceShuttle','SpaceShuttle',131,0,1),(23,'http://dbpedia.org/ontology/Archaea','Archaea',333,0,1),(24,'http://dbpedia.org/ontology/HandballPlayer','HandballPlayer',157,0,1),(25,'http://dbpedia.org/ontology/Arachnid','Arachnid',300,0,1),(26,'http://dbpedia.org/ontology/Park','Park',62,0,1),(27,'http://dbpedia.org/ontology/Cricketer','Cricketer',157,0,1),(28,'http://dbpedia.org/ontology/FloweringPlant','FloweringPlant',152,0,0),(29,'http://dbpedia.org/ontology/TelevisionEpisode','TelevisionEpisode',198,0,1),(30,'http://dbpedia.org/ontology/Gnetophytes','Gnetophytes',152,0,1),(31,'http://dbpedia.org/ontology/Protein','Protein',315,0,1),(32,'http://dbpedia.org/ontology/HumanGeneLocation','HumanGeneLocation',53,0,1),(33,'http://dbpedia.org/ontology/SpeedwayTeam','SpeedwayTeam',287,0,1),(34,'http://dbpedia.org/ontology/ChristianPatriarch','ChristianPatriarch',114,0,1),(35,'http://dbpedia.org/ontology/GovernmentType','GovernmentType',0,0,1),(36,'http://dbpedia.org/ontology/Town','Town',57,0,1),(37,'http://dbpedia.org/ontology/ReligiousBuilding','ReligiousBuilding',358,0,0),(38,'http://dbpedia.org/ontology/PowerStation','PowerStation',229,0,1),(39,'http://dbpedia.org/ontology/Name','Name',0,0,0),(40,'http://dbpedia.org/ontology/FormulaOneRacer','FormulaOneRacer',157,0,1),(41,'http://dbpedia.org/ontology/Conifer','Conifer',152,0,1),(42,'http://dbpedia.org/ontology/SpeedwayLeague','SpeedwayLeague',220,0,1),(43,'http://dbpedia.org/ontology/VideogamesLeague','VideogamesLeague',220,0,1),(44,'http://dbpedia.org/ontology/Company','Company',318,0,0),(45,'http://dbpedia.org/ontology/Locomotive','Locomotive',131,0,1),(46,'http://dbpedia.org/ontology/Wrestler','Wrestler',157,0,1),(47,'http://dbpedia.org/ontology/MotorcycleRacingLeague','MotorcycleRacingLeague',220,0,1),(48,'http://dbpedia.org/ontology/Sales','Sales',0,0,1),(49,'http://dbpedia.org/ontology/AdultActor','AdultActor',214,0,1),(50,'http://dbpedia.org/ontology/GridironFootballPlayer','GridironFootballPlayer',157,0,0),(51,'http://dbpedia.org/ontology/SoccerLeague','SoccerLeague',220,0,0),(52,'http://dbpedia.org/ontology/TeamMember','TeamMember',157,0,1),(53,'http://dbpedia.org/ontology/GeneLocation','GeneLocation',0,0,0),(54,'http://dbpedia.org/ontology/RoadJunction','RoadJunction',323,0,1),(55,'http://dbpedia.org/ontology/Brain','Brain',8,0,1),(56,'http://dbpedia.org/ontology/WomensTennisAssociationTournament','WomensTennisAssociationTournament',282,0,1),(57,'http://dbpedia.org/ontology/Settlement','Settlement',336,0,0),(58,'http://dbpedia.org/ontology/Software','Software',198,0,0),(59,'http://dbpedia.org/ontology/Opera','Opera',129,0,1),(60,'http://dbpedia.org/ontology/ControlledDesignationOfOriginWine','ControlledDesignationOfOriginWine',143,0,1),(61,'http://dbpedia.org/ontology/President','President',100,0,1),(62,'http://dbpedia.org/ontology/ArchitecturalStructure','ArchitecturalStructure',320,0,0),(63,'http://dbpedia.org/ontology/TennisPlayer','TennisPlayer',157,0,1),(64,'http://dbpedia.org/ontology/Event','Event',0,0,0),(65,'http://dbpedia.org/ontology/Band','Band',318,0,1),(66,'http://dbpedia.org/ontology/Country','Country',336,0,1),(67,'http://dbpedia.org/ontology/BullFighter','BullFighter',157,0,1),(68,'http://dbpedia.org/ontology/Fish','Fish',300,0,1),(69,'http://dbpedia.org/ontology/Magazine','Magazine',150,0,1),(70,'http://dbpedia.org/ontology/Galaxy','Galaxy',245,0,1),(71,'http://dbpedia.org/ontology/Manhwa','Manhwa',205,0,1),(72,'http://dbpedia.org/ontology/OrganisationMember','OrganisationMember',226,0,0),(73,'http://dbpedia.org/ontology/TelevisionSeason','TelevisionSeason',198,0,1),(74,'http://dbpedia.org/ontology/LawFirm','LawFirm',44,0,1),(75,'http://dbpedia.org/ontology/WaterwayTunnel','WaterwayTunnel',90,0,1),(76,'http://dbpedia.org/ontology/Airport','Airport',229,0,1),(77,'http://dbpedia.org/ontology/Boxer','Boxer',157,0,1),(78,'http://dbpedia.org/ontology/Fern','Fern',152,0,1),(79,'http://dbpedia.org/ontology/SoccerPlayer','SoccerPlayer',157,0,1),(80,'http://dbpedia.org/ontology/Island','Island',336,0,1),(81,'http://dbpedia.org/ontology/NaturalPlace','NaturalPlace',320,0,0),(82,'http://dbpedia.org/ontology/CollegeCoach','CollegeCoach',226,0,1),(83,'http://dbpedia.org/ontology/HumanGene','HumanGene',133,0,1),(84,'http://dbpedia.org/ontology/Muscle','Muscle',8,0,1),(85,'http://dbpedia.org/ontology/Stream','Stream',263,0,0),(86,'http://dbpedia.org/ontology/Hospital','Hospital',358,0,1),(87,'http://dbpedia.org/ontology/Philosopher','Philosopher',226,0,1),(88,'http://dbpedia.org/ontology/BiologicalDatabase','BiologicalDatabase',123,0,1),(89,'http://dbpedia.org/ontology/Church','Church',37,0,1),(90,'http://dbpedia.org/ontology/Tunnel','Tunnel',323,0,0),(91,'http://dbpedia.org/ontology/Ginkgo','Ginkgo',152,0,1),(92,'http://dbpedia.org/ontology/Valley','Valley',81,0,1),(93,'http://dbpedia.org/ontology/Writer','Writer',266,0,1),(94,'http://dbpedia.org/ontology/Automobile','Automobile',131,0,1),(95,'http://dbpedia.org/ontology/Ideology','Ideology',0,0,1),(96,'http://dbpedia.org/ontology/SupremeCourtOfTheUnitedStatesCase','SupremeCourtOfTheUnitedStatesCase',377,0,1),(97,'http://dbpedia.org/ontology/CanadianFootballTeam','CanadianFootballTeam',287,0,1),(98,'http://dbpedia.org/ontology/RadioStation','RadioStation',379,0,1),(99,'http://dbpedia.org/ontology/SoccerManager','SoccerManager',226,0,1),(100,'http://dbpedia.org/ontology/Politician','Politician',226,0,0),(101,'http://dbpedia.org/ontology/Comedian','Comedian',266,0,1),(102,'http://dbpedia.org/ontology/ComicsCreator','ComicsCreator',266,0,1),(103,'http://dbpedia.org/ontology/Monarch','Monarch',226,0,1),(104,'http://dbpedia.org/ontology/Road','Road',323,0,1),(105,'http://dbpedia.org/ontology/PlayboyPlaymate','PlayboyPlaymate',226,0,1),(106,'http://dbpedia.org/ontology/Device','Device',0,0,0),(107,'http://dbpedia.org/ontology/Volcano','Volcano',81,0,1),(108,'http://dbpedia.org/ontology/Newspaper','Newspaper',150,0,1),(109,'http://dbpedia.org/ontology/AmericanFootballPlayer','AmericanFootballPlayer',50,0,1),(110,'http://dbpedia.org/ontology/AcademicJournal','AcademicJournal',150,0,1),(111,'http://dbpedia.org/ontology/Artwork','Artwork',198,0,0),(112,'http://dbpedia.org/ontology/VolleyballPlayer','VolleyballPlayer',157,0,1),(113,'http://dbpedia.org/ontology/Non-ProfitOrganisation','Non-ProfitOrganisation',318,0,1),(114,'http://dbpedia.org/ontology/Cleric','Cleric',226,0,0),(115,'http://dbpedia.org/ontology/School','School',275,0,1),(116,'http://dbpedia.org/ontology/LightNovel','LightNovel',271,0,1),(117,'http://dbpedia.org/ontology/SiteOfSpecialScientificInterest','SiteOfSpecialScientificInterest',320,0,1),(118,'http://dbpedia.org/ontology/SnookerPlayer','SnookerPlayer',157,0,0),(119,'http://dbpedia.org/ontology/IceHockeyLeague','IceHockeyLeague',220,0,1),(120,'http://dbpedia.org/ontology/PersonFunction','PersonFunction',0,0,1),(121,'http://dbpedia.org/ontology/MusicalArtist','MusicalArtist',266,0,1),(122,'http://dbpedia.org/ontology/PoliticalParty','PoliticalParty',318,0,1),(123,'http://dbpedia.org/ontology/Database','Database',0,0,0),(124,'http://dbpedia.org/ontology/EthnicGroup','EthnicGroup',0,2294,1),(125,'http://dbpedia.org/ontology/BaseballTeam','BaseballTeam',287,0,1),(126,'http://dbpedia.org/ontology/Holiday','Holiday',0,0,1),(127,'http://dbpedia.org/ontology/Insect','Insect',300,0,1),(128,'http://dbpedia.org/ontology/Mineral','Mineral',327,0,1),(129,'http://dbpedia.org/ontology/MusicalWork','MusicalWork',198,0,0),(130,'http://dbpedia.org/ontology/SoccerClubSeason','SoccerClubSeason',324,0,1),(131,'http://dbpedia.org/ontology/MeanOfTransportation','MeanOfTransportation',0,0,0),(132,'http://dbpedia.org/ontology/NationalCollegiateAthleticAssociationAthlete','NationalCollegiateAthleticAssociationAthlete',157,0,1),(133,'http://dbpedia.org/ontology/Gene','Gene',315,0,0),(134,'http://dbpedia.org/ontology/Referee','Referee',226,0,1),(135,'http://dbpedia.org/ontology/Reptile','Reptile',300,0,1),(136,'http://dbpedia.org/ontology/CanadianFootballPlayer','CanadianFootballPlayer',50,0,1),(137,'http://dbpedia.org/ontology/GovernmentAgency','GovernmentAgency',318,0,1),(138,'http://dbpedia.org/ontology/Flag','Flag',0,0,1),(139,'http://dbpedia.org/ontology/Bacteria','Bacteria',333,0,1),(140,'http://dbpedia.org/ontology/Cardinal','Cardinal',114,0,1),(141,'http://dbpedia.org/ontology/Mollusca','Mollusca',300,0,1),(142,'http://dbpedia.org/ontology/Stadium','Stadium',358,0,1),(143,'http://dbpedia.org/ontology/Wine','Wine',21,0,0),(144,'http://dbpedia.org/ontology/NationalSoccerClub','NationalSoccerClub',339,0,1),(145,'http://dbpedia.org/ontology/Museum','Museum',358,0,1),(146,'http://dbpedia.org/ontology/FigureSkater','FigureSkater',157,0,1),(147,'http://dbpedia.org/ontology/Manga','Manga',205,0,1),(148,'http://dbpedia.org/ontology/College','College',275,0,1),(149,'http://dbpedia.org/ontology/NascarDriver','NascarDriver',157,0,1),(150,'http://dbpedia.org/ontology/PeriodicalLiterature','PeriodicalLiterature',159,0,0),(151,'http://dbpedia.org/ontology/Vein','Vein',8,0,1),(152,'http://dbpedia.org/ontology/Plant','Plant',272,0,0),(153,'http://dbpedia.org/ontology/Film','Film',198,0,1),(154,'http://dbpedia.org/ontology/SkiArea','SkiArea',320,0,1),(155,'http://dbpedia.org/ontology/Swimmer','Swimmer',157,0,1),(156,'http://dbpedia.org/ontology/PrimeMinister','PrimeMinister',100,0,1),(157,'http://dbpedia.org/ontology/Athlete','Athlete',226,0,0),(158,'http://dbpedia.org/ontology/Colour','Colour',0,970,1),(159,'http://dbpedia.org/ontology/WrittenWork','WrittenWork',198,0,0),(160,'http://dbpedia.org/ontology/SnookerChamp','SnookerChamp',118,0,1),(161,'http://dbpedia.org/ontology/Ambassador','Ambassador',226,0,1),(162,'http://dbpedia.org/ontology/SnookerWorldRanking','SnookerWorldRanking',0,0,1),(163,'http://dbpedia.org/ontology/RadioProgram','RadioProgram',198,0,1),(164,'http://dbpedia.org/ontology/Royalty','Royalty',226,0,0),(165,'http://dbpedia.org/ontology/BasketballTeam','BasketballTeam',287,0,1),(166,'http://dbpedia.org/ontology/Planet','Planet',245,0,1),(167,'http://dbpedia.org/ontology/Deputy','Deputy',100,0,1),(168,'http://dbpedia.org/ontology/YearInSpaceflight','YearInSpaceflight',64,0,1),(169,'http://dbpedia.org/ontology/Village','Village',57,0,1),(170,'http://dbpedia.org/ontology/Theatre','Theatre',358,0,1),(171,'http://dbpedia.org/ontology/ProtectedArea','ProtectedArea',320,0,1),(172,'http://dbpedia.org/ontology/Canal','Canal',85,0,1),(173,'http://dbpedia.org/ontology/MusicGenre','MusicGenre',326,0,1),(174,'http://dbpedia.org/ontology/Year','Year',64,0,1),(175,'http://dbpedia.org/ontology/Priest','Priest',114,0,1),(176,'http://dbpedia.org/ontology/Congressman','Congressman',100,0,1),(177,'http://dbpedia.org/ontology/Sport','Sport',184,0,1),(178,'http://dbpedia.org/ontology/Nerve','Nerve',8,0,1),(179,'http://dbpedia.org/ontology/SambaSchool','SambaSchool',318,0,1),(180,'http://dbpedia.org/ontology/Hotel','Hotel',358,0,1),(181,'http://dbpedia.org/ontology/Library','Library',275,0,1),(182,'http://dbpedia.org/ontology/Model','Model',226,0,1),(183,'http://dbpedia.org/ontology/Ligament','Ligament',8,0,1),(184,'http://dbpedia.org/ontology/Activity','Activity',0,1349,0),(185,'http://dbpedia.org/ontology/RecordLabel','RecordLabel',44,0,1),(186,'http://dbpedia.org/ontology/VoiceActor','VoiceActor',214,0,1),(187,'http://dbpedia.org/ontology/Olympics','Olympics',282,0,1),(188,'http://dbpedia.org/ontology/CanadianFootballLeague','CanadianFootballLeague',220,0,1),(189,'http://dbpedia.org/ontology/Food','Food',0,0,0),(190,'http://dbpedia.org/ontology/Song','Song',129,0,0),(191,'http://dbpedia.org/ontology/Play','Play',159,0,1),(192,'http://dbpedia.org/ontology/Album','Album',129,0,1),(193,'http://dbpedia.org/ontology/Sculpture','Sculpture',111,0,1),(194,'http://dbpedia.org/ontology/Airline','Airline',44,0,1),(195,'http://dbpedia.org/ontology/VolleyballLeague','VolleyballLeague',220,0,1),(196,'http://dbpedia.org/ontology/GrandPrix','GrandPrix',282,0,1),(197,'http://dbpedia.org/ontology/MountainPass','MountainPass',320,0,1),(198,'http://dbpedia.org/ontology/Work','Work',0,317544,0),(199,'http://dbpedia.org/ontology/Rocket','Rocket',131,0,1),(200,'http://dbpedia.org/ontology/Bird','Bird',300,0,1),(201,'http://dbpedia.org/ontology/GolfPlayer','GolfPlayer',157,0,1),(202,'http://dbpedia.org/ontology/BowlingLeague','BowlingLeague',220,0,1),(203,'http://dbpedia.org/ontology/Saint','Saint',114,0,1),(204,'http://dbpedia.org/ontology/HistoricalPeriod','HistoricalPeriod',4,0,1),(205,'http://dbpedia.org/ontology/Comics','Comics',159,0,0),(206,'http://dbpedia.org/ontology/PokerPlayer','PokerPlayer',157,0,1),(207,'http://dbpedia.org/ontology/Anime','Anime',364,0,1),(208,'http://dbpedia.org/ontology/Book','Book',159,0,0),(209,'http://dbpedia.org/ontology/Language','Language',0,0,1),(210,'http://dbpedia.org/ontology/Restaurant','Restaurant',358,0,1),(211,'http://dbpedia.org/ontology/Game','Game',184,0,1),(212,'http://dbpedia.org/ontology/Legislature','Legislature',318,0,1),(213,'http://dbpedia.org/ontology/MovieGenre','MovieGenre',326,0,1),(214,'http://dbpedia.org/ontology/Actor','Actor',266,0,0),(215,'http://dbpedia.org/ontology/Amphibian','Amphibian',300,0,1),(216,'http://dbpedia.org/ontology/GolfLeague','GolfLeague',220,0,1),(217,'http://dbpedia.org/ontology/Skyscraper','Skyscraper',358,0,1),(218,'http://dbpedia.org/ontology/Group','Group',318,0,1),(219,'http://dbpedia.org/ontology/Drug','Drug',0,0,1),(220,'http://dbpedia.org/ontology/SportsLeague','SportsLeague',318,0,0),(221,'http://dbpedia.org/ontology/BadmintonPlayer','BadmintonPlayer',157,0,1),(222,'http://dbpedia.org/ontology/Currency','Currency',0,242,1),(223,'http://dbpedia.org/ontology/ProgrammingLanguage','ProgrammingLanguage',0,0,1),(224,'http://dbpedia.org/ontology/ChessPlayer','ChessPlayer',157,0,1),(225,'http://dbpedia.org/ontology/RugbyPlayer','RugbyPlayer',157,0,1),(226,'http://dbpedia.org/ontology/Person','Person',255,0,0),(227,'http://dbpedia.org/ontology/Architect','Architect',226,0,1),(228,'http://dbpedia.org/ontology/HandballLeague','HandballLeague',220,0,1),(229,'http://dbpedia.org/ontology/Infrastructure','Infrastructure',62,0,0),(230,'http://dbpedia.org/ontology/Atoll','Atoll',336,0,1),(231,'http://dbpedia.org/ontology/SpaceMission','SpaceMission',64,0,1),(232,'http://dbpedia.org/ontology/RailwayLine','RailwayLine',323,0,1),(233,'http://dbpedia.org/ontology/CyclingCompetition','CyclingCompetition',282,0,1),(234,'http://dbpedia.org/ontology/RailwayTunnel','RailwayTunnel',90,0,1),(235,'http://dbpedia.org/ontology/Senator','Senator',100,0,1),(236,'http://dbpedia.org/ontology/ChemicalElement','ChemicalElement',327,0,1),(237,'http://dbpedia.org/ontology/GivenName','GivenName',39,0,1),(238,'http://dbpedia.org/ontology/Artery','Artery',8,347,1),(239,'http://dbpedia.org/ontology/FieldHockeyLeague','FieldHockeyLeague',220,0,1),(240,'http://dbpedia.org/ontology/TennisTournament','TennisTournament',282,0,1),(241,'http://dbpedia.org/ontology/Project','Project',0,0,0),(242,'http://dbpedia.org/ontology/Judge','Judge',226,0,1),(243,'http://dbpedia.org/ontology/Ship','Ship',131,0,1),(244,'http://dbpedia.org/ontology/Award','Award',0,1464,0),(245,'http://dbpedia.org/ontology/CelestialBody','CelestialBody',0,0,0),(246,'http://dbpedia.org/ontology/MartialArtist','MartialArtist',157,0,1),(247,'http://dbpedia.org/ontology/Musical','Musical',198,0,1),(248,'http://dbpedia.org/ontology/InlineHockeyLeague','InlineHockeyLeague',220,0,1),(249,'http://dbpedia.org/ontology/EurovisionSongContestEntry','EurovisionSongContestEntry',190,0,1),(250,'http://dbpedia.org/ontology/Bone','Bone',8,0,1),(251,'http://dbpedia.org/ontology/City','City',57,0,1),(252,'http://dbpedia.org/ontology/Convention','Convention',64,0,1),(253,'http://dbpedia.org/ontology/ShoppingMall','ShoppingMall',358,0,1),(254,'http://dbpedia.org/ontology/Journalist','Journalist',226,0,1),(255,'http://dbpedia.org/ontology/Agent','Agent',0,485419,0),(256,'http://dbpedia.org/ontology/Painting','Painting',111,0,1),(257,'http://dbpedia.org/ontology/OlympicResult','OlympicResult',0,0,1),(258,'http://dbpedia.org/ontology/SportsTeamMember','SportsTeamMember',72,0,1),(259,'http://dbpedia.org/ontology/MilitaryUnit','MilitaryUnit',318,0,1),(260,'http://dbpedia.org/ontology/SoccerTournament','SoccerTournament',282,0,1),(261,'http://dbpedia.org/ontology/Disease','Disease',0,0,1),(262,'http://dbpedia.org/ontology/Grape','Grape',28,0,1),(263,'http://dbpedia.org/ontology/BodyOfWater','BodyOfWater',81,0,0),(264,'http://dbpedia.org/ontology/HistoricBuilding','HistoricBuilding',358,0,1),(265,'http://dbpedia.org/ontology/Monument','Monument',320,0,1),(266,'http://dbpedia.org/ontology/Artist','Artist',226,0,0),(267,'http://dbpedia.org/ontology/GeopoliticalOrganisation','GeopoliticalOrganisation',318,0,1),(268,'http://dbpedia.org/ontology/MouseGene','MouseGene',133,0,1),(269,'http://dbpedia.org/ontology/AmericanFootballTeam','AmericanFootballTeam',287,0,1),(270,'http://dbpedia.org/ontology/GaelicGamesPlayer','GaelicGamesPlayer',157,0,1),(271,'http://dbpedia.org/ontology/Novel','Novel',208,0,0),(272,'http://dbpedia.org/ontology/Eukaryote','Eukaryote',333,0,0),(273,'http://dbpedia.org/ontology/VicePresident','VicePresident',100,0,1),(274,'http://dbpedia.org/ontology/BaseballLeague','BaseballLeague',220,0,1),(275,'http://dbpedia.org/ontology/EducationalInstitution','EducationalInstitution',318,0,0),(276,'http://dbpedia.org/ontology/SpaceStation','SpaceStation',131,0,1),(277,'http://dbpedia.org/ontology/Constellation','Constellation',0,0,1),(278,'http://dbpedia.org/ontology/PaintballLeague','PaintballLeague',220,0,1),(279,'http://dbpedia.org/ontology/ClubMoss','ClubMoss',152,0,1),(280,'http://dbpedia.org/ontology/LacrosseLeague','LacrosseLeague',220,0,1),(281,'http://dbpedia.org/ontology/FormulaOneRacing','FormulaOneRacing',220,0,1),(282,'http://dbpedia.org/ontology/SportsEvent','SportsEvent',64,0,0),(283,'http://dbpedia.org/ontology/Pope','Pope',114,0,1),(284,'http://dbpedia.org/ontology/ChemicalCompound','ChemicalCompound',327,0,1),(285,'http://dbpedia.org/ontology/WorldHeritageSite','WorldHeritageSite',320,0,1),(286,'http://dbpedia.org/ontology/AmericanFootballLeague','AmericanFootballLeague',220,0,1),(287,'http://dbpedia.org/ontology/SportsTeam','SportsTeam',318,0,0),(288,'http://dbpedia.org/ontology/AustralianFootballLeague','AustralianFootballLeague',220,0,1),(289,'http://dbpedia.org/ontology/BroadcastNetwork','BroadcastNetwork',379,0,1),(290,'http://dbpedia.org/ontology/HockeyTeam','HockeyTeam',287,0,1),(291,'http://dbpedia.org/ontology/Decoration','Decoration',244,0,1),(292,'http://dbpedia.org/ontology/WrestlingEvent','WrestlingEvent',282,0,1),(293,'http://dbpedia.org/ontology/Bridge','Bridge',323,0,1),(294,'http://dbpedia.org/ontology/Mountain','Mountain',81,0,1),(295,'http://dbpedia.org/ontology/Cave','Cave',81,0,1),(296,'http://dbpedia.org/ontology/Scientist','Scientist',226,0,1),(297,'http://dbpedia.org/ontology/Website','Website',198,0,1),(298,'http://dbpedia.org/ontology/Lymph','Lymph',8,0,1),(299,'http://dbpedia.org/ontology/ResearchProject','ResearchProject',241,0,1),(300,'http://dbpedia.org/ontology/Animal','Animal',272,0,0),(301,'http://dbpedia.org/ontology/Continent','Continent',336,0,1),(302,'http://dbpedia.org/ontology/Arena','Arena',358,0,1),(303,'http://dbpedia.org/ontology/SoccerLeagueSeason','SoccerLeagueSeason',51,0,1),(304,'http://dbpedia.org/ontology/Criminal','Criminal',226,0,1),(305,'http://dbpedia.org/ontology/Surname','Surname',39,0,1),(306,'http://dbpedia.org/ontology/Mammal','Mammal',300,0,1),(307,'http://dbpedia.org/ontology/Election','Election',64,0,1),(308,'http://dbpedia.org/ontology/BoxingLeague','BoxingLeague',220,0,1),(309,'http://dbpedia.org/ontology/RadioControlledRacingLeague','RadioControlledRacingLeague',220,0,1),(310,'http://dbpedia.org/ontology/Fungus','Fungus',272,0,1),(311,'http://dbpedia.org/ontology/Aircraft','Aircraft',131,0,1),(312,'http://dbpedia.org/ontology/FictionalCharacter','FictionalCharacter',226,0,0),(313,'http://dbpedia.org/ontology/Moss','Moss',152,0,1),(314,'http://dbpedia.org/ontology/TennisLeague','TennisLeague',220,0,1),(315,'http://dbpedia.org/ontology/Biomolecule','Biomolecule',0,9593,0),(316,'http://dbpedia.org/ontology/Station','Station',229,0,0),(317,'http://dbpedia.org/ontology/River','River',85,0,1),(318,'http://dbpedia.org/ontology/Organisation','Organisation',255,175252,0),(319,'http://dbpedia.org/ontology/ComicsCharacter','ComicsCharacter',312,0,1),(320,'http://dbpedia.org/ontology/Place','Place',0,0,0),(321,'http://dbpedia.org/ontology/Embryology','Embryology',8,0,1),(322,'http://dbpedia.org/ontology/Unknown','Unknown',0,0,1),(323,'http://dbpedia.org/ontology/RouteOfTransportation','RouteOfTransportation',229,0,0),(324,'http://dbpedia.org/ontology/SportsTeamSeason','SportsTeamSeason',318,0,0),(325,'http://dbpedia.org/ontology/GreenAlga','GreenAlga',152,0,1),(326,'http://dbpedia.org/ontology/Genre','Genre',0,0,0),(327,'http://dbpedia.org/ontology/ChemicalSubstance','ChemicalSubstance',0,6057,0),(328,'http://dbpedia.org/ontology/BaseballPlayer','BaseballPlayer',157,0,1),(329,'http://dbpedia.org/ontology/RoadTunnel','RoadTunnel',90,0,1),(330,'http://dbpedia.org/ontology/VicePrimeMinister','VicePrimeMinister',100,0,1),(331,'http://dbpedia.org/ontology/Instrument','Instrument',0,0,1),(332,'http://dbpedia.org/ontology/Mayor','Mayor',100,0,1),(333,'http://dbpedia.org/ontology/Species','Species',0,0,0),(334,'http://dbpedia.org/ontology/TelevisionStation','TelevisionStation',379,0,1),(335,'http://dbpedia.org/ontology/Race','Race',282,0,1),(336,'http://dbpedia.org/ontology/PopulatedPlace','PopulatedPlace',320,0,0),(337,'http://dbpedia.org/ontology/Cyclist','Cyclist',157,0,1),(338,'http://dbpedia.org/ontology/RugbyLeague','RugbyLeague',220,0,1),(339,'http://dbpedia.org/ontology/SoccerClub','SoccerClub',287,0,0),(340,'http://dbpedia.org/ontology/Asteroid','Asteroid',245,0,1),(341,'http://dbpedia.org/ontology/Weapon','Weapon',106,0,1),(342,'http://dbpedia.org/ontology/Lake','Lake',263,0,1),(343,'http://dbpedia.org/ontology/Lieutenant','Lieutenant',100,0,1),(344,'http://dbpedia.org/ontology/Celebrity','Celebrity',226,0,1),(345,'http://dbpedia.org/ontology/Letter','Letter',0,0,1),(346,'http://dbpedia.org/ontology/TopicalConcept','TopicalConcept',0,0,1),(347,'http://dbpedia.org/ontology/Lighthouse','Lighthouse',358,0,1),(348,'http://dbpedia.org/ontology/MemberOfParliament','MemberOfParliament',100,0,1),(349,'http://dbpedia.org/ontology/WineRegion','WineRegion',320,0,1),(350,'http://dbpedia.org/ontology/RailwayStation','RailwayStation',316,0,1),(351,'http://dbpedia.org/ontology/ChristianBishop','ChristianBishop',114,0,1),(352,'http://dbpedia.org/ontology/PoloLeague','PoloLeague',220,0,1),(353,'http://dbpedia.org/ontology/AutoRacingLeague','AutoRacingLeague',220,0,1),(354,'http://dbpedia.org/ontology/HistoricPlace','HistoricPlace',320,0,1),(355,'http://dbpedia.org/ontology/BritishRoyalty','BritishRoyalty',164,0,1),(356,'http://dbpedia.org/ontology/Manhua','Manhua',205,0,1),(357,'http://dbpedia.org/ontology/PolishKing','PolishKing',164,0,1),(358,'http://dbpedia.org/ontology/Building','Building',62,0,0),(359,'http://dbpedia.org/ontology/Astronaut','Astronaut',226,0,1),(360,'http://dbpedia.org/ontology/MixedMartialArtsLeague','MixedMartialArtsLeague',220,0,1),(361,'http://dbpedia.org/ontology/AdministrativeRegion','AdministrativeRegion',336,0,1),(362,'http://dbpedia.org/ontology/Single','Single',129,0,1),(363,'http://dbpedia.org/ontology/SoftballLeague','SoftballLeague',220,0,1),(364,'http://dbpedia.org/ontology/Cartoon','Cartoon',198,0,0),(365,'http://dbpedia.org/ontology/Spacecraft','Spacecraft',131,0,1),(366,'http://dbpedia.org/ontology/MixedMartialArtsEvent','MixedMartialArtsEvent',282,0,1),(367,'http://dbpedia.org/ontology/VideoGame','VideoGame',58,0,1),(368,'http://dbpedia.org/ontology/Governor','Governor',100,0,1),(369,'http://dbpedia.org/ontology/TradeUnion','TradeUnion',318,0,1),(370,'http://dbpedia.org/ontology/OfficeHolder','OfficeHolder',226,0,1),(371,'http://dbpedia.org/ontology/CricketLeague','CricketLeague',220,0,1),(372,'http://dbpedia.org/ontology/RugbyClub','RugbyClub',287,0,1),(373,'http://dbpedia.org/ontology/MountainRange','MountainRange',81,0,1),(374,'http://dbpedia.org/ontology/AustralianRulesFootballPlayer','AustralianRulesFootballPlayer',157,0,1),(375,'http://dbpedia.org/ontology/BasketballPlayer','BasketballPlayer',157,0,1),(376,'http://dbpedia.org/ontology/Chancellor','Chancellor',100,0,1),(377,'http://dbpedia.org/ontology/LegalCase','LegalCase',0,0,0),(378,'http://dbpedia.org/ontology/Crustacean','Crustacean',300,0,1),(379,'http://dbpedia.org/ontology/Broadcaster','Broadcaster',318,0,0),(380,'http://dbpedia.org/ontology/Cycad','Cycad',152,0,1);
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `errors`
--

DROP TABLE IF EXISTS `errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `errors` (
  `error_id` bigint(2) NOT NULL,
  `error_title` varchar(256) NOT NULL,
  `example_uri` varchar(256) NOT NULL,
  `example_n3` varchar(512) NOT NULL,
  `description` varchar(10000) NOT NULL,
  `error_parent` bigint(2) NOT NULL,
  `is_leaf` tinyint(1) NOT NULL,
  PRIMARY KEY (`error_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `errors`
--

LOCK TABLES `errors` WRITE;
/*!40000 ALTER TABLE `errors` DISABLE KEYS */;
INSERT INTO `errors` VALUES (1,'Accuracy','','','Accuracy',0,0),(2,'Triple incorrectly extracted','','','Triple incorrectly extracted',1,0),(3,'Object value is incorrectly extracted','http://dbpedia.org/resource/Oregon_Route_238','dbpprop:map \"238.0\"^^<http://dbpedia.org/datatype/second> .','The resource is about a \"Oregon Route 238\", which is a state highway and the property map has the value 238 which is incorrect. This is because Wikipedia has an attribute map, which has the image name as the value: map=Oregon Route 238.svg. The dbprop only extracted the value 238 from his attribute value and gave it a dataype second. ',2,1),(4,'Object value is incompletely extracted','http://dbpedia.org/resource/Dave_Dobbyn','dbpprop:dateOfBirth \"3\"^^<http://www.w3.org/2001/XMLSchema#int> .','In this example, only the day of birth of a person is extracted and mapped to the dateofBirth property when it should have been the entire date ie. day,month and year. ',2,1),(5,'Special template not properly recognized','http://dbpedia.org/page/328_Gudrun','dbpprop:auto \"yes\"@en .','Many Wikipedia articles have an indication on top of the page, which says: \"This article does not cite any references or sources.\" referring to the fact that it is unreferenced. This information is stored in an attribute \"Unreferenced stub|auto=yes\". DBpedia thus extracts this attribute auto and value yes as a triple, which is not meaningful by itself and should not be extracted. ',2,1),(6,'Datatype problems','','','Datatype problems',1,0),(7,'Datatype incorrectly extracted','http://dbpedia.org/resource/Torishima_%28Izu_Islands%29','foaf:name, 鳥島@en','The datatype of a literal is incorrectly mapped. In this case, the name is not in English but has the datatype @en. ',6,1),(8,'Implicit relationship between attributes','','','Incorrect mapping - Implicit relationship between attributes',1,0),(9,'One fact is encoded in several attributes ','http://dbpedia.org/resource/Barlinek','dbpprop:postalCodeType Postal code','In several cases, one fact in Wikipedia is encoded in several attributes. In this example, the value of the postal code of the town of Barlinek is encoded in two attributes \"postal_code_type = Postal code\" and \"postal_code = 74-320\". DBpedia extracts both of these attributes as is when it should have combined the attributes together to produce only the triple \"http://dbpedia.org/resource/Barlinek dbpprop:postalCode 74\" and not extract the postal code type as \"postal code\". ',8,1),(10,'Several facts are encoded in one attribute','http://dbpedia.org/resource/Picathartes','dbpedia-owl:synonym \"Galgulus Wagler, 1827 (non Brisson, 1760: preoccupied)\"@en . ','In this example, the triple is not incorrect but in fact contains two pieces of information in it. Only the first word is the synonym, the rest is a citation or reference. In Wikipedia, it is represented as \"synonyms = \'\'Galgulus\'\' <small>Wagler, 1827 (\'\'non\'\' [[Mathurin Jacques Brisson|Brisson]], 1760: [[Coracias|preoccupied]])</small><br />\". Therefore, since several facts (synonym and reference, in this case) are encoded in one attribute, DBpedia should recognize and separate these facts into several triples. ',8,1),(11,'Attribute value computed from another attribute value','http://dbpedia.org/page/Barlinek','dbpprop:populationDensityKm \"auto\"@en . ','In Wikipedia, the attribute \"population_density_km2\" = auto means that this property is computed automatically by dividing population by area. But, DBpedia doesn\'t do the conversion and extract the value \"auto\" as it is. ',8,1),(12,'Relevancy','','','Relevancy',0,0),(13,'Irrelevant information extracted','','','Irrelevant information extracted',12,0),(14,'Extraction of attributes containing layout information','http://dbpedia.org/resource/L%C3%A6rdals%C3%B8yri','dbpprop:pushpinLabelPosition \"bottom\"@en .','Information related to layout of a page in Wikipedia, such as position of an image caption, position or size of an image, position of the co-ordinates are irrelevant when extracted in DBpedia. ',13,1),(15,'Redundant attributes value','http://dbpedia.org/resource/Niedersimmental_District','\"dbpedia-owl:thumbnail, foaf:depiction, dbpprop:imageMap Karte Bezirk Niedersimmental 2007.png\"\"\"','Three properties: dbpedia-owl:thumbnail, foaf:depiction, and dbpprop:imageMapRedundant refer to the same subject \"Karte_Bezirk_Niedersimmental_2007.png\" and are thus redundant in DBpedia. ',13,1),(16,'Image related information','http://dbpedia.org/resource/Three-banded_Plover','dbpprop:imageCaption At Masai Mara National Reserve, Kenya','Extraction of an image caption and name of the image are irrelevant when extracted in DBpedia. ',13,1),(17,'Other irrelevant information ','http://dbpedia.org/resource/Chestnut-banded_Plover','dbpprop:downloaded 2007-07-24 (xsd:date)','The date when the article is retrieved is extracted as a triple. This information is not present in Wikipedia\'s Infobox but it refers to the date on which a link in the references was retrieved and therefore should not be extracted for this resource. ',13,1),(18,'Representational - Consistency','','','Representational Consistency',0,0),(19,'Representation of number values','http://dbpedia.org/resource/Drei_Fl%C3%BCsse_Stadion','dbpprop:seatingCapacity \"20\"^^<http://www.w3.org/2001/XMLSchema#int> .','In Wkipedia, the seating capacity has the value \"20.000\", but the extractor is stopped by the period and does not include \"000\". ',18,1),(20,'Single double-quote inside literal value','http://dbpedia.org/resource/Arthur_Jensen','dbpprop:workInstitution \"Editorial boards of Intelligence and \'\'Personality and Individual Differences\"@en .','in Wkipedia: work_institution = Editorial boards of \'\'[[Intelligence (journal)|Intelligence]]\'\' and \'\'[[Personality and Individual Differences]] , which has the error of not completing the double quotes. Thus, instead of extracting two triples, it extracts the entire value as one. ',18,1),(21,'External websites (URLs)','http://dbpedia.org/page/Canaan_Valley','foaf:homepage <http://www.nature.nps.gov/nnl/Registry/USA_Map/States/West%20Virginia/NNL/CV/index.cfm> .','Links to external websites or external data sources are either incorrect or they do not show any information or are expired. ',27,1),(22,'Interlinks with other datasets (URIs)','','','Interlinks with other datasets (URIs)',27,0),(23,'Links to wikimedia','http://dbpedia.org/page/Wizard_of_New_Zealand','foaf:depiction <http://upload.wikimedia.org/wikipedia/commons/c/c4/Christchurchwizard.jpg> .','Links to wikimedia',22,1),(24,'Links to Freebase','http://dbpedia.org/page/ChaalBaaz','dbpprop:hasPhotoCollection http://www4.wiwiss.fu-berlin.de/flickrwrappr/photos/ChaalBaaz','Links to Freebase',22,1),(25,'Links to geospecies ','http://dbpedia.org/resource/Balkan_Mole','owl:sameAs <http://lod.geospecies.org/ses/LfBGE>.','Links to geospecies ',22,1),(26,'Links generated via flickr wrapper','http://dbpedia.org/page/ChaalBaaz','dbpprop:hasPhotoCollection http://www4.wiwiss.fu-berlin.de/flickrwrappr/photos/ChaalBaaz','Links generated via flickr wrapper',22,1),(27,'Interlinking - Coherency','','','Interlinking - Coherency',0,0);
/*!40000 ALTER TABLE `errors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluated_resource`
--

DROP TABLE IF EXISTS `evaluated_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluated_resource` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `sid` bigint(20) NOT NULL,
  `resource` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `comments` varchar(10000) COLLATE utf8_unicode_ci NOT NULL,
  `class` varchar(256) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT 'The user selected to evaluate a resource of this class',
  `correct` tinyint(1) NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `resource` (`resource`(255)),
  KEY `sid` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluated_resource`
--

LOCK TABLES `evaluated_resource` WRITE;
/*!40000 ALTER TABLE `evaluated_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluated_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluated_resource_details`
--

DROP TABLE IF EXISTS `evaluated_resource_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluated_resource_details` (
  `eid` bigint(20) NOT NULL AUTO_INCREMENT,
  `rid` bigint(20) DEFAULT NULL,
  `predicate` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `object` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `error_id` bigint(20) DEFAULT NULL,
  `comment` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`eid`),
  KEY `rid` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluated_resource_details`
--

LOCK TABLES `evaluated_resource_details` WRITE;
/*!40000 ALTER TABLE `evaluated_resource_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluated_resource_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation_session`
--

DROP TABLE IF EXISTS `evaluation_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation_session` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `cid` bigint(11) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sid`),
  KEY `cid` (`cid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_session`
--

LOCK TABLES `evaluation_session` WRITE;
/*!40000 ALTER TABLE `evaluation_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluation_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `googleid` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `picture` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `profile` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `statR` int(11) NOT NULL DEFAULT '0',
  `statT` int(11) NOT NULL DEFAULT '0',
  `statD` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `googleid` (`googleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-13 20:07:33
