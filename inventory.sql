-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 02, 2017 at 06:33 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
/*Time Zone
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `type` varchar(100) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `type`, `description`) VALUES
(1, 'Shampoo', 'Hair Product'),
(2, 'Gel', 'Hair styling'),
(3, 'Cosmetics', 'Grooming products'),
(4, 'Color', 'Hair colour and dyes');

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `type` enum('admin','employee') NOT NULL DEFAULT 'employee'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `firstname`, `lastname`, `username`, `password`, `phone`, `address`, `type`) VALUES
(1, 'john', 'cena', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', '0099887766', 'New York, USA', 'admin'),
(2, 'Martha', 'Jones', 'user', '12dea96fec20593566ab75692c9949596833adc9', '123456789', 'Seattle', 'employee');

-- --------------------------------------------------------

--
-- Table structure for table `invoices`
--

CREATE TABLE `invoices` (
  `id` varchar(13) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `total` double NOT NULL,
  `vat` double NOT NULL,
  `discount` double NOT NULL,
  `payable` double NOT NULL,
  `paid` double NOT NULL,
  `returned` double NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `invoices`
--

INSERT INTO `invoices` (`id`, `employeeId`, `total`, `vat`, `discount`, `payable`, `paid`, `returned`, `datetime`) VALUES
('1491729973342', 2, 760, 19, 5, 774, 800, 26, '2017-01-09 15:26:13'),
('1491730560516', 2, 370, 9.25, 5, 374.25, 375, 0.75, '2017-01-09 15:36:00'),
('1492165305284', 2, 270, 6.75, 5, 271.75, 280, 8.25, '2017-01-14 16:21:45'),
('1492189349464', 2, 490, 12.25, 5, 497.25, 500, 2.75, '2017-02-14 23:02:29'),
('1492189946488', 2, 190, 4.75, 5, 189.75, 200, 10.25, '2017-02-14 23:12:26'),
('1492190099626', 2, 120, 3, 5, 118, 120, 2, '2017-04-14 23:14:59'),
('1492190341116', 2, 65, 1.625, 5, 61.625, 62, 0.375, '2017-04-14 23:19:01'),
('1492191099328', 2, 190, 4.75, 5, 189.75, 190, 0.25, '2017-04-14 23:31:39'),
('1492192975710', 2, 770, 19.25, 5, 784.25, 1000, 215.75, '2017-04-15 00:02:55'),
('1492193361407', 2, 865, 21.625, 5, 881.625, 900, 18.375, '2017-03-15 00:09:21'),
('1492313070538', 2, 275, 6.875, 5, 276.875, 300, 23.125, '2017-03-16 09:24:30'),
('1493699328760', 2, 70, 1.75, 5, 66.75, 70, 3.25, '2017-05-02 10:28:48'),
('1493699482352', 2, 190, 4.75, 5, 189.75, 190, 0.25, '2017-05-02 10:31:22');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `quantity` double NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `categoryId`, `supplierId`, `name`, `price`, `quantity`, `description`) VALUES
(1, 1, 1, 'Clinical Solutions', 165, 47, 'Anti-dandruff shampoo'),
(2, 1, 1, 'Classic Clean', 120, 144, 'General shampoo'),
(3, 4, 2, 'HiColor', 190, 33, 'Red Color 50g box'),
(4, 2, 2, 'Wax', 65, 38, 'Hair wax'),
(5, 3, 3, 'Power Light', 70, 93, 'Freshness Cream'),
(6, 3, 3, 'Oil Clear', 160, 294, 'Face Wash'),
(7, 3, 6, 'Brylcreem (Red)', 300, 128, 'Light glossy hold'),
(8, 3, 1, 'Brylcreem (Green)', 105, 0, 'Anti-dandruff');

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `supplierId` int(11) NOT NULL,
  `quantity` double NOT NULL,
  `price` double NOT NULL,
  `total` double NOT NULL,
  `datetime` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`id`, `productId`, `supplierId`, `quantity`, `price`, `total`, `datetime`) VALUES
(1, 1, 1, 5, 165, 825, '2017-03-14 00:00:00'),
(2, 2, 2, 6, 120, 720, '2017-03-09 00:00:00'),
(3, 1, 1, 1, 24, 24, '2017-05-02 10:02:47'),
(4, 1, 1, 2, 20, 40, '2017-05-02 10:10:37');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `invoiceId` varchar(13) NOT NULL,
  `productId` int(11) NOT NULL,
  `quantity` double NOT NULL,
  `price` int(11) NOT NULL,
  `total` double NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `invoiceId`, `productId`, `quantity`, `price`, `total`, `datetime`) VALUES
(1, '1491729973342', 7, 2, 300, 600, '2017-04-09 15:26:13'),
(2, '1491729973342', 6, 1, 160, 160, '2017-04-09 15:26:13'),
(3, '1491730560516', 2, 2, 120, 240, '2017-04-09 15:36:00'),
(4, '1491730560516', 4, 2, 65, 130, '2017-04-09 15:36:00'),
(5, '1492165305284', 5, 2, 70, 140, '2017-04-14 16:21:45'),
(6, '1492165305284', 4, 2, 65, 130, '2017-04-14 16:21:45'),
(7, '1492189349464', 1, 2, 165, 330, '2017-01-14 23:02:29'),
(8, '1492189349464', 6, 1, 160, 160, '2017-04-14 23:02:29'),
(9, '1492189946488', 3, 1, 190, 190, '2017-04-14 23:12:26'),
(10, '1492190099626', 2, 1, 120, 120, '2017-04-14 23:14:59'),
(11, '1492190341116', 4, 1, 65, 65, '2017-04-14 23:19:01'),
(12, '1492191099328', 3, 1, 190, 190, '2017-04-14 23:31:39'),
(13, '1492192975710', 6, 2, 160, 320, '2017-04-15 00:02:55'),
(14, '1492192975710', 2, 1, 120, 120, '2017-04-15 00:02:55'),
(15, '1492192975710', 1, 2, 165, 330, '2017-02-15 00:02:55'),
(16, '1492193361407', 3, 2, 190, 380, '2017-04-15 00:09:21'),
(17, '1492193361407', 1, 1, 165, 165, '2017-03-15 00:09:21'),
(18, '1492193361407', 6, 2, 160, 320, '2017-04-15 00:09:21'),
(19, '1492313070538', 5, 3, 70, 210, '2017-04-16 09:24:30'),
(20, '1492313070538', 4, 1, 65, 65, '2017-04-16 09:24:30'),
(21, '1493699482352', 3, 1, 190, 190, '2017-05-02 10:31:22');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`id`, `name`, `phone`, `address`) VALUES
(1, 'Head & Shoulder', '00000000', 'USA'),
(2, 'Loreal', '1111111', 'France'),
(3, 'Garnier', '22222222', 'France'),
(4, 'Set Wet', '444444', 'India'),
(5, 'Layer', '555555', 'India'),
(6, 'Brylcreem', '777777', 'UK'),
(7, 'Gatsby', '8888888', 'Canada');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invoices`
--
ALTER TABLE `invoices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoryId` (`categoryId`),
  ADD KEY `supplierId` (`supplierId`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD KEY `productId` (`productId`),
  ADD KEY `supplierId` (`supplierId`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `productId` (`productId`),
  ADD KEY `invoiceId` (`invoiceId`);

--
-- Indexes for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`);

--
-- Constraints for table `purchases`
--
ALTER TABLE `purchases`
  ADD CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `purchases_ibfk_2` FOREIGN KEY (`supplierId`) REFERENCES `suppliers` (`id`);

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`invoiceId`) REFERENCES `invoices` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
