package HomeWork6ConsoleVar;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Server {


    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
            System.out.println("Waiting for connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Thread t1 = new Thread(() -> {
                while (true) {
                    try {
                        String str = reader.readLine();
                        if (str.equalsIgnoreCase(Constants.STOP_WORD)) {
                            break;
                        }
                        out.writeUTF(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread t2 = new Thread(() -> {
                while (true) {
                    try {
                        System.out.println("Client: " + in.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t1.start();
            t2.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
