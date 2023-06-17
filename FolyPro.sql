CREATE DATABASE EduSys;
go
USE EduSys;
GO
--tạo bảng chuyên đề
CREATE TABLE ChuyenDe(
	MaCD NCHAR(5) NOT NULL PRIMARY KEY,
	TenCD NVARCHAR(50) NOT NULL,
	HocPhi FLOAT NOT NULL,
	ThoiLuong INT NOT NULL,
	Hinh NVARCHAR(50) NOT NULL,
	MoTa NVARCHAR(255) NOT NULL
);
--tạo bảng nhân viên
CREATE TABLE NhanVien(
	MaNV NVARCHAR(20) PRIMARY KEY NOT NULL,
	MatKhau NVARCHAR(200) NOT NULL,
	HoTen NVARCHAR(50) NOT NULL,
	VaiTro BIT DEFAULT 0
);
--tạo bảng khóa học
CREATE TABLE KhoaHoc(
	MaKH INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	MaCD NCHAR(5) NOT NULL,
	HocPhi FLOAT NOT NULL,
	ThoiLuong INT NOT NULL,
	NgayKG DATE NOT NULL,
	GhiChu NVARCHAR(50) NOT NULL,
	MaNV NVARCHAR(20) NOT NULL,
	NgayTao DATE DEFAULT GETDATE(),
	FOREIGN KEY (MaCD) REFERENCES dbo.ChuyenDe(MaCD) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien(MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
);
--tạo bảng người học
CREATE TABLE NguoiHoc(
	MaNH NCHAR(7) PRIMARY KEY NOT NULL,
	HoTen NVARCHAR(50) NOT NULL,
	GioiTinh BIT DEFAULT 1,
	NgaySinh DATE NOT NULL,
	DienThoai NVARCHAR(24) NOT NULL,
	Email NVARCHAR(50) NOT NULL,
	GhiChu NVARCHAR(255) NULL,
	MaNV NVARCHAR(20) NOT NULL,
	NgayDK DATE DEFAULT GETDATE(),
	FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
);
--tạo bảng học viên
CREATE TABLE HocVien(
	MaHV INT IDENTITY(1,1) PRIMARY KEY,
	MaKH INT NOT NULL,
	MaNH NCHAR(7) NOT NULL,
	Diem FLOAT  DEFAULT -1,
	FOREIGN KEY (MaNH) REFERENCES dbo.NguoiHoc(MaNH) ON DELETE NO ACTION ON UPDATE CASCADE
	,
	FOREIGN KEY (MaKH) REFERENCES dbo.KhoaHoc (MaKH) ON DELETE CASCADE ON UPDATE NO ACTION  
);

--Thêm dữ liệu vào bảng chuyên đề
INSERT INTO dbo.ChuyenDe VALUES(   N'CD001', N'Cở sở dữ liệu', 500, 90, N'bg_5.jpg', N'Xây dựng cơ sở dữ liệu' )
INSERT INTO dbo.ChuyenDe VALUES(   N'CD002', N'Java 1', 500, 90, N'bg_5.jpg', N'Lập trình java 1' )
INSERT INTO dbo.ChuyenDe VALUES(   N'CD003', N'Javascript', 500, 90, N'bg_5.jpg', N'Lập trình javascript' )
INSERT INTO dbo.ChuyenDe VALUES(   N'CD004', N'Xây dựng trang web', 500, 90, N'bg_5.jpg', N'Xây dựng trang web' )
INSERT INTO dbo.ChuyenDe VALUES(   N'CD005', N'Java 2', 500, 90, N'bg_5.jpg', N'Lạp trình java 2' )
--Xem dữ liệu bảng chuyên đề
SELECT * FROM dbo.ChuyenDe
--Cập nhật dữ liệu từ bảng chuyên đề
UPDATE dbo.ChuyenDe SET TenCD=N'Lập trình java 3',HocPhi=500,ThoiLuong=90,Hinh=N'No Avatar',MoTa=N'Lập trình java 3' WHERE MaCD=N'CD006'
--Xóa dữ liệu từ bảng chuyên đề
DELETE FROM dbo.ChuyenDe WHERE MaCD=N'CD005'
--------------------------------------------------------------------------------------------------------------
-- Thêm dữ liệu vào bảng Nhân viên
INSERT INTO dbo.NhanVien VALUES(   N'Tuan123',N'+rP2fZv1DsymN0Wse79IWjCwsEfVnumOSmmUQDKcQYIf32gYM9D2sX5Ub9V8Rq1CHXxRAw4EuX38J3Wxblex0A==', N'Đặng Hoàng Tuấn',1 )
INSERT INTO dbo.NhanVien VALUES(   N'Thanh567',N'123456', N'Lê Thị Thanh', 0 )
INSERT INTO dbo.NhanVien VALUES(   N'Minh98',N'123456', N'Lê Thanh Minh', 0 )
INSERT INTO dbo.NhanVien VALUES(   N'Thanh99',N'987654', N'Nguyễn Văn Thành', 0 )
INSERT INTO dbo.NhanVien VALUES(   N'Binh65',N'123456', N'Nguyễn Thanh Bình', 0 )
--demo
SELECT * FROM dbo.NhanVien WHERE MaNV='Tuan123' AND MatKhau='123456'
SELECT MaKH,ChuyenDe.MaCD,ChuyenDe.HocPhi,ChuyenDe.ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao FROM dbo.KhoaHoc INNER JOIN dbo.ChuyenDe ON
ChuyenDe.MaCD = KhoaHoc.MaCD WHERE TenCD=N'lập trình javascript'
--Xem dữ liệu bảng Nhân viên
SELECT * FROM dbo.NhanVien
--Cập nhật dữ liệu từ bảng Nhân viên
UPDATE dbo.NhanVien SET MatKhau ='01234567' , HoTen='Nguyễn Thành Án', VaiTro =0 WHERE MaNV = N'Binh65'
--Xóa dữ liệu bảng Nhân viên
DELETE FROM dbo.NhanVien WHERE MaNV=N'Binh65'
--------------------------------------------------------------------------------------------------------------
--Thêm dữ liệu vào bảng khóa học
INSERT INTO dbo.KhoaHoc VALUES(N'CD001',500,90, GETDATE(),N'Khóa học',N'Tuan123',GETDATE())
INSERT INTO dbo.KhoaHoc VALUES(N'CD002',500,90, GETDATE(),N'Khóa học',N'Binh65',GETDATE())
INSERT INTO dbo.KhoaHoc VALUES(N'CD003',500,90, GETDATE(),N'Khóa học',N'Binh65',GETDATE())
INSERT INTO dbo.KhoaHoc VALUES(N'CD001',500,90, GETDATE(),N'Khóa học',N'Tuan123',GETDATE())
INSERT INTO dbo.KhoaHoc VALUES(N'CD004',500,90, GETDATE(),N'Khóa học',N'Minh98',GETDATE())
--Xem dữ liệu bảng Khóa Học
SELECT * FROM dbo.KhoaHoc 
--Xóa bảng Khóa học
DELETE FROM dbo.KhoaHoc WHERE MaKH =1
--Cập nhật dữ liệu từ bảng Khóa học
UPDATE dbo.KhoaHoc SET MaCD=N'CD002',HocPhi=500,ThoiLuong=100,NgayKG='2023-07-01' , GhiChu='Khóa học CNTT',MaNV ='Minh98',NgayTao=GETDATE() WHERE MaCD='CD002'
--Thêm vào bảng người học
GO
INSERT INTO dbo.NguoiHoc VALUES (N'NH00001',N'Đặng Thanh Thỏa',1,  '1999-01-01',N'0945646543',N'Thoa@gmail.com',N'Đăng ký học',  N'Tuan123', '2023-03-04' )
INSERT INTO dbo.NguoiHoc VALUES (N'NH002',N'Lê Thị Yến Nhi',0,  '2000-02-04',N'0945466345',N'yenNhi@gmail.com',N'Đăng ký học',  N'Tuan123', '2023-03-06' )
INSERT INTO dbo.NguoiHoc VALUES (N'NH003',N'Nguyễn Văn An',1,  '2003-06-06',N'0942256765',N'An143@gmail.com',N'Đăng ký học',  N'Tuan123', '2023-03-03' )
INSERT INTO dbo.NguoiHoc VALUES (N'NH004',N'Thảo Thanh Trà',0,  '2002-09-01',N'0945465654',N'Binh435@gmail.com',N'Đăng ký học',  N'Minh98', '2023-03-02')
INSERT INTO dbo.NguoiHoc VALUES (N'NH005',N'Nguyễn Thị Quỳnh Mai',0, '2003-07-08',N'0946764345',N'MaiQuynh@gmail.com',N'Đăng ký học',  N'Minh98','2023-03-01' )
--Cập nhật thông tin người học
UPDATE dbo.NguoiHoc SET HoTen='Nguyễn Thanh Thảo', GioiTinh=0,NgaySinh='1999-01-01',DienThoai='09453453444',Email='Thao@gmail.com',GhiChu='Đăng kí học',MaNV='Tuan123'
NgayDK='2022-01-01' WHERE MaNH='NH001'
-- xóa thông tin người học
DELETE FROM dbo.NguoiHoc WHERE MaNH =N'NH001'
--Thêm bảng học viên
INSERT INTO dbo.HocVien VALUES(  3, N'NH00001',3)
INSERT INTO dbo.HocVien VALUES(  2, N'NH00002',9)
INSERT INTO dbo.HocVien VALUES(  2, N'NH00002',9)
INSERT INTO dbo.HocVien VALUES(  2, N'NH00002',7)
--xem dữ liệu bảng người học
SELECT * FROM dbo.NguoiHoc
--Xóa người học trong bảng học viên
DELETE FROM dbo. HocVien WHERE MaNH =''
--Cập nhật bảng học viên 
UPDATE dbo.HocVien SET MaKH=3,MaNH='NH003',Diem =8 WHERE MaHV=''
go
CREATE PROC Sp_LuongNguoiHoc 
AS 
	BEGIN
		SELECT 
			YEAR(NgayDK) Nam,
			COUNT(*) SoLuong,
			MIN(NgayDK) DauTien,
			MAX(NgayDK) CuoiCung
		
		FROM dbo.NguoiHoc GROUP BY YEAR(NgayDK)
	END
	EXECUTE dbo.Sp_LuongNguoiHoc
	--Proc bảng điểm
CREATE PROC Sp_BangDiem(@MaKH int)
AS
BEGIN
	SELECT nh.MaNH, nh.HoTen,hv.Diem FROM dbo.HocVien hv JOIN dbo.NguoiHoc nh ON nh.MaNH = hv.MaNH WHERE hv.MaKH =@maKH
	ORDER BY hv.Diem DESC
END
EXECUTE dbo.Sp_BangDiem @MaKH = 2 -- int

--Proc Thống kê điểm
CREATE PROC sp_ThongKeDiem AS
BEGIN 
	SELECT cd.TenCD, COUNT(hv.MaHV) soHV,
		MIN(hv.Diem) ThapNhat,
		MAX(hv.Diem) CaoNhat,
		AVG(hv.Diem) TrungBinh
	FROM dbo.KhoaHoc kh JOIN dbo.HocVien hv ON hv.MaKH = kh.MaKH
	JOIN dbo.ChuyenDe cd ON cd.MaCD = kh.MaCD
	GROUP BY cd.TenCD
END
EXECUTE dbo.sp_ThongKeDiem
--Thống kê doanh thu
CREATE PROC sp_ThongKeDoanhThu(@Year INT)
AS BEGIN 
	SELECT 
		cd.TenCD ChuyenDe,
		COUNT(DISTINCT kh.MaKH) SoKH,
		COUNT( hv.MaHV) SoHV,
		SUM(kh.HocPhi) DoanhThu,
		MIN(kh.HocPhi) ThapNhat,
		MAX(kh.HocPhi) CaoNhat,
		AVG(kh.HocPhi) TrungBinh
	FROM dbo.KhoaHoc kh JOIN dbo.HocVien hv ON hv.MaKH = kh.MaKH
	JOIN dbo.ChuyenDe cd ON cd.MaCD = kh.MaCD WHERE YEAR(kh.NgayKG)=@Year GROUP BY cd.TenCD	
END
EXECUTE dbo.sp_ThongKeDoanhThu @Year = 2023 -- int
--demo
go
select * from NhanVien where HoTen like N'Nguyễn Thanh Bình'
SELECT MaHV,KhoaHoc.MaKH,HocVien.MaNH,HoTen,Diem  FROM dbo.HocVien INNER JOIN dbo.KhoaHoc ON KhoaHoc.MaKH = HocVien.MaKH INNER JOIN dbo.NguoiHoc ON NguoiHoc.MaNH = HocVien.MaNH WHERE NgayKG= '2023-09-01'
SELECT ChuyenDe.MaCD,TenCD,ChuyenDe.HocPhi,ChuyenDe.ThoiLuong,NgayKG,GhiChu FROM dbo.ChuyenDe INNER JOIN dbo.KhoaHoc ON KhoaHoc.MaCD = ChuyenDe.MaCD WHERE TenCD =N'Java 1'