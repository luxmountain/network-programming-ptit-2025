import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Practice {
    private static void sendMessage(OutputStream os, String msg) throws IOException {
        os.write(msg.getBytes());
        os.flush();
        System.out.println("Sent: " + msg);
    }

    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2206;
        String qCode = "Bz6pCg9n";
        String sCode = "B22DCVT445";
        String msg = sCode + ";" + qCode;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            sendMessage(outputStream, msg);

            System.out.println("Waiting to receive data from server...");
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Received data: " + receivedData);

            String[] numbers = receivedData.split(",");
            List<Integer> nums = new ArrayList<>();
            long sum = 0;

            for (String number : numbers) {
                try {
                    int num = Integer.parseInt(number);
                    nums.add(num);
                    sum += num;
                } catch (NumberFormatException e) {
                    System.out.println("Error " + e);
                }
            }

            double average = (double) sum / nums.size();
            double target = average * 2;

            int bestNum1 = nums.get(0);
            int bestNum2 = nums.get(1);
            double bestDifference = Math.abs((bestNum1 + bestNum2) - target);

            for (int i = 0; i < nums.size(); i++) {
                for (int j = i + 1; j < nums.size(); j++) {
                    int num1 = nums.get(i);
                    int num2 = nums.get(j);
                    double difference = Math.abs((num1 + num2) - target);

                    if (difference < bestDifference) {
                        if (num1 < num2) {
                            bestNum1 = num1;
                            bestNum2 = num2;
                        } else {
                            bestNum1 = num2;
                            bestNum2 = num1;
                        }
                        bestDifference = difference;
                    }
                }
            }

            String resutl = bestNum1 + "," + bestNum2;
            sendMessage(outputStream, resutl);
            inputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("Close connection");
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}