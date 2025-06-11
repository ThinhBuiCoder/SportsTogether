CREATE DATABASE SportTogether_a;

USE SportTogether_a;

DROP TABLE IF EXISTS OrderItem;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Payments;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Suppliers;
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS Types;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
  id INT NOT NULL IDENTITY(1,1),
  firstname NVARCHAR(30) NOT NULL,
  lastname NVARCHAR(30) NOT NULL,
  email NVARCHAR(50) NOT NULL,
  avatar VARCHAR(200) NOT NULL,
  username VARCHAR(30) PRIMARY KEY NOT NULL,
  password VARCHAR(64) NOT NULL,
  address NVARCHAR(200) NOT NULL,
  phone NVARCHAR(15) NOT NULL,
  roleid INT NOT NULL,
  status BIT NOT NULL
);

CREATE TABLE Types (
  id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  name NVARCHAR(100)
);

CREATE TABLE Categories (
  categoryid INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  categoryname NVARCHAR(30),
  type_id INT FOREIGN KEY REFERENCES [dbo].Types(id)
);

CREATE TABLE Suppliers (
  supplierid INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  suppliername NVARCHAR(100),
  supplierimage VARCHAR(255) NOT NULL
);

CREATE TABLE Products (
  id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  productname NVARCHAR(MAX) NOT NULL,
  supplierid INT FOREIGN KEY REFERENCES [dbo].[Suppliers](supplierid) ON DELETE SET NULL ON UPDATE CASCADE,
  categoryid INT FOREIGN KEY REFERENCES [dbo].[Categories](categoryid) ON DELETE SET NULL ON UPDATE CASCADE,
  size VARCHAR(40) NOT NULL,
  stock INT NOT NULL,
  [description] NVARCHAR(MAX),
  [images] VARCHAR(255) NOT NULL,
  [colors] NVARCHAR(255) NOT NULL,
  releasedate DATE NOT NULL,
  discount FLOAT,
  unitSold INT,
  price MONEY NOT NULL,
  status BIT NOT NULL,
  typeid INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Types](id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payments (
  paymentid INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  payment_method NVARCHAR(30)
);

CREATE TABLE Orders (
  order_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  orderdate DATETIME,
  totalprice DECIMAL(10,2),
  paymentid INT NOT NULL FOREIGN KEY REFERENCES Payments(paymentid),
  username VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES Users([username]),
  status BIT NOT NULL
);

CREATE TABLE OrderItem (
  order_item_id INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
  quantity INT,
  price DECIMAL(10,2),
  product_id INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Products](id) ON DELETE CASCADE,
  order_id INT NOT NULL FOREIGN KEY REFERENCES [dbo].[Orders](order_id)
);

INSERT INTO Users  VALUES
(N'Phan Văn', N'Quyết', 'user@gmail.com', 'view/assets/home/img/users/user.jpg','user1', '12345', N'Quảng Trị', '0349940617', 2, 0),
(N'Bùi Mạnh', N'Thịnh', 'admin@gmail.com', 'view/assets/home/img/users/user.jpg', 'admin', '12345', N'Gia Lai', '0369184220', 1, 1),
(N'Phan Hoài', N'An', 'thanh@gmail.com', 'view/assets/home/img/users/1.jpg','an2004', '12345', N'Quảng Trị', '0847343246', 1, 1),
(N'Bé', N'Hạ', 'ha123@gmail.com', 'view/assets/home/img/users/user1.jpg','user2', '12345', N'Quảng Nam', '0385299310', 2, 1),
(N'Woang', N'Wuy', 'user3@gmail.com', 'view/assets/home/img/users/user3.jpg','user3', '12345', N'USA', '06868686868', 2, 1);


-- Thêm các môn thể thao vào bảng Types
INSERT INTO Types (name) VALUES
(N'Bóng đá'),
(N'Cầu lông'),
(N'Tenis'),
(N'Bóng rổ'),
(N'Pickle ball');

-- Thêm các sản phẩm vào bảng Categories theo các môn thể thao
INSERT INTO Categories (categoryname, type_id) 
VALUES
(N'Áo bóng đá', 1),  
(N'Giày bóng đá', 1),  
(N'Phụ kiện bóng đá', 1),  
(N'Áo cầu lông', 2),  
(N'Giày cầu lông', 2),  
(N'Phụ kiện cầu lông', 2),  
(N'Áo tenis', 3),  
(N'Giày tenis', 3),  
(N'Phụ kiện tenis', 3),  
(N'Áo bóng rổ', 4),  
(N'Giày bóng rổ', 4),  
(N'Phụ kiện bóng rổ', 4),  
(N'Áo pickle ball', 5),  
(N'Giày pickle ball', 5),  
(N'Phụ kiện pickle ball', 5);  

-- Thêm các nhà cung cấp cho các môn thể thao
INSERT INTO Suppliers (suppliername, supplierimage) VALUES
(N'Bóng đá', 'view/assets/home/img/suppliers/1.jpg'),
(N'Cầu lông', 'view/assets/home/img/suppliers/2.jpg'),
(N'Tenis', 'view/assets/home/img/suppliers/3.jpg'),
(N'Bóng rổ', 'view/assets/home/img/suppliers/4.jpg'),
(N'Pickle ball', 'view/assets/home/img/suppliers/5.jpg');







-- Bóng đá
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Áo bóng đá Nike LFC', 1, 1, 'M,L', 30, N'Chất liệu cotton mềm, nhẹ, giúp bạn đạt hiệu suất cao nhất trên sân.',
'view/assets/home/img/products/football/1.jpg', N'Đỏ', '2023-11-01', 0, 21, 199.00, 1, 1),
(N'Áo Đá Bóng Nike Paris Saint-Germain', 1, 1, 'M,L', 25, N'Thiết kế năng động, thấm hút mồ hôi tốt, giúp bạn thoải mái trong mỗi trận đấu.',
'view/assets/home/img/products/football/2.jpg', N'Trắng', '2024-03-01', 0, 20, 999.00, 1, 1),
(N'Áo Đá Bóng Nike Barcelona', 1, 1, 'M,L', 20, N'Thiết kế mạnh mẽ, thoáng khí, phù hợp với những người chơi bóng đá chuyên nghiệp và đam mê thể thao.',
'view/assets/home/img/products/football/3.jpg', N'Xanh', '2024-04-01', 0, 18, 899.00, 1, 1),
(N'Áo Đá Bóng Adidas Manchester United', 1, 1, 'S,M,L', 18, N'Chất liệu siêu nhẹ và thấm mồ hôi, giúp bạn đạt hiệu suất cao nhất trên sân.',
'view/assets/home/img/products/football/4.jpg', N'Trắng', '2024-06-01', 0.3, 20, 1199.00, 1, 1),
(N'Áo Đá Bóng Adidas Arsenal', 1, 1, 'M,L', 22, N'Thiết kế năng động, phù hợp với các trận đấu căng thẳng, giúp bạn cảm thấy tự tin khi thi đấu.',
'view/assets/home/img/products/football/5.jpg', N'Xanh', '2025-08-01', 0.35, 19, 1099.00, 1, 1);

-- Giày bóng đá
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES
(N'Giày bóng đá Nike Tiempo Legend 8', 1, 2, '41,42,43,44', 25, N'Công nghệ đế giày chuyên dụng giúp tăng cường độ bám và hỗ trợ tối đa trong các pha chạy nhanh và di chuyển trên sân.',
'view/assets/home/img/products/football/6.jpg', N'Vàng', '2023-02-01', 0.45, 22, 2199.00, 1, 1),
(N'Giày bóng đá Adidas Predator Edge', 1, 2, '41,42,43,44', 28, N'Công nghệ đế giày siêu bền, hỗ trợ tăng tốc nhanh chóng và ổn định trong suốt trận đấu.',
'view/assets/home/img/products/football/7.jpg', N'Hồng', '2023-05-01', 0.4, 24, 2499.00, 1, 1),
(N'Giày bóng đá Adidas Future Z', 1, 2, '40,41,42,43', 30, N'Công nghệ đế giày hỗ trợ tối ưu cho các pha bóng đá nhanh và mạnh mẽ.',
'view/assets/home/img/products/football/8.jpg', N'Trắng', '2023-06-01', 0.45, 27, 1899.00, 1, 1),
(N'Giày bóng đá Nike Phantom GT', 1, 2, '41,42,43,44', 20, N'Công nghệ đế giày linh hoạt, giúp bạn có thể xử lý bóng chính xác và nhanh chóng trong các pha thi đấu.',
'view/assets/home/img/products/football/9.jpg', N'Xanh', '2023-07-01', 0.4, 22, 2299.00, 1, 1),
(N'Giày bóng đá Nike Morelia Neo', 1, 2, '40,41,42', 18, N'Công nghệ đế giày đặc biệt giúp hỗ trợ di chuyển nhanh và chính xác trong suốt trận đấu.',
'view/assets/home/img/products/football/10.jpg', N'Xanh lá', '2023-08-01', 0.38, 20, 2099.00, 1, 1);

-- Phụ kiện bóng đá
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N' Body Sculpture Knee Support', 1, 3, 'Free', 30, N'Dụng Cụ Hỗ Trợ Đầu Gối Body Sculpture Knee Support With Terry Cloth.',
'view/assets/home/img/products/football/11.jpg', N'Đen', '2023-05-01', 0.25, 22, 399.00, 1, 1),
(N'Body Sculpture Ankle Support', 1, 3, 'Free', 25, N'Dụng Cụ Hỗ Trợ Mắt Cá Chân Body Sculpture Ankle Support With Terry Cloth.',
'view/assets/home/img/products/football/12.jpg', N'Đen', '2023-06-01', 0.2, 20, 299.00, 1, 1),
(N'Body Sculpture Patella Strap', 1, 3, 'Free', 28, N'Dụng Cụ Hỗ Trợ Khớp Gối Body Sculpture Patella Strap.',
'view/assets/home/img/products/football/13.jpg', N'Đen', '2023-07-01', 0.3, 23, 249.00, 1, 1),
(N'Body Sculpture Wrist Support Open Patella ', 1, 3, 'Free', 32, N'Dụng Cụ Hỗ Trợ Cổ Tay Body Sculpture Wrist Support Open Patella With Terry Cloth.',
'view/assets/home/img/products/football/14.jpg', N'Đen', '2023-08-01', 0.35, 28, 349.00, 1, 1),
(N'Venum Challenger Standup', 1, 3, 'Free', 35, N'Giáp Bảo Vệ Chân Venum Challenger Standup.',
'view/assets/home/img/products/football/15.jpg', N'Đen', '2023-09-01', 0.3, 30, 499.00, 1, 1);

-- Cầu lông
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES
(N'Áo cầu lông Yonex A518', 2, 4, 'S,M,L', 30, N'Áo cầu lông Yonex A518 với chất liệu thấm hút mồ hôi, thiết kế nhẹ nhàng, thoải mái, giúp người chơi vận động dễ dàng và tự tin trong mỗi pha cầu.',
'view/assets/home/img/products/badminton/1.jpg', N'Trắng', '2022-05-01', 0.3, 18, 450.00, 1, 2),
(N'Áo cầu lông Lining A386', 2, 4, 'M,L', 28, N'Áo cầu lông Lining A386 thiết kế chuyên nghiệp, thấm hút mồ hôi hiệu quả và mang lại sự thoải mái tối đa cho người chơi.',
'view/assets/home/img/products/badminton/2.jpg', N'Xanh', '2023-06-01', 0.25, 20, 399.00, 1, 2),
(N'Áo Cầu Lông Lining A537', 2, 4, 'S,M,L', 22, N'Áo Cầu Lông Lining A537 với chất liệu vải cao cấp, giúp người chơi thoải mái di chuyển và đạt hiệu suất tốt nhất.',
'view/assets/home/img/products/badminton/3.jpg', N'Đen', '2023-08-01', 0.35, 18, 399.00, 1, 2),
(N'Áo Cầu Lông Lining A320', 2, 4, 'M,L', 20, N'Áo Cầu Lông Lining A320 được làm từ chất liệu thoáng khí, giúp bạn duy trì hiệu suất cao trong suốt trận đấu.',
'view/assets/home/img/products/badminton/4.jpg', N'Hồng', '2023-09-01', 0.4, 17, 499.00, 1, 2),
(N'Áo Cầu Lông Lining VM1066', 2, 4, 'S,M,L', 18, N'Áo Cầu Lông Lining VM1066 thiết kế gọn gàng, mang đến sự thoải mái và hỗ trợ cho người chơi trong suốt trận đấu.',
'view/assets/home/img/products/badminton/5.jpg', N'Cam', '2023-10-01', 0.3, 15, 450.00, 1, 2);

-- Giày cầu lông
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Giày cầu lông Yonex Eclipsion Z3', 2, 5, '40,41,42', 28, N'Giày cầu lông Yonex Eclipsion Z3 với đế giày đặc biệt giúp giảm sốc và tạo độ bám tốt cho các vận động viên cầu lông.',
'view/assets/home/img/products/badminton/6.jpg', N'Xanh', '2023-06-01', 0.35, 22, 1499.00, 1, 2),
(N'Giày cầu lông Yonex SHB 65X4', 2, 5, '41,42,43,44', 30, N'Giày cầu lông Yonex SHB 65X4 giúp tăng cường độ ổn định và tốc độ di chuyển, phù hợp với các vận động viên chuyên nghiệp và người chơi phong trào.',
'view/assets/home/img/products/badminton/7.jpg', N'Trắng', '2023-07-01', 0.4, 25, 1799.00, 1, 2),
(N'Giày cầu lông Yonex Aerus X2', 2, 5, '40,41,42', 28, N'Giày cầu lông Yonex Aerus X2 với công nghệ đế giày giúp tăng cường độ bám sân và bảo vệ chân người chơi.',
'view/assets/home/img/products/badminton/8.jpg', N'Xanh', '2023-08-01', 0.45, 22, 1899.00, 1, 2),
(N'Giày Cầu Lông Yonex Aerus X', 2, 5, '41,42,43,44', 25, N'Giày Cầu Lông Yonex Aerus X thiết kế chuyên nghiệp giúp bảo vệ các khớp và tạo sự thoải mái tối đa trong suốt trận đấu.',
'view/assets/home/img/products/badminton/9.jpg', N'Xanh', '2023-09-01', 0.4, 20, 1999.00, 1, 2),
(N'Giày cầu lông Yonex Strider Flow Wide', 2, 5, '40,41,42', 30, N'Giày cầu lông Yonex Strider Flow Wide với thiết kế nhẹ và thoải mái, giúp người chơi dễ dàng di chuyển nhanh nhẹn và chính xác.',
'view/assets/home/img/products/badminton/10.jpg', N'Xanh', '2023-10-01', 0.35, 28, 1799.00, 1, 2);




-- Phụ kiện cầu lông
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Balo Cầu Lông Yonex BAG524B0812Z', 2, 6, 'Free', 28, N'Balo cầu lông Yonex BAG524B0812Z là mẫu balo thời thượng, đẳng cấp không chỉ dành riêng cho cầu lông mà ngay cả đi du lịch cũng rất tiện lợi với những chuyến du lịch ngắn ngày.',
'view/assets/home/img/products/badminton/11.jpg', N'Đen', '2023-06-01', 0.25, 22, 249.00, 1, 2),
(N'Túi đựng giày đa năng Kamito Style Pro KMTUI230323', 2, 6, 'Free', 30, N'Túi đựng giày đa năng Kamito Style Pro KMTUI230323 chính hãng có thiết kế nhỏ gọn, dễ dàng mang và di chuyển, với sức chức gồm 1 đôi giày, 1 bộ quần áo và các vật dụng nhỏ gọn khác đi kèm vô cùng tiện lợi.',
'view/assets/home/img/products/badminton/12.jpg', N'Đen', '2023-07-01', 0.3, 25, 399.00, 1, 2),
(N'Vớ cầu lông Taro TR021-02', 2, 6, 'Free', 32, N'Vớ cầu lông là một phần quan trọng của trang phục cho người chơi môn thể thao cầu lông, không chỉ giúp bảo vệ bàn chân mà còn hỗ trợ giảm độ ma sát và tạo sự thoải mái trong quá trình tập luyện hay thi đấu.',
'view/assets/home/img/products/badminton/13.jpg', N'Đen', '2023-08-01', 0.35, 28, 299.00, 1, 2),
(N'Vợt cầu lông Yonex Arcsaber 2 Feel', 2, 6, 'Free', 35, N'Vợt cầu lông Yonex Arcsaber 2 Feel là cây vợt thuộc phân khúc tầm trung, được thiết kế với điểm cân bằng ở mức cân bằng, hướng tới các người chơi có lối đánh công thủ toàn diện, linh hoạt.',
'view/assets/home/img/products/badminton/14.jpg', N'Đen', '2023-09-01', 0.3, 30, 399.00, 1, 2),
(N'Vợt Cầu Lông Mizuno Acrospeed 8', 2, 6, 'Free', 30, N'Vợt Cầu Lông Mizuno Acrospeed 8 gồm vợt và dây vợt giúp người chơi nâng cao hiệu quả trong từng cú đánh.',
'view/assets/home/img/products/badminton/15.jpg', N'Trắng', '2023-10-01', 0.25, 28, 349.00, 1, 2);



-- Tenis
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Áo Tennis Nadal Nike COURT Dry RAFA Crew', 3, 7, 'S,M,L', 25, N'Áo tenis với công nghệ Dri-FIT giúp thấm hút mồ hôi, giữ cho người chơi luôn khô thoáng trong suốt trận đấu, thiết kế thể thao thoải mái.',
'view/assets/home/img/products/tennis/1.jpg', N'Trắng', '2022-06-01', 0.4, 20, 799.00, 1, 3),
(N'Áo Tennis Nike Court Advantage Polo - Tropical Twist', 3, 7, 'M,L', 30, N'Áo tenis với thiết kế đơn giản, dễ chịu, giúp người chơi có thể vận động thoải mái và linh hoạt.',
'view/assets/home/img/products/tennis/2.jpg', N'Xanh lá', '2023-07-01', 0.35, 25, 899.00, 1, 3),
(N'Áo Tennis Nike Court RF Essentials T-Shirt', 3, 7, 'S,M,L', 28, N'Áo tenis với chất liệu vải co giãn tốt, mang lại sự thoải mái và phong cách cho người chơi.',
'view/assets/home/img/products/tennis/3.jpg', N'Xanh dương', '2023-08-01', 0.35, 24, 999.00, 1, 3),
(N'Áo tenis Lacoste Regular Fit Striped Cotton', 3, 7, 'M,L', 22, N'Áo tenis được thiết kế nhẹ nhàng và thấm hút mồ hôi, giúp người chơi có thể duy trì hiệu suất trong suốt trận đấu.',
'view/assets/home/img/products/tennis/4.jpg', N'Trắng', '2023-09-01', 0.4, 20, 799.00, 1, 3),
(N'Áo Lacoste SPORT Contrast', 3, 7, 'S,M,L', 18, N'Áo tenis với công nghệ thấm hút mồ hôi tốt, mang lại cảm giác nhẹ nhàng và thoải mái cho người chơi.',
'view/assets/home/img/products/tennis/5.jpg', N'Xanh', '2023-10-01', 0.3, 15, 850.00, 1, 3);
-- Giày tenis
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Giày tenis Asics Gel Challenger 14', 3, 8, '41,42,43,44', 30, N'Giày tenis Asics Gel Challenger giúp tăng cường độ ổn định và tốc độ di chuyển, phù hợp với các vận động viên chuyên nghiệp và người chơi phong trào.',
'view/assets/home/img/products/tennis/6.jpg', N'Đen', '2023-02-01', 0.45, 25, 1499.00, 1, 3),
(N'Giày tenis Asics COURT FF 3', 3, 8, '41,42,43,44', 28, N'Giày tenis Asics COURT FF 3 với thiết kế đế giày nhẹ và hỗ trợ tối ưu cho các pha di chuyển nhanh và chính xác.',
'view/assets/home/img/products/tennis/7.jpg', N'Trắng', '2023-04-01', 0.4, 22, 1799.00, 1, 3),
(N'Giày tenis Asics COURT FF 3 Novak Djokovic', 3, 8, '40,41,42,43', 25, N'Giày tenis Asics COURT FF 3 Novak Djokovic giúp người chơi có độ bám tối ưu và hỗ trợ trong các pha di chuyển và đánh bóng.',
'view/assets/home/img/products/tennis/8.jpg', N'Xanh dương', '2023-05-01', 0.45, 20, 2199.00, 1, 3),
(N'Giày tenis GEL CHALLENGER 13', 3, 8, '40,41,42,43', 28, N'Giày tenis GEL CHALLENGER 13 thiết kế chuyên nghiệp, giúp người chơi ổn định trong suốt trận đấu và tăng hiệu quả di chuyển.',
'view/assets/home/img/products/tennis/9.jpg', N'Xanh dương', '2023-06-01', 0.5, 24, 1999.00, 1, 3),
(N'Giày tenis Asics COURT FF 3 NOVAK', 3, 8, '40,41,42', 26, N'Giày tenis Asics COURT FF 3 NOVAK với đế giày hỗ trợ tốt trong các pha di chuyển nhanh và khả năng tăng tốc trong khi chơi.',
'view/assets/home/img/products/tennis/10.jpg', N'Đỏ', '2023-07-01', 0.45, 30, 1799.00, 1, 3);
-- Phụ kiện tenis
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Balo Tennis Babolat PURE DRIVE', 3, 9, 'Free', 28, N'Thiết kế chất liệu mới, cao cấp theo truyền thống, chứa 2 vợt , giày và quần áo đủ cho 1 buổi chơi.',
'view/assets/home/img/products/tennis/11.jpg', N'Xanh', '2023-06-01', 0.25, 22, 249.00, 1, 3),
(N'Balo Tennis Pickleball WILSON RF Backpack', 3, 9, 'Free', 30, N'Phụ kiện tenis Yonex bao gồm các loại vợt và dây vợt giúp bạn duy trì khả năng kiểm soát bóng tối ưu trong suốt trận đấu.',
'view/assets/home/img/products/tennis/12.jpg', N'Đen', '2023-07-01', 0.3, 25, 399.00, 1, 3),
(N'Vợt Tennis Head RADICAL TEAM', 3, 9, 'Free', 25, N'RADICAL Lite trợ lúc ít, nhẹ hơn, khung vợt mềm hơn, mặt vợt to hơn một chút (102 inch) độ vung vợt linh hoạt, sweet spot lớn , phù hợp cho rất nhiều bạn thể hình nhỏ và các bạn nữ.',
'view/assets/home/img/products/tennis/13.jpg', N'Đỏ', '2023-08-01', 0.35, 28, 299.00, 1, 3),
(N'Vợt Tennis Babolat PURE DRIVE LITE', 3, 9, 'Free', 35, N'PURE DRIVE - Một trong những dòng vợt huyền thoại mà Babolat luôn chú trọng và là một trong những khung vợt bán chạy nhất ở Mỹ và Châu Âu.',
'view/assets/home/img/products/tennis/14.jpg', N'Xanh', '2023-09-01', 0.3, 30, 399.00, 1, 3),
(N'Babolat Addiction 17', 3, 9, 'Free', 32, N'Dây Mềm dạng Wrap - êm, lực + bám bóng tạo xoáy.',
'view/assets/home/img/products/tennis/15.jpg', N'Trắng', '2023-10-01', 0.3, 28, 249.00, 1, 3);



-- Bóng rổ
-- Áo bóng rổ
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Áo bóng rổ Nike NBA Swingman', 4, 10, 'M,L,XL', 30, N'Áo bóng rổ với thiết kế theo phong cách NBA, thoáng khí và cực kỳ thoải mái, giúp bạn dễ dàng di chuyển trên sân đấu.',
'view/assets/home/img/products/basketball/1.jpg', N'Trắng', '2022-08-01', 0.3, 20, 1299.00, 1, 4),
(N'Áo bóng rổ Nike Harden', 4, 10, 'M,L', 25, N'Áo bóng rổ thiết kế đột phá, hỗ trợ di chuyển nhanh và linh hoạt cho người chơi.',
'view/assets/home/img/products/basketball/2.jpg', N'Xanh', '2023-03-01', 0.35, 22, 1599.00, 1, 4),
(N'Áo bóng rổ Nike LeBron James', 4, 10, 'S,M,L', 28, N'Áo bóng rổ thiết kế đặc biệt, giúp người chơi thoải mái và tập trung vào từng pha bóng.',
'view/assets/home/img/products/basketball/3.jpg', N'Vàng', '2023-05-01', 0.4, 24, 1699.00, 1, 4),
(N'Áo bóng rổ Nike Clyde', 4, 10, 'M,L', 22, N'Áo bóng rổ được làm từ chất liệu thoáng khí, giúp bạn duy trì hiệu suất cao trong suốt trận đấu.',
'view/assets/home/img/products/basketball/4.jpg', N'Đen', '2023-06-01', 0.35, 25, 1299.00, 1, 4),
(N'Áo bóng rổ Nike Under Armour Curry', 4, 10, 'S,M,L', 20, N'Áo bóng rổ thiết kế chuyên dụng, giúp bạn thoải mái di chuyển và tăng cường sức mạnh trong mỗi cú ném.',
'view/assets/home/img/products/basketball/5.jpg', N'Xanh', '2023-07-01', 0.4, 18, 1399.00, 1, 4);
-- Giày bóng rổ
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES
(N'ADIDAS AE 1 LOW', 4, 11, '41,42,43,44', 40, N'Giày bóng rổ với thiết kế độc đáo, giúp người chơi có được sự hỗ trợ tối đa trong các cú nhảy và di chuyển nhanh.',
'view/assets/home/img/products/basketball/6.jpg', N'Đen', '2023-06-01', 0.45, 25, 2299.00, 1, 4),
(N'ADIDAS AE 2 LOW', 4, 11, '40,41,42,43', 35, N'Giày bóng rổ với đế giày linh hoạt, giúp người chơi chuyển động nhanh chóng và dễ dàng.',
'view/assets/home/img/products/basketball/7.jpg', N'Xanh', '2023-08-01', 0.4, 30, 1799.00, 1, 4),
(N'JORDAN TATUM 1', 4, 11, '41,42,43', 25, N'Giày bóng rổ với thiết kế bền bỉ và hỗ trợ tốt cho mọi bước di chuyển trên sân.',
'view/assets/home/img/products/basketball/8.jpg', N'Vàng', '2023-09-01', 0.45, 28, 2199.00, 1, 4),
(N'NIKE AIR MAX IMPACT 3', 4, 11, '40,41,42', 22, N'Giày bóng rổ với đế giày giúp tối ưu hóa độ bám sân, giúp người chơi tự do trong các pha di chuyển và ném bóng.',
'view/assets/home/img/products/basketball/9.jpg', N'Xám', '2023-10-01', 0.5, 20, 2399.00, 1, 4),
(N'ADIDAS AE Z11 LOW', 4, 11, '42,43,44', 40, N'Giày bóng rổ thiết kế với đệm êm ái và khả năng tăng tốc tuyệt vời.',
'view/assets/home/img/products/basketball/10.jpg', N'Trắng', '2023-11-01', 0.45, 30, 2599.00, 1, 4);
-- Phụ kiện bóng rổ
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES
(N'VỚ NIKE ELITE HIGH', 4, 12, 'Free', 25, N'Phù hợp mang chơi thể thao hoặc mang hàng ngày.',
'view/assets/home/img/products/basketball/11.jpg', N'Đen', '2023-07-01', 0.3, 20, 399.00, 1, 4),
(N'BANH LI-NING GRIP', 4, 12, 'Free', 30, N'Chất liệu: Da PU.',
'view/assets/home/img/products/basketball/12.jpg', N'Cam', '2023-08-01', 0.35, 28, 499.00, 1, 4),
(N'BALO JORDAN MINI', 4, 12, 'Free', 32, N'Kích thước: 40x30x15 (Dài x Ngang x Rộng) (cm). Được chia thành nhiều ngăn thuận tiện cho việc chứa đồ.',
'view/assets/home/img/products/basketball/13.jpg', N'Đen', '2023-09-01', 0.3, 25, 299.00, 1, 4),
(N'BALO NIKE UTILITY ELITE', 4, 12, 'Free', 30, N'Kích thước (Cao x Ngang x Rộng): 48 x 30 x 19 (cm). Có thể chứa banh size 7. Có quai đeo đệm Air, êm ái hơn khi mang.',
'view/assets/home/img/products/basketball/14.jpg', N'Đen', '2023-10-01', 0.3, 20, 399.00, 1, 4),
(N'BALO NIKE AIR ELITE 2.0', 4, 12, 'Free', 35, N'Phụ kiện bóng rổ Wilson giúp bảo vệ người chơi trong suốt trận đấu, bao gồm các miếng bảo vệ tay và đầu gối.',
'view/assets/home/img/products/basketball/15.jpg', N'Cam', '2023-11-01', 0.35, 30, 499.00, 1, 4);




-- Pickle Ball
-- Áo pickle ball
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES 
(N'Áo pickle ball Adidas Court', 5, 13, 'S,M,L', 20, N'Áo pickle ball thiết kế đơn giản, thấm hút mồ hôi, giúp người chơi có thể vận động thoải mái trong suốt trận đấu.',
'view/assets/home/img/products/pickleball/1.jpg', N'Xanh dương', '2023-07-01', 0.25, 18, 599.00, 1, 5),
(N'Áo pickle ball Adidas CLUB TEE', 5, 13, 'M,L', 25, N'Áo pickle ball với chất liệu nhẹ nhàng, hỗ trợ tối đa cho các pha di chuyển nhanh và mạnh mẽ trên sân.',
'view/assets/home/img/products/pickleball/2.jpg', N'Trắng', '2023-08-01', 0.3, 20, 799.00, 1, 5),
(N'Áo pickle ball FREELIFT Melbourne ', 5, 13, 'S,M,L', 28, N'Áo pickle ball với thiết kế thoải mái, giúp người chơi duy trì hiệu suất tối ưu trong suốt trận đấu.',
'view/assets/home/img/products/pickleball/3.jpg', N'Trắng', '2023-09-01', 0.35, 22, 899.00, 1, 5),
(N'Áo pickle ball CLUB TENNIS 3-STRIPES TEE', 5, 13, 'M,L', 30, N'Áo pickle ball thiết kế tinh tế, giúp người chơi có thể tự do di chuyển trong các pha bóng quyết liệt.',
'view/assets/home/img/products/pickleball/4.jpg', N'Trắng', '2023-10-01', 0.4, 25, 999.00, 1, 5),
(N'Áo pickle ball CLUB 3-STRIPES POLO', 5, 13, 'S,M,L', 32, N'Áo pickle ball với chất liệu thoáng khí, giúp người chơi không cảm thấy bí bách khi vận động mạnh.',
'view/assets/home/img/products/pickleball/5.jpg', N'Xanh', '2023-11-01', 0.3, 28, 1099.00, 1, 5);
-- Giày Pickle ball
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES
(N'Giày Pickleball Adidas BARICADE', 5, 14, '40,41,42', 25, N'Giày pickle ball với đế giày đặc biệt, giúp người chơi có độ bám cao trên sân và bảo vệ bàn chân trong suốt trận đấu.',
'view/assets/home/img/products/pickleball/6.jpg', N'Đen', '2023-09-01', 0.35, 22, 1899.00, 1, 5),
(N'Giày pickle Adidas BARICADE MIAPULCO', 5, 14, '41,42,43', 28, N'Giày pickle ball với thiết kế nhẹ và hỗ trợ tối ưu trong các pha di chuyển nhanh.',
'view/assets/home/img/products/pickleball/7.jpg', N'Vàng', '2023-10-01', 0.4, 25, 2099.00, 1, 5),
(N'Giày pickle ball Adidas ADIZERO UBERSONIC', 5, 14, '40,41,42,43', 30, N'Giày pickle ball giúp tăng cường độ ổn định và bảo vệ người chơi trong các pha di chuyển nhanh và mạnh.',
'view/assets/home/img/products/pickleball/8.jpg', N'Đỏ', '2023-11-01', 0.4, 27, 1999.00, 1, 5),
(N'Giày pickle ball Asics GEL-RESOLUTION', 5, 14, '40,41,42,43', 22, N'Giày pickle ball thiết kế chuyên dụng, giúp bạn dễ dàng di chuyển và thoải mái trong mỗi pha đấu.',
'view/assets/home/img/products/pickleball/9.jpg', N'Xanh lá', '2023-12-01', 0.45, 30, 2099.00, 1, 5),
(N'Giày pickle ball Asics', 5, 14, '40,41,42,43', 22, N'Giày pickle ball thiết kế chuyên dụng, giúp bạn dễ dàng di chuyển và thoải mái trong mỗi pha đấu.',
'view/assets/home/img/products/pickleball/10.jpg', N'Xanh lá', '2023-12-01', 0.45, 30, 2099.00, 1, 5);

-- Phụ kiện Pickle ball
INSERT INTO Products (productname, supplierid, categoryid, size, stock, [description], images, colors, releasedate, discount, unitSold, price, status, typeid) 
VALUES
(N'Vợt Pickleball BABOLAT BALLR', 5, 15, 'Free', 30, N'Độ dày : 16mm / Công nghệ RPM Grit : nhám tạo xoáy và kiểm soát bóng / Cán dài 5.5 inch hỗ trợ các cú trái tay bằng cả hai tay / Hình dạng mặt vợt tròn làm tăng độ linh hoạt và lực đánh nhanh hơn.',
'view/assets/home/img/products/pickleball/11.jpg', N'Xanh', '2023-10-01', 0.3, 25, 299.00, 1, 5),
(N'Vợt Pickleball JOOLA Ben Johns Perseus', 5, 15, 'Free', 25, N'Bề mặt vợt: Charged Carbon. Cấu trúc lõi: Reactive Honeycomb Polymer. Trọng lượng: 7.8 oz (221.1 gram)',
'view/assets/home/img/products/pickleball/12.jpg', N'Xanh', '2023-08-01', 0.3, 20, 399.00, 1, 5),
(N'Vợt Pickleball Joola Tyson Mcguffin Magnus', 5, 15, 'Free', 30, N'Bề mặt vợt: Charged Carbon. Cấu trúc lõi: Reactive Honeycomb Polymer. Cân nặng trung bình: 230g.',
'view/assets/home/img/products/pickleball/13.jpg', N'Đỏ', '2023-07-01', 0.3, 25, 499.00, 1, 5),
(N'Rổ đựng banh có bánh xe chứa 50 trái banh', 5, 15, 'Free', 35, N'Rổ đựng banh cơ động, quai cầm tiện lợi không cần khom lưng nhặt bóng.Thiết kế mới có banh xe có thể kéo nhẹ nhàng trên sân.',
'view/assets/home/img/products/pickleball/14.jpg', N'Đen', '2023-06-01', 0.35, 30, 399.00, 1, 5),
(N'Cây đẩy hút nước sân PADEL', 5, 15, 'Free', 28, N'Cây đẩy nước Pickleball thiết kế đặt biệt dạng roll, hút và vắt nước.',
'view/assets/home/img/products/pickleball/15.jpg', N'Xanh', '2023-09-01', 0.25, 22, 249.00, 1, 5);


INSERT INTO Payments VALUES 
(N'Tiền mặt'),
(N'Credit Card')


INSERT INTO [Orders] VALUES 
('2024-03-10 12:30:00', 2241.00, 1, 'an2004', 1),
('2024-05-19 11:30:00', 1129.00, 1,  'an2004', 1),
('2023-06-10 12:30:00', 1192.00, 1, 'user2', 0),
('2025-02-20 11:19:00', 4396.00, 1, 'an2004', 1),
('2019-01-19 11:30:00', 2100.00, 1, 'user2', 1),
('2024-01-19 11:30:00', 2200.00, 1, 'user2', 1),
('2021-10-20 11:19:00', 3250.00, 1, 'an2004', 1),
('2021-03-10 12:30:00', 500.00, 1, 'user2', 1),
('2024-04-20 11:19:00', 824.00, 1, 'user2', 1),
('2024-05-19 11:30:00', 2200.00, 1, 'an2004', 1),
('2023-06-10 12:30:00', 1190.00, 1, 'an2004', 1),
('2022-07-20 11:19:00', 1040.00, 1, 'user2', 1),
('2021-08-19 11:30:00', 3000.00, 1, 'an2004', 1),
('2024-09-10 12:30:00', 650.00, 1, 'user2', 1),
('2020-11-20 11:19:00', 425.00, 1, 'an2004', 1),
('2019-12-19 11:30:00', 1399.00, 1, 'user2', 1),
('2023-02-10 04:30:00', 1290.00, 1, 'an2004', 0),
('2023-01-10 01:50:41', 700.00, 1, 'an2004', 0),
('2022-11-10 12:10:16', 500.00, 1, 'user2', 1),
('2021-01-10 11:03:50', 699.00, 1, 'an2004', 0),
('2024-02-10 10:15:00', 425.00, 1, 'an2004', 0),
('2023-02-10 12:30:00', 1053.00, 1, 'an2004', 0),
('2020-02-10 12:30:00', 875.0, 1, 'an2004', 1),
('2019-02-14 12:30:00', 875.00, 1, 'admin', 1);


INSERT INTO OrderItem VALUES 
(1, 249.000, 1, 1),
(8, 249.000, 1, 1),
(4, 399.0, 4, 2),
(2, 699.0, 7, 2),
(3, 179.0, 2, 2),
(4, 249.0, 1, 2),
(5, 179.0, 2, 2),
(1, 325.0, 13, 3),
(1, 950.0, 15, 3),
(1, 179.0, 2, 4),
(7, 179.0, 2, 4),
(6, 315.0, 5, 5),
(9, 179.0, 2, 5),
(5, 179.0, 2, 5),
(2, 1050.0, 17, 6),
(2, 1100.0, 11, 7),
(3, 345.0, 19, 8),
(3, 550.0, 20, 8),
(5, 650.0, 23, 9),
(1, 500.0, 22, 10),
(2, 412.0, 22, 11),
(1, 2200.0, 14, 12),
(1, 875.0, 6, 13),
(1, 315.0, 5, 13),
(1, 1040.0, 28, 14),
(2, 1500.0, 38, 15),
(1, 650.0, 36, 16),
(1, 425.0, 34, 17),
(1, 1399.0, 33, 18),
(1, 1290.0, 29, 19),
(1, 700.0, 26, 20),
(1, 500.0, 22, 21),
(1, 699.0, 7, 22),
(1, 425.0, 8, 23),
(1, 1053.0, 10, 24),
(1, 875.0, 6, 20),
(1, 875.0, 6, 24);


