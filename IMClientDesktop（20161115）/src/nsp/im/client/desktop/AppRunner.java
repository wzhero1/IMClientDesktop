package nsp.im.client.desktop;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;

public class AppRunner {
	public static void main(final String[] args) throws Exception {
		System.setErr(new PrintStream(new OutputStream() {
			private FileOutputStream fos;
			private PrintStream sos;

			{
				fos = new FileOutputStream("err.log");
				sos = System.err;
			}

			@Override
			public void write(int arg0) throws IOException {
				fos.write(arg0);
				sos.write(arg0);
			}
		}));
		System.setOut(new PrintStream(new OutputStream() {
			private FileOutputStream fos;
			private PrintStream sos;

			{
				fos = new FileOutputStream("out.log");
				sos = System.out;
			}

			@Override
			public void write(int arg0) throws IOException {
				fos.write(arg0);
				sos.write(arg0);
			}
		}));

	//String ip = "127.0.0.1";
//		String ip = "111.204.219.246";
//		String ip = "10.10.4.254";//ÕýÊ½IP
		String ip = "10.10.30.136";//ÓÊÏä×¢²áµØÖ·
//		int port = 16069;
		int port = 16068;//²âÊÔÓÃ
	//	File historyPath = new File("path.dat");
		//String dir = "D:\\Task\\IMClientDesktopIdea(0829)\\IM Files";
		String dir = "IM Files";
		if (args.length == 2) {
			ip = args[0];
			port = Integer.parseInt(args[1]);
		}
		run(ip, port, dir);
	}

	private static void run(String ip, int port, String dir) {
		SwingUtilities.invokeLater(() ->
			new ImApplication(ip, port, dir).run()
		);
	}
}
