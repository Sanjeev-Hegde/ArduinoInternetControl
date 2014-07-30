package com.javacodegeeks.android.androidsocketclient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Client extends Activity {

	   private EditText serverIp;
	   
	    private Button connectPhones;
	 
	    private String serverIpAddress = "";
	 
	    public static final int SERVERPORT = 8080;
	    private boolean connected = false;
	 
	    @SuppressWarnings("unused")
		private Handler handler = new Handler();
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	 
	        serverIp = (EditText) findViewById(R.id.EditText01);
	       // connectPhones = (Button) findViewById(R.id.myButton);
	       // connectPhones.setOnClickListener(connectListener);
	        
	        /////////////  btn forward ///////////////
	        ((Button)findViewById(R.id.btn_forward)).setOnTouchListener(new View.OnTouchListener() {
				
	        	 @SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {
	                 switch (event.getAction())
	                 {
	                 	case MotionEvent.ACTION_DOWN:
	                 	 {
	                 		sendBtnVal(1);
	                         return true;
	                     }

	                     case MotionEvent.ACTION_UP:
	                     {
	                    	 sendBtnVal(0);
	                         return true;
	                     }

	                     default:
	                         return false;
	                 }
	              }

			}); 
	        ///////////////////////////////////////////////
	        /////////////  btn backward ///////////////
	        ((Button)findViewById(R.id.btn_backward)).setOnTouchListener(new View.OnTouchListener() {
				
	        	 @SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {
	                 switch (event.getAction())
	                 {
	                 	case MotionEvent.ACTION_DOWN:
	                 	 {
	                 		sendBtnVal(2);
	                         return true;
	                     }

	                     case MotionEvent.ACTION_UP:
	                     {
	                    	 sendBtnVal(0);
	                         return true;
	                     }

	                     default:
	                         return false;
	                 }
	              }

			}); 
	        ///////////////////////////////////////////////
	        /////////////  btn left///////////////
	        ((Button)findViewById(R.id.btn_left)).setOnTouchListener(new View.OnTouchListener() {
				
	        	 @SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {
	                 switch (event.getAction())
	                 {
	                 	case MotionEvent.ACTION_DOWN:
	                 	 {
	                 		sendBtnVal(3);
	                         return true;
	                     }

	                     case MotionEvent.ACTION_UP:
	                     {
	                    	 sendBtnVal(0);
	                         return true;
	                     }

	                     default:
	                         return false;
	                 }
	              }

			}); 
	        ///////////////////////////////////////////////
	        /////////////  btn right///////////////
	        ((Button)findViewById(R.id.btn_right)).setOnTouchListener(new View.OnTouchListener() {
				
	        	 @SuppressLint("ClickableViewAccessibility")
				public boolean onTouch(View v, MotionEvent event) {
	                 switch (event.getAction())
	                 {
	                 	case MotionEvent.ACTION_DOWN:
	                 	 {
	                 		sendBtnVal(4);
	                         return true;
	                     }

	                     case MotionEvent.ACTION_UP:
	                     {
	                    	 sendBtnVal(0);
	                         return true;
	                     }

	                     default:
	                         return false;
	                 }
	              }

			}); 
	        ///////////////////////////////////////////////
	      
	      

	    }
	 
	    public void connectListener(View view){
	 
	      
			
				 if (!connected) {
		                serverIpAddress = serverIp.getText().toString();
		                if (!serverIpAddress.equals("")) {
		                    Thread cThread = new Thread(new ClientThread());
		                    cThread.start();
		                }
		            }
			
	   
	    }
	    Socket socket;
	    public class ClientThread implements Runnable {
	 
	        public void run() {
	            try {
	                InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
	                Log.d("ClientActivity", "C: Connecting...");
	                 socket = new Socket(serverAddr, SERVERPORT);
	                connected = true;
	                
	               /* while (connected) {
	                    try {
	                        Log.d("ClientActivity", "C: Sending command.");
	                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
	                                    .getOutputStream())), true);
	                            // WHERE YOU ISSUE THE COMMANDS
	                            out.println("Hey Server!");
	                            Log.d("ClientActivity", "C: Sent.");
	                    } catch (Exception e) {
	                        Log.e("ClientActivity", "S: Error", e);
	                    }
	                }*/
	                //socket.close();
	                //Log.d("ClientActivity", "C: Closed.");
	            } catch (Exception e) {
	                Log.e("ClientActivity", "C: Error", e);
	                connected = false;
	            }
	        }
	    }
	    public void sendclientmessage(View view){
	    	String message="";
	    	EditText clientMessage = (EditText)findViewById(R.id.EditText02);
	    	message=clientMessage.getText().toString();
	    	try{
	    	PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                    .getOutputStream())), true);
            // WHERE YOU ISSUE THE COMMANDS
            out.println(message);
	    	}
	    	catch(IOException e){
	    	     
                Log.e("ClientActivity", "S: Error", e);
	    	}
           
	    	
	    }
	    public void sendBtnVal(int val){
	    	String message="";
	    	message=""+val+"";
	    	try{
	    	PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
                    .getOutputStream())), true);
            // WHERE YOU ISSUE THE COMMANDS
            out.println(message);
	    	}
	    	catch(IOException e){
	    	     
                Log.e("ClientActivity", "S: Error", e);
	    	}
           
	    	
	    }
}