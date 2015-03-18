import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket1 = null;
		ServerSocket serverSocket2 = null;
		try {
			serverSocket1 = new ServerSocket(0);
			serverSocket1.setSoTimeout(60000);
			System.out.println(serverSocket1.getLocalPort());
			System.out.println(serverSocket1.getLocalSocketAddress());
			System.out.println(serverSocket1.getInetAddress());
			while (true) {
				Socket socket = serverSocket1.accept();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				System.out.println(reader.readLine());
				reader.close();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != serverSocket1)
			serverSocket1.close();

			if (null != serverSocket2)
			serverSocket2.close();
		}
	}

}
