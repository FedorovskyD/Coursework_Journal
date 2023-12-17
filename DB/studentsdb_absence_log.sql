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
-- Table structure for table `absence_log`
--

DROP TABLE IF EXISTS `absence_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `absence_log` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `student_ID` int NOT NULL,
  `lesson_ID` int NOT NULL,
  `isHalf` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `attendance_lab_null_fk` (`lesson_ID`),
  KEY `attendance_student_null_fk` (`student_ID`),
  CONSTRAINT `attendance_lab_null_fk` FOREIGN KEY (`lesson_ID`) REFERENCES `lesson` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `attendance_student_null_fk` FOREIGN KEY (`student_ID`) REFERENCES `student` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2939 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absence_log`
--

LOCK TABLES `absence_log` WRITE;
/*!40000 ALTER TABLE `absence_log` DISABLE KEYS */;
INSERT INTO `absence_log` VALUES (2516,7,70,0),(2518,9,70,0),(2539,6,76,0),(2541,8,76,0),(2576,5,72,0),(2578,7,72,0),(2579,8,72,0),(2580,9,72,0),(2581,10,72,1),(2583,12,72,0),(2584,13,72,0),(2585,14,72,0),(2587,16,72,0),(2588,17,72,0),(2593,5,71,0),(2595,7,71,0),(2596,8,71,0),(2600,12,71,0),(2601,13,71,0),(2602,14,71,0),(2603,15,71,0),(2604,16,71,0),(2611,9,135,0),(2613,11,133,0),(2614,13,138,0),(2679,5,85,0),(2680,6,85,0),(2681,7,85,0),(2682,8,85,0),(2685,5,76,0),(2727,10,70,0),(2784,20,79,0),(2797,9,71,0),(2799,9,79,0),(2801,9,89,0),(2802,9,85,0),(2805,175,135,0),(2806,175,188,1),(2820,9,202,0),(2822,8,202,0),(2823,7,202,0),(2824,5,202,0),(2826,16,202,0),(2827,15,202,0),(2828,14,202,0),(2829,13,202,0),(2832,10,202,0),(2833,6,202,0),(2835,18,202,0),(2836,17,202,0),(2853,6,77,0),(2854,6,79,0),(2883,22,92,0),(2890,15,70,0),(2925,5,223,0),(2937,11,71,1),(2938,175,76,0);
/*!40000 ALTER TABLE `absence_log` ENABLE KEYS */;
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
