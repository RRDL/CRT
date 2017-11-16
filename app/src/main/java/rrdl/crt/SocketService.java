package rrdl.crt;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static android.content.ContentValues.TAG;

public class SocketService extends Service {
    public static final String SERVERIP = "192.168.43.221";
    //public static final int SERVERPORT = 2222;
    PrintWriter out;
    BufferedReader in ;
    Socket socket;
    InetAddress serverAddr;
    Boolean mRun = false;
    public SocketService() {}
    private final IBinder myBinder = new LocalBinder();

    String incomingMessage;
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
            //System.out.println("I am in Localbinder ");
            return SocketService.this;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        connect();
    }

    public void sendMessage(String msg){
        if (out != null && !out.checkError()) {
            //System.out.println("in sendMessage"+message);
            out.println(msg);
            out.flush();
        }
    }




    public void connect(){
        Runnable connect = new connectSocket();
        new Thread(connect).start();

    }

    class connectSocket implements Runnable {
        @Override
        public void run() {

            mRun = true;

            try {
                // Creating InetAddress object from ipNumber passed via constructor from IpGetter class.
                InetAddress serverAddress = InetAddress.getByName(SERVERIP);

                Log.d(TAG, "Connecting...");
                Socket socket = new Socket(serverAddress, 2222);

                try {
                    // Create PrintWriter object for sending messages to server.
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                    //Create BufferedReader object for receiving messages from server.
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (mRun) {
                        incomingMessage = in.readLine();
                        if (incomingMessage != null) {

                            Log.e("MSG",incomingMessage);

                        }

                    }

                } catch (Exception e) {

                    Log.d(TAG, "Error", e);


                } finally {

                    out.flush();
                    out.close();
                    in.close();
                    socket.close();
                    Log.d(TAG, "Socket Closed");
                }

            } catch (Exception e) {

                Log.d(TAG, "Error", e);

            }

        }
    }
    public void stopClient(){
        Log.d(TAG, "Client stopped!");
        mRun = false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
            Log.e("Socket","Socket closed");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        socket = null;
    }


}
