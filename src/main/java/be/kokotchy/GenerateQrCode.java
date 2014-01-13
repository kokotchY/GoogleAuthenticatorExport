package be.kokotchy;

import be.kokotchy.util.QRCodeUtil;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: canas
 * Date: 1/13/14
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateQrCode {
	public static void main(String[] args) {
		new GenerateQrCode(args);
	}

	public GenerateQrCode(String[] args) {
		String email = args[0];
		// Allow to read from stdin
		String secret;
		if (args.length == 2) {
			secret = args[1];
		} else {
			secret = readPasswordFromStdin();
		}

		String app = "/usr/bin/node qrcode-terminal/bin/qrcode-terminal.js";
		try {
			QRCodeUtil.displayQRCode(app, email, secret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readPasswordFromStdin() {
		return String.valueOf(System.console().readPassword());
	}
}
