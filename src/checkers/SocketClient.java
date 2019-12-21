package checkers;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;

public class SocketClient {
	private String address = "127.0.0.1";// server name
	private int port = 8765;// port number

	public SocketClient() {

		Socket client = new Socket();
		InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
		try {
			client.connect(isa, 10000);
			BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
			// for(int i=0;i<10;i++) {
			// out.write(("t"+i+"Send From Client yayaya\n").getBytes());
			out.write("Send From Client \n".getBytes());
			out.flush();
			// }
			// out.write("Send From Client ".getBytes());
			// out.flush();
			out.close();
			out = null;
			client.close();
			client = null;

		} catch (java.io.IOException e) {
			System.out.println("Socket連線有問題 !");
			System.out.println("IOException :" + e.toString());
		}
	}

	public static void main(String args[]) {
		new SocketClient();
	}
}