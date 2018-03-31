package com.iyuexian.wechat4j.plugin;

import com.iyuexian.wechat4j.config.Enums.OS;

public class QrUtil {

	public static OS getOsName() {
		String os = System.getProperty("os.name").toUpperCase();
		if (os.indexOf(OS.DARWIN.toString()) >= 0) {
			return OS.DARWIN;
		} else if (os.indexOf(OS.WINDOWS.toString()) >= 0) {
			return OS.WINDOWS;
		} else if (os.indexOf(OS.LINUX.toString()) >= 0) {
			return OS.LINUX;
		} else if (os.indexOf(OS.MAC.toString()) >= 0) {
			return OS.MAC;
		}
		return OS.OTHER;
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
