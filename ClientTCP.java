import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClientTCP {
    public static void main(String[] args) {
        String host = "203.162.10.109";       // hoặc thay bằng IP server thật
        int port = 2208;
        String studentCode = "B22DCVT445";
        String qCode = "TbSNyGJG";       // Mã câu hỏi trong đề

        try (Socket socket = new Socket()) {
            // (0) Kết nối tới server với timeout 5 giây
            socket.connect(new InetSocketAddress(host, port), 5000);

            // (0.1) Tạo luồng đọc/ghi (BufferedWriter/BufferedReader)
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            // (a) Gửi chuỗi "studentCode;qCode"
            String msg = studentCode + ";" + qCode;
            writer.write(msg);
            writer.newLine(); // kết thúc dòng để server đọc được
            writer.flush();
            System.out.println("Đã gửi: " + msg);

            // (b) Nhận danh sách tên miền từ server
            String domainsLine = reader.readLine();
            System.out.println("Nhận được: " + domainsLine);

            // (c) Lọc ra các tên miền .edu
            String[] domains = domainsLine.split(",");
            List<String> eduDomains = new ArrayList<>();
            for (String d : domains) {
                d = d.trim(); // bỏ khoảng trắng thừa
                if (d.endsWith(".edu")) {
                    eduDomains.add(d);
                }
            }

            // Ghép danh sách .edu thành chuỗi để gửi lại
            String result = String.join(", ", eduDomains);
            writer.write(result);
            writer.newLine();
            writer.flush();
            System.out.println("Đã gửi lại tên miền .edu: " + result);

            // (d) Đóng kết nối
            writer.close();
            reader.close();
            socket.close();
            System.out.println("Kết thúc chương trình.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
