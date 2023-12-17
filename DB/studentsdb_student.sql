-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: studentsdb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `LastName` varchar(255) NOT NULL,
  `FirstName` varchar(255) NOT NULL,
  `MiddleName` varchar(255) NOT NULL,
  `GroupID` int NOT NULL,
  `Telephone` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `PhotoPath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `GroupID` (`GroupID`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`GroupID`) REFERENCES `group` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (5,'Гузаревич','Елизавета ','Игоревна',1,'+375297787832','guzar@gmail.com','247_r.jpg'),(6,'Касперович','Илья','Олегович',1,'+375295874215','kasper@gmail.com','photos/default.jpg'),(7,'Кириллов','Станислав','Вячеславович',1,'+375297787832','sffdf@mail.com','photos/7.jpg'),(8,'Комисарчук','Марина','Сергеевна',1,'+375338594578','marishka04@gmail.com','photos\\default.jpg'),(9,'Кухта','Александрович','Артем',1,'+375334898','artem78@mail.ru','photos/default.jpg'),(10,'Новик','Егор','Сергеевич',1,'+375335872541','egor007@mail.ru','photos/default.jpg'),(11,'Попович','Алескей','Васильевич',1,'+375293654785','leha@gmail.com','photos/default.jpg'),(12,'Пополамов','Денис1','Вячеславович',1,'+375297787832','leha@gmail.com','photos\\default.jpg'),(13,'Савицкий','Даниил','Сергеевич',1,'+375335628978','flybynight@gmail.com','photos\\default.jpg'),(14,'Самуйлич','Данила','Сергеевич',1,'+375443589647','danya03@mail.ru','photos/default.jpg'),(15,'Синявская','Ульяна','Александровна',1,'+375293551505','sinyavskaau03@gmail.com','photos/15.jpg'),(16,'Слепцов','Даниил','Алексеевич',1,'+375297787832','leha@gmail.com','photos/default.jpg'),(17,'Тищенко','Святослав','Николаевич',1,'+375293657845','svyat@mail.ru','photos/default.jpg'),(18,'Турко','Владислав','Сергеевич',1,'+375293547845','vladtur@mail.ru','photos/default.jpg'),(19,'Фан','Ань Туан','Чан',1,'+375443658978','toha45@mail.ru','photos/default.jpg'),(20,'Федоровский','Дмитрий','Александрович',1,'+375334587963','fedosik56@gmail.com','photos\\20.jpg'),(21,'Тонкович','Никита','Шварцевич',2,'+375293658745','jintonik@gmail.com','photos/default.jpg'),(22,'Кирющева','Алена','Владимировна',2,'+37523894523','alena@gmail.com','photos/default.jpg'),(175,'Бойко','Богдан','Генадьевич',1,'+37528989922','boiki@gmail.com','photos\\175.jpg'),(198,'Федоровский','Дмитрий','Александрович',40,'+375293551505','dmitr@mail.ru','198_Федоровский.jpg');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-17 19:30:47
