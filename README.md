GoogleAuthenticatorExport
=========================

Allow to export the secrets of google authenticator for Android

You need the databases file for google authenticator (may vary how to retrieve it), and put it at the root of the application

Start the application
---------------------

mvn exec:java -Dexec.mainClass=be.kokotchy.ExportSecrets -Dexec.args=test.db

It will display a list of url to QR code that can be scan by any devices
