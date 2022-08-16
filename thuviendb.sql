CREATE SCHEMA `thuviendb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE thuviendb;
SET SQL_SAFE_UPDATES = 0;

CREATE TABLE `danh_muc` (
  `ma_danh_muc` int NOT NULL AUTO_INCREMENT,
  `ten_danh_muc` text DEFAULT NULL,
  PRIMARY KEY (`ma_danh_muc`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sach` (
  `ma_sach` varchar(50) NOT NULL,
  `ten_sach` text DEFAULT NULL,
  `mo_ta` text DEFAULT NULL,
  `nam_xuat_ban` int DEFAULT NULL,
  `noi_xuat_ban` text DEFAULT NULL,
  `ngay_nhap` date DEFAULT NULL,
  `vi_tri` text DEFAULT NULL,
  `ma_danh_muc` int DEFAULT NULL,
  PRIMARY KEY (`ma_sach`),
  FOREIGN KEY (`ma_danh_muc`) REFERENCES `danh_muc` (`ma_danh_muc`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tac_gia` (
  `ma_tac_gia` int NOT NULL AUTO_INCREMENT,
  `ten_tac_gia` text DEFAULT NULL,
  PRIMARY KEY (`ma_tac_gia`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sach_tac_gia` (
  `ma_sach` varchar(50) NOT NULL,
  `ma_tac_gia` int NOT NULL,
  PRIMARY KEY (`ma_sach`, `ma_tac_gia`),
  FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`) ON DELETE CASCADE,
  FOREIGN KEY (`ma_tac_gia`) REFERENCES `tac_gia` (`ma_tac_gia`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `doi_tuong` (
  `ma_doi_tuong` int NOT NULL AUTO_INCREMENT,
  `ten_doi_tuong` text DEFAULT NULL,
  PRIMARY KEY (`ma_doi_tuong`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bo_phan` (
  `ma_bo_phan` int NOT NULL AUTO_INCREMENT,
  `ten_bo_phan` text DEFAULT NULL,
  PRIMARY KEY (`ma_bo_phan`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `doc_gia` (
  `ma_doc_gia` varchar(50) NOT NULL,
  `mat_khau` varchar(255) DEFAULT NULL,
  `ho_ten` text DEFAULT NULL,
  `gioi_tinh` text DEFAULT NULL,
  `ngay_sinh` date DEFAULT NULL,
  `ma_doi_tuong` int DEFAULT NULL,
  `ma_bo_phan` int DEFAULT NULL,
  `ngay_dang_ky` date DEFAULT NULL,
  `ngay_het_han` date DEFAULT NULL,
  `email` text DEFAULT NULL,
  `dia_chi` text DEFAULT NULL,
  `dien_thoai` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ma_doc_gia`),
  FOREIGN KEY (`ma_doi_tuong`) REFERENCES `doi_tuong` (`ma_doi_tuong`),
  FOREIGN KEY (`ma_bo_phan`) REFERENCES `bo_phan` (`ma_bo_phan`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tien_phat` (
  `ma_tien_phat` int NOT NULL,
  `so_tien_phat` float NOT NULL,
  PRIMARY KEY (`ma_tien_phat`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sach_doc_gia` (
  `ma_sach` varchar(50) NOT NULL,
  `ma_doc_gia` varchar(50) NOT NULL,
  `so_luong`  int NOT NULL,
  `ngay_tra` datetime DEFAULT NULL,
  `ngay_muon` datetime DEFAULT NULL,
  `ngay_dat` datetime NOT NULL,
  `ma_tien_phat` int DEFAULT NULL,
  PRIMARY KEY (`ma_sach`, `ma_doc_gia`, `ngay_dat`),
  FOREIGN KEY (`ma_sach`) REFERENCES `sach` (`ma_sach`) ON DELETE CASCADE,
  FOREIGN KEY (`ma_doc_gia`) REFERENCES `doc_gia` (`ma_doc_gia`) ON DELETE CASCADE,
  FOREIGN KEY (`ma_tien_phat`) REFERENCES `tien_phat` (`ma_tien_phat`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `tien_phat` VALUES (1, 0.0), (2, 5000.0);
INSERT INTO `danh_muc` VALUES (1, 'Chưa có danh mục'), (2, 'Sách khoa học'), (3, 'Sách tiếng Anh');
INSERT INTO `tac_gia` VALUES (1, 'Tác giả 1'), (2, 'Tác giả 2'), (3, 'Tác giả 3');
INSERT INTO `doi_tuong` VALUES (1, 'Giảng viên'), (2, 'Viên chức'), (3, 'Sinh viên');
INSERT INTO `bo_phan` VALUES (1, 'Khoa công nghệ thông tin'), (2, 'Khoa công nghệ sinh học'), (3, 'Khoa xây dựng');

INSERT INTO `doc_gia`(`ma_doc_gia`, `mat_khau` ,`ho_ten`, `gioi_tinh`, `ma_doi_tuong`, `ma_bo_phan`, `ngay_dang_ky`, `ngay_het_han`, `email`) 
VALUES ('admin', '202cb962ac59075b964b07152d234b70', 'Nguyễn Văn A', 'Nam', 2, 1, '2017-11-18', '2020-12-31', 'user1@ou.edu.vn');
INSERT INTO `doc_gia`(`ma_doc_gia`, `mat_khau` ,`ho_ten`, `gioi_tinh`, `ma_doi_tuong`, `ma_bo_phan`, `ngay_dang_ky`, `ngay_het_han`, `email`) 
VALUES ('user', '202cb962ac59075b964b07152d234b70', 'Nguyễn Văn B', 'Nam', 1, 1, '2017-11-18', '2020-12-31', 'user1@ou.edu.vn');

INSERT INTO `sach` VALUES ('267f046d-defc-4621-8672-3cf62d56eee1', 'Kiểm thử phần mềm', 'Mô tả 1', 2022, 'TP Hồ Chí Minh', '2022-01-01', 'Kệ sách 1', 1);
INSERT INTO `sach` VALUES ('115dd543-06f0-417e-941a-1ec75fde5f64', 'Công nghệ phần mềm', 'Mô tả 2', 2022, 'TP Hồ Chí Minh', '2022-01-01', 'Kệ sách 1', 1);
INSERT INTO `sach` VALUES ('372c7092-0c50-4f1a-b9e9-827093a522c2', 'TEST1', 'Mô tả 3', 2022, 'TP Hồ Chí Minh', '2022-01-01', 'Kệ sách 1', 1);
INSERT INTO `sach` VALUES ('8e624f82-2281-42ac-b1bf-e84ae3747417', 'TEST2', 'Mô tả 4', 2022, 'TP Hồ Chí Minh', '2022-01-01', 'Kệ sách 1', 1);
INSERT INTO `sach_tac_gia` VALUES ('267f046d-defc-4621-8672-3cf62d56eee1', 1);
INSERT INTO `sach_tac_gia` VALUES ('267f046d-defc-4621-8672-3cf62d56eee1', 2);
INSERT INTO `sach_tac_gia` VALUES ('115dd543-06f0-417e-941a-1ec75fde5f64', 3);