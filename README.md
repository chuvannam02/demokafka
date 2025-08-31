### Spring Boot learning

🔹 hashCode() dùng để làm gì?

Trong Java, hashCode() được dùng để:

Tạo mã băm (hash code) đại diện cho object.

Hỗ trợ cấu trúc dữ liệu dựa trên hash như:

HashMap

HashSet

Hashtable

Những cấu trúc này cần một số nguyên (int) để xác định "bucket" nơi object sẽ được lưu trữ → từ đó tra cứu nhanh hơn.

# Phân biệt giữa Comparable<T> và Comparator<T> trong Java?
- Comparable<T>:
  - Được sử dụng để định nghĩa thứ tự tự nhiên (natural ordering - mặc đnh) của các object.
  - Cần implement phương thức compareTo(T o).
  - Chỉ có thể có một cách sắp xếp duy nhất cho một lớp.
  - Phải khai báo trong lớp cần so sánh.
  ####
-  Comparator<T>:
    - Được sử dụng để định nghĩa nhiều cách sắp xếp khác nhau cho cùng một loại object.
    - Cần implement phương thức compare(T o1, T o2).
    - Có thể tạo nhiều Comparator khác nhau cho cùng một lớp.
    - Không cần phải khai báo trong lớp cần so sánh.

# Phân biệt giữa == và equals() trong Java?
- == so sánh địa chỉ bộ nhớ (reference) của hai object.
- equals() so sánh giá trị bên trong của hai object (nếu phương thức equals() được override).
-  Nếu không override, equals() sẽ hoạt động giống như ==.

# Singleton pattern là gì?
- Singleton pattern là một mẫu thiết kế (design pattern) trong lập trình phần mềm, nhằm đảm bảo rằng một lớp chỉ có một thể hiện (instance) duy nhất và cung cấp một điểm truy cập toàn cục đến thể hiện đó.
- Các đặc điểm chính của Singleton pattern:
  - Chỉ có một thể hiện duy nhất của lớp.
  - Cung cấp một phương thức tĩnh (static method) để truy cập thể hiện đó.
  - Thường sử dụng trong các trường hợp như quản lý kết nối cơ sở dữ liệu, cấu hình ứng dụng, hoặc các dịch vụ dùng chung.
- Cách triển khai Singleton pattern trong Java:
  - Sử dụng biến tĩnh để lưu trữ thể hiện duy nhất.
  - Sử dụng phương thức tĩnh để trả về thể hiện đó.
  - Đảm bảo rằng constructor của lớp là private để ngăn chặn việc tạo thể hiện mới từ bên ngoài lớp.

# Builder pattern là gì?

# Factory pattern là gì?

# 
