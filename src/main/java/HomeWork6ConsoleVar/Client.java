package HomeWork6ConsoleVar;


import java.io.*;
import java.net.Socket;


class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader reader;


    public Client(){
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        new Client();
    }


    private void openConnection() throws IOException {
        socket = new Socket(Constants.HOST, Constants.PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(System.in));

        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    String str = reader.readLine();
                    if(str.equalsIgnoreCase(Constants.STOP_WORD)){
                        closeConnection();
                        break;
                    }
                    out.writeUTF(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Client shutting down the server");
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Server: " + in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
    }

       public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


