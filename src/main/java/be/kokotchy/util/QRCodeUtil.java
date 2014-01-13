package be.kokotchy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: canas
 * Date: 1/13/14
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class QRCodeUtil {

	private static String url = "https://www.google.com/chart?chs=200x200&chld=M|0&cht=qr&chl=otpauth://totp/";

	public static BufferedReader displayQRCode(String app, String email, String secret) throws IOException {
		String command = createCommand(app, email, secret);
		Process exec = Runtime.getRuntime().exec(command);
		BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		return reader;
	}

	private static String createCommand(String app, String email, String secret) {
		StringBuilder builder = new StringBuilder();
		builder.append(app);
		builder.append(" otpauth://totp/");
		builder.append(email);
		builder.append("?secret=");
		builder.append(secret);
		return builder.toString();
	}

	public static String generateUrl(String email, String secret) {
		return url + email + "%3Fsecret%3D" + secret;
	}
}
