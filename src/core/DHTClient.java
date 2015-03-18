package core;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class DHTClient extends Thread {

	public void run() {
		
		DHTClient tcpClient = new DHTClient();
		try {
			tcpClient.startTCPClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startTCPClient() throws IOException{
		
		Scanner input = new Scanner(System.in);
		String message;
		boolean complete = false;
	
		Socket client_socket = new Socket(InetAddress.getLocalHost(), 1337);
		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
		out.println("join a");
		while (!complete) {
			
			message = new String (input.nextLine());
			System.out.println("to be sent: " + message);
			out.println(message);
			System.out.println("Message sent");
			
			if (message.compareTo("exit") == 0) {
				complete = true;
			}
			
		}
		in.close();
		out.close();
		client_socket.close();
		System.out.println("Client socket closed");

		
	}
	
	public static void main(String[] args)
	{
		DHTClient serv = new DHTClient();//listens on port leet!
		while(true) {
			try {
				serv.run();
				} catch (Exception e) {
            		e.printStackTrace();
       		 }
        }
	}

}