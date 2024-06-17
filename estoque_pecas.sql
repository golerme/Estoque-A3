-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2024 at 08:40 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `estoque_pecas`
--

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `telefone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ordemservico`
--

CREATE TABLE `ordemservico` (
  `codigo` int(11) NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `descricao` text NOT NULL,
  `status` varchar(50) NOT NULL,
  `valor_servico` double DEFAULT NULL,
  `valor_pecas` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `peca`
--

CREATE TABLE `peca` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `peca`
--

INSERT INTO `peca` (`codigo`, `nome`, `descricao`, `quantidade`) VALUES
(3, 'martelo', 'aço inox', 13),
(4, 'bagos', '321', 2),
(5, 'pau', 'durasso', 99);

-- --------------------------------------------------------

--
-- Table structure for table `pecaordemservico`
--

CREATE TABLE `pecaordemservico` (
  `os_id` int(11) NOT NULL,
  `peca_id` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pecapedidocompra`
--

CREATE TABLE `pecapedidocompra` (
  `pedido_id` int(11) NOT NULL,
  `peca_id` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pedidocompra`
--

CREATE TABLE `pedidocompra` (
  `id` int(11) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','cliente') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'senha', 'admin'),
(2, 'pedrinhomatador', '321123', 'cliente');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ordemservico`
--
ALTER TABLE `ordemservico`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- Indexes for table `peca`
--
ALTER TABLE `peca`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `pecaordemservico`
--
ALTER TABLE `pecaordemservico`
  ADD PRIMARY KEY (`os_id`,`peca_id`),
  ADD KEY `peca_id` (`peca_id`);

--
-- Indexes for table `pecapedidocompra`
--
ALTER TABLE `pecapedidocompra`
  ADD PRIMARY KEY (`pedido_id`,`peca_id`),
  ADD KEY `peca_id` (`peca_id`);

--
-- Indexes for table `pedidocompra`
--
ALTER TABLE `pedidocompra`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ordemservico`
--
ALTER TABLE `ordemservico`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `peca`
--
ALTER TABLE `peca`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pedidocompra`
--
ALTER TABLE `pedidocompra`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ordemservico`
--
ALTER TABLE `ordemservico`
  ADD CONSTRAINT `ordemservico_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Constraints for table `pecaordemservico`
--
ALTER TABLE `pecaordemservico`
  ADD CONSTRAINT `pecaordemservico_ibfk_1` FOREIGN KEY (`os_id`) REFERENCES `ordemservico` (`codigo`),
  ADD CONSTRAINT `pecaordemservico_ibfk_2` FOREIGN KEY (`peca_id`) REFERENCES `peca` (`codigo`);

--
-- Constraints for table `pecapedidocompra`
--
ALTER TABLE `pecapedidocompra`
  ADD CONSTRAINT `pecapedidocompra_ibfk_1` FOREIGN KEY (`pedido_id`) REFERENCES `pedidocompra` (`id`),
  ADD CONSTRAINT `pecapedidocompra_ibfk_2` FOREIGN KEY (`peca_id`) REFERENCES `peca` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
