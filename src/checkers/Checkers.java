package checkers;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;

public class Checkers extends Thread {
	private boolean OutServer = false;
	private ServerSocket server;
	private final int ServerPort = 8765;
	win w;
	String strbuf = new String();

	public void run() {
		Socket socket;
		java.io.BufferedInputStream in;
		strbuf = "Server started!";
		w.sockettext.setText(strbuf);
		System.out.println("Server started!");
		while (!OutServer) {
			socket = null;
			try {
				synchronized (server) {
					socket = server.accept();
				}
				System.out.println("connected : InetAddress = " + socket.getInetAddress());
				strbuf += "connected : InetAddress = " + socket.getInetAddress();
				w.sockettext.setText(strbuf);
// TimeOut時間
				socket.setSoTimeout(100000);

				in = new java.io.BufferedInputStream(socket.getInputStream());
				byte[] b = new byte[1024];
				String data = "";
				int length;
				while ((length = in.read(b)) > 0) {
					data += new String(b, 0, length);
					strbuf += data;
					w.sockettext.setText(strbuf);
				}

				System.out.println("value:" + data);
				in.close();
				in = null;
				socket.close();

			} catch (java.io.IOException e) {
				System.out.println("Socketconnect error !");
				System.out.println("IOException :" + e.toString());
			}

		}
	}

	public Checkers() {
		try {
			server = new ServerSocket(ServerPort);

		} catch (java.io.IOException e) {
			System.out.println("Socket startup error!");
			System.out.println("IOException :" + e.toString());
		}
	}

	public static void main(String[] args) {
		Checkers c = new Checkers();
		c.w = new win();
		c.w.setSize(1000, 1000);
		c.w.setLayout(new FlowLayout());

		c.w.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				super.windowClosing(e);
			}

		});

		c.w.setVisible(true);
		c.start();

	}

}