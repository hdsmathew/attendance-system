SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `administration` (
  `id` int NOT NULL,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `phoneNo` int NOT NULL,
  `type` set('admin','registry') NOT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `administration` (`id`, `fname`, `lname`, `password`, `phoneNo`, `type`, `email`) VALUES
(1, 'Mathew', 'mat', '1234', 57087019, 'admin', 'mat@gmail.com'),
(2, 'Headrick', 'hds', '1234', 58078091, 'registry', 'hds@gmail.com');

CREATE TABLE `attendance` (
  `lectID` int NOT NULL,
  `moduleCode` int NOT NULL,
  `studentID` int NOT NULL,
  `date` date NOT NULL,
  `present` tinyint NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course` (
  `courseCode` int NOT NULL,
  `courseName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `onOffer` tinyint NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `course` (`courseCode`, `courseName`, `onOffer`) VALUES
(1, 'Computer Science', 1),
(2, 'Software Engineering', 1),
(3, 'Applied Computing', 1),
(4, 'Data Science', 1),
(5, 'Mathematics', 0),
(6, 'Information System', 1);

CREATE TABLE `enrollment` (
  `studentID` int NOT NULL,
  `courseCode` int NOT NULL,
  `enrollDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `enrollment` (`studentID`, `courseCode`, `enrollDate`) VALUES
(1, 1, '2022-01-02'),
(2, 1, '2022-01-02'),
(3, 1, '2022-01-02'),
(4, 1, '2022-01-02'),
(5, 1, '2022-01-02'),
(6, 2, '2022-01-02'),
(7, 2, '2022-01-02'),
(8, 2, '2022-01-02'),
(9, 2, '2022-01-02'),
(10, 2, '2022-01-02'),
(11, 3, '2022-01-02'),
(12, 3, '2022-01-02'),
(13, 3, '2022-01-02'),
(14, 3, '2022-01-02'),
(15, 3, '2022-01-02'),
(16, 4, '2022-01-02'),
(17, 4, '2022-01-02'),
(18, 4, '2022-01-02'),
(19, 4, '2022-01-02'),
(20, 4, '2022-01-02'),
(21, 5, '2022-01-02'),
(22, 5, '2022-01-02'),
(23, 5, '2022-01-02'),
(24, 5, '2022-01-02'),
(25, 5, '2022-01-02');

CREATE TABLE `lecturer` (
  `lectID` int NOT NULL,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phoneNo` int NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `teachingStatus` tinyint NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `lecturer` (`lectID`, `fname`, `lname`, `address`, `phoneNo`, `email`, `password`, `teachingStatus`) VALUES
(1, 'John', 'Doe', 'Port Louis', 1234567, 'doe@gmail.com', '1234', 1),
(2, 'Joe', 'Too', 'Quatre Bornes', 2143657, 'joe@gmail.com', '4321', 1),
(3, 'Sava', 'Toa', 'Quatre Soeurs', 3241756, 'sava@gmail.com', '2341', 0),
(4, 'Sudo', 'Koo', 'Quatre Freres', 1324576, 'sudo@gmail.com', '3142', 0),
(5, 'Kong', 'Ngolo', 'Pamplemousses', 7564312, 'kong@gmail.com', '2143', 1);

CREATE TABLE `module` (
  `moduleCode` int NOT NULL,
  `moduleName` varchar(75) NOT NULL,
  `courseCode` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `module` (`moduleCode`, `moduleName`, `courseCode`) VALUES
(1, 'Formal', 1),
(2, 'Architecture', 1),
(3, 'Operating System', 1),
(4, 'Ethics', 2),
(5, 'Development', 2),
(6, 'Maths', 2),
(7, 'Network', 3),
(8, 'Data Structures', 3),
(9, 'Algorithms', 3),
(10, 'Machine Learning', 4),
(11, 'Advanced Maths', 4),
(12, 'Programming', 4),
(13, 'Calculus 1', 5),
(14, 'Calculus 2', 5),
(15, 'Calculus 3', 5),
(16, 'Database', 6),
(17, 'Mobile', 6),
(18, 'Cloud', 6);

CREATE TABLE `student` (
  `studentID` int NOT NULL,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `phoneNo` int NOT NULL,
  `email` varchar(50) NOT NULL,
  `active` tinyint NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `student` (`studentID`, `fname`, `lname`, `address`, `phoneNo`, `email`, `active`) VALUES
(1, 'One', 'Wan', 'Savanne', 4235131, 'One@gmail.com', 1),
(2, 'Two', 'Too', 'Savanne', 4235131, 'Two@gmail.com', 1),
(3, 'Three', 'Tree', 'Savanne', 4235131, 'Three@gmail.com', 1),
(4, 'Four', 'Fort', 'Savanne', 4235131, 'Four@gmail.com', 1),
(5, 'Five', 'Fyve', 'Savanne', 4235131, 'Five@gmail.com', 1),
(6, 'Six', 'Sick', 'Savanne', 4235131, 'Six@gmail.com', 0),
(7, 'Seven', 'Sevainne', 'Savanne', 4235131, 'Seven@gmail.com', 1),
(8, 'Eight', 'Ate', 'Savanne', 4235131, 'Eight@gmail.com', 1),
(9, 'Nine', 'Neigne', 'Savanne', 4235131, 'Nine@gmail.com', 1),
(10, 'Ten', 'Tein', 'Savanne', 4235131, 'Ten@gmail.com', 1),
(11, 'Eleven', 'WanWan', 'Savanne', 4235131, 'Eleven@gmail.com', 1),
(12, 'Twelve', 'WanToo', 'Savanne', 4235131, 'Twelve@gmail.com', 1),
(13, 'Thirteen', 'WanTree', 'Savanne', 4235131, 'Three@gmail.com', 0),
(14, 'Fourteen', 'WanFort', 'Savanne', 4235131, 'Four@gmail.com', 1),
(15, 'Fifteen', 'WanFyve', 'Savanne', 4235131, 'Fifteen@gmail.com', 1),
(16, 'Sixteen', 'WanSick', 'Savanne', 4235131, 'Sixteen@gmail.com', 1),
(17, 'Seventeen', 'WanSevainne', 'Savanne', 4235131, 'Seventeen@gmail.com', 1),
(18, 'Eighteen', 'WanAte', 'Savanne', 4235131, 'Eighteen@gmail.com', 1),
(19, 'Nineteen', 'WanNeigne', 'Savanne', 4235131, 'Nineteen@gmail.com', 1),
(20, 'Twenty', 'Teinty', 'Savanne', 4235131, 'Twenty@gmail.com', 0),
(21, 'TwentyOne', 'TeintyWan', 'Savanne', 4235131, 'TwentyOne@gmail.com', 1),
(22, 'TwentyTwo', 'TeintyToo', 'Savanne', 4235131, 'TwentyTwo@gmail.com', 1),
(23, 'TwentyThree', 'TeintyTree', 'Savanne', 4235131, 'TwentyThree@gmail.com', 1),
(24, 'TwentyFour', 'TeintyFort', 'Savanne', 4235131, 'TwentyFour@gmail.com', 1),
(25, 'TwentyFive', 'TeintyFyve', 'Savanne', 4235131, 'TwentyFive@gmail.com', 1);

CREATE TABLE `teach` (
  `lectID` int NOT NULL,
  `moduleCode` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `teach` (`lectID`, `moduleCode`) VALUES
(1, 1),
(3, 2),
(3, 3),
(1, 4),
(2, 5),
(4, 6),
(1, 7),
(1, 8),
(1, 9),
(2, 10),
(2, 11),
(3, 12),
(5, 13),
(3, 14),
(4, 15),
(4, 16),
(5, 17),
(4, 18);


ALTER TABLE `administration`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `attendance`
  ADD PRIMARY KEY (`lectID`,`moduleCode`,`studentID`,`date`),
  ADD KEY `module-attendance` (`moduleCode`),
  ADD KEY `student-attendance` (`studentID`);

ALTER TABLE `course`
  ADD PRIMARY KEY (`courseCode`);

ALTER TABLE `enrollment`
  ADD PRIMARY KEY (`studentID`,`courseCode`,`enrollDate`),
  ADD KEY `course-enrollment` (`courseCode`);

ALTER TABLE `lecturer`
  ADD PRIMARY KEY (`lectID`);

ALTER TABLE `module`
  ADD PRIMARY KEY (`moduleCode`),
  ADD KEY `module-course` (`courseCode`);

ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`);

ALTER TABLE `teach`
  ADD PRIMARY KEY (`lectID`,`moduleCode`),
  ADD KEY `teach-module` (`moduleCode`);


ALTER TABLE `administration`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE `course`
  MODIFY `courseCode` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `lecturer`
  MODIFY `lectID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `module`
  MODIFY `moduleCode` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

ALTER TABLE `student`
  MODIFY `studentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;


ALTER TABLE `attendance`
  ADD CONSTRAINT `lecturer-attendance` FOREIGN KEY (`lectID`) REFERENCES `lecturer` (`lectID`),
  ADD CONSTRAINT `module-attendance` FOREIGN KEY (`moduleCode`) REFERENCES `module` (`moduleCode`),
  ADD CONSTRAINT `student-attendance` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`);

ALTER TABLE `enrollment`
  ADD CONSTRAINT `course-enrollment` FOREIGN KEY (`courseCode`) REFERENCES `course` (`courseCode`),
  ADD CONSTRAINT `student-enrollment` FOREIGN KEY (`studentID`) REFERENCES `student` (`studentID`);

ALTER TABLE `teach`
  ADD CONSTRAINT `teach-lecturer` FOREIGN KEY (`lectID`) REFERENCES `lecturer` (`lectID`),
  ADD CONSTRAINT `teach-module` FOREIGN KEY (`moduleCode`) REFERENCES `module` (`moduleCode`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
