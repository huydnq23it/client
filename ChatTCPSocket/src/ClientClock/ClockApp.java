package ClientClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClockApp extends JFrame {
    private JLabel clockLabel;

    public ClockApp() {
        setTitle("Clock Application");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tạo nhãn để hiển thị thời gian
        clockLabel = new JLabel();
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(clockLabel, BorderLayout.CENTER);

        // Khởi động luồng để cập nhật thời gian từ server mỗi giây
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTimeFromServer();
            }
        });
        timer.start();
    }

    // Phương thức để gửi yêu cầu "time" đến server và nhận thời gian từ server
    private void updateTimeFromServer() {
        try {
            // Kết nối đến server qua cổng 9876
            Socket socket = new Socket("localhost", 9876);

            // Gửi yêu cầu "time" đến server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("time");

            // Nhận thời gian từ server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String time = in.readLine();

            // Hiển thị thời gian trên nhãn đồng hồ
            clockLabel.setText(time);

            // Đóng kết nối
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClockApp().setVisible(true);
            }
        });
    }
}
