package com.javacodegeeks.android.androidsocketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class Server extends Activity {

    private TextView serverStatus;
    
    // DEFAULT IP
    public static String SERVERIP = "10.0.2.15";
    public static String msg="";
    // DESIGNATE A PORT
    public static final int SERVERPORT = 8080;
    public static final int isTrue = 0;
 
    private Handler handler = new Handler();
 
    private ServerSocket serverSocket;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        serverStatus = (TextView) findViewById(R.id.server_status);
 
        SERVERIP = getLocalIpAddress();
 
        Thread fst = new Thread(new ServerThread());
        fst.start();
    }
    public void updateMessageBox(String msg){
    	TextView Client_message = (TextView)findViewById(R.id.client_message);
    	Client_message.setText(msg);
    	 Log.d("ServerActivity", " from update display");
    	Client_message.postInvalidateDelayed(100);
    }
    public final String ll="";
    public class ServerThread implements Runnable {
 
        public void run() {
            try {
                if (SERVERIP != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Listening on IP: " + SERVERIP);
                        }
                    });
                    serverSocket = new ServerSocket(SERVERPORT);
                    
                    while (true) {
                        // LISTEN FOR INCOMING CLIENTS
                        Socket client = serverSocket.accept();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serverStatus.setText("Connected.");
                            }
                        });
                        
                        Log.d("ServerActivity", "1111111111");
                        try {
                            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            String line = null;
                            
                            
                            while((line = in.readLine()) != null) {
                            	
                               // Log.d("ServerActivity", line);
                               // updateMessageBox(line);
                                msg=line;
                                handler.post(new Runnable() {
                                	
                                    @Override
                                    public void run() {
                                        // DO WHATEVER YOU WANT TO THE FRONT END
                                        // THIS IS WHERE YOU CAN BE CREATIVE
                                    	 Log.d("ServerActivity", msg);
                                    	 updateMessageBox(msg);
                                    	                                    	
                                    }
                                });
                            }
                            //break;
                        } catch (Exception e) {
                        	 Log.e("ClientActivity", "S: Error", e);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    serverStatus.setText("Oops. Connection interrupted. Please reconnect your phones.");
        	                       
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            serverStatus.setText("Couldn't detect internet connection.");
                        }
                    });
                }
            } catch (Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        serverStatus.setText("Error");
                    }
                });
                e.printStackTrace();
            }
        }
    }
 
    // GETS THE IP ADDRESS OF YOUR PHONE'S NETWORK
    private String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) { return inetAddress.getHostAddress().toString(); }
                }
            }
        } catch (SocketException ex) {
            Log.e("ServerActivity", ex.toString());
        }
        return null;
    }
 
    @Override
    protected void onStop() {
        super.onStop();
        try {
             // MAKE SURE YOU CLOSE THE SOCKET UPON EXITING
             serverSocket.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
 
}