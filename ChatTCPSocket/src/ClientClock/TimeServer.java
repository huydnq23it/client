package ClientClock;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
    public static void main(String[] args) {
        try {
            // Tạo socket server ở cổng 9876
            ServerSocket serverSocket = new ServerSocket(9876);
            System.out.println("Server is running...");

            while (true) {
                // Chấp nhận kết nối từ client
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);

                // Tạo luồng vào và ra
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Đọc yêu cầu từ client
                String request = in.readLine();
                if (request.equals("time")) {
                    // Phản hồi lại thời gian hiện tại
                    String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    out.println(time);
                    System.out.println("Sent time to client: " + time);
                }

                // Đóng kết nối
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
