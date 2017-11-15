package rrdl.crt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketService extends Service {
    public static final String SERVERIP = "192.168.43.221";
    public static final int SERVERPORT = 2222;
    PrintWriter out;
    BufferedReader in ;
    Socket socket;
    InetAddress serverAddr;

    public SocketService() {}
    private final IBinder myBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("onBind","Ibinder from onBind");
        //throw new UnsupportedOperationException("Not yet implemented");
        return myBinder;
    }


    //TCPClient mTcpClient = new TCPClient();
    public class LocalBinder extends Binder {
        public SocketService getService() {
            System.out.println("I am in Localbinder ");
            return SocketService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        connect();
        //System.out.println("I am in on create");
    }
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            //System.out.println("in sendMessage"+message);
            out.println(message);
            out.flush();
        }
    }
    public String recieveMessage(){
        String msg = "";
        if(in != null){
            try {
                msg = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    public void connect(){
        Runnable connect = new connectSocket();
        new Thread(connect).start();
    }

    class connectSocket implements Runnable {
        @Override
        public void run() {
            try {
                //here you must put your computer's IP address.
                serverAddr = InetAddress.getByName(SERVERIP);
                Log.e("TCP Client", "C: Connecting...");
                //create a socket to make the connection with the server
                socket = new Socket(serverAddr, SERVERPORT);
                Log.e("SOCKET","Socket Created");

                try {
                    //send the message to the server
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    Log.e("TCP Client", "C: Sent.");
                    Log.e("TCP Client", "C: Done.");
                }
                catch (Exception e) {

                    Log.e("TCP Writer", "S: Error", e);

                }
                try {
                    //read from the server
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.e("TCP Client", "C: Recieve.");
                    Log.e("TCP Client", "C: Done.");

                }catch (Exception e){
                    Log.e("TCP Reader", "C: Error", e);
                }
            } catch (Exception e) {

                Log.e("TCP", "C: Error", e);

            }

        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket = null;
    }
}
