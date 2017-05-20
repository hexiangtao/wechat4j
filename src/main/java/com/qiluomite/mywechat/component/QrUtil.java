package com.qiluomite.mywechat.component;

public class QrUtil {

	public static OsName getOsName() {
		String os = System.getProperty("os.name").toUpperCase();
		if (os.indexOf(OsName.DARWIN.toString()) >= 0) {
			return OsName.DARWIN;
		} else if (os.indexOf(OsName.WINDOWS.toString()) >= 0) {
			return OsName.WINDOWS;
		} else if (os.indexOf(OsName.LINUX.toString()) >= 0) {
			return OsName.LINUX;
		} else if (os.indexOf(OsName.MAC.toString()) >= 0) {
			return OsName.MAC;
		}
		return OsName.OTHER;
	}

	public static void printQr(String qrPath) throws Exception {
		String cmd = "";
		switch (getOsName()) {
		case WINDOWS: {
			cmd = " cmd /c start  ";
			break;
		}
		case MAC:
			cmd = " open ";
			break;
		default:
			throw new RuntimeException("operation not supported");
		}
		Runtime runtime = Runtime.getRuntime();
		runtime.exec(cmd + qrPath);
	}
}
