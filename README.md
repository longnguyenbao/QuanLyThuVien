# BTL_KTPM
HƯỞNG DẪN

vào mysql run file thuviendb.sql nếu trong CSDL đã có database thuviendb thì xóa đi rồi mới run file

Vào đường dẫn quanlythuvien/src/main/java/com/btl/utils/JdbcUtils.java
Chỉnh lại user và password của mysql driver sao cho phù hợp với máy

Ví dụ máy trường có user=root và password=12345678 thì chỉnh là: DriverManager.getConnection("jdbc:mysql://localhost/thuviendb", "root", "12345678");
