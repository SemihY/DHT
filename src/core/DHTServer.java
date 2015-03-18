package core;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class DHTServer {

	private static DHT<String> dht;
	private ServerSocket ss;
	private int port;

	public DHTServer(int port) {
		try {
			this.ss = new ServerSocket(port); //accepts connections from client
			this.port = port;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void startTCPServer() throws IOException{
		
		boolean complete = false;
		Socket sock = null;
		System.out.println("\nWaiting for client to connect on port " + port);
		try {
			sock = ss.accept(); //blocking wait
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Woohoo! Someone connected from " + sock.getInetAddress());
		
		dht=new NodeImpl<>("firstnode");
		
		BufferedReader in = null;
		PrintWriter out=null;
		while (!complete) {
			//out = new PrintWriter(sock.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String input = new String (in.readLine());
			System.out.println("Recd: " + input);
				try {
					String[] inputs = input.split(" ");
					switch(inputs[0]) {
					case "put":
						String key = inputs[1];
						String value = inputs[2];
						dht.put(key, value);
						out.println("");
						break;
					case "get":
						key = inputs[1];
						out.println("Got from dht: " + dht.get(key));
						break;
					case "join":
						key = inputs[1];
						new NodeImpl<>(key, (Node<String>)dht);
						break;
					default:
						out.println("Usage:");
						out.println("put key value");
						out.println("get key");
						out.println("exit");
						break;
					}
				} catch (Exception e) {
					System.out.println("Something went wrong.. a bad input?");
				}
				
				
				Scanner inputServer = new Scanner(System.in);
				String inputsServer = new String (inputServer.nextLine());
				try {
					String[] inputs = inputsServer.split(" ");
					switch(inputs[0]) {
					case "put":
						String key = inputs[1];
						String value = inputs[2];
						dht.put(key, value);
						break;
					case "get":
						key = inputs[1];
						System.out.println("Got from dht: " + dht.get(key));
						break;
					case "join":
						key = inputs[1];
						new NodeImpl<>(key, (Node<String>)dht);
						break;
					default:
						System.out.println("Usage:");
						System.out.println("put key value");
						System.out.println("get key");
						System.out.println("remove key");
						System.out.println("exit");
						break;
					}
				} catch (Exception e) {
					System.out.println("Something went wrong.. a bad input?");
				}
				
			if (input.compareTo("exit") == 0) {
				complete = true;
			}
			
		}
		in.close();
		sock.close();
		System.out.println("Server closed"); 

		
	}
	
	public static void main(String[] args)
	{
		DHTServer serv = new DHTServer(1337);//listens on port leet!
		while(true) {
			try {
				serv.startTCPServer();
				} catch (Exception e) {
            		e.printStackTrace();
       		 }
        }
	}

}