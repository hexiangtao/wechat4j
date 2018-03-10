package com.iyuexian.wechat4j.crawler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

	public static enum Level {
		DEBUG(1, "DEBUG"), INFO(2, "INFO"), WARN(3, "WARN"), ERROR(4, "ERROR"), NONE(5, "NONE");
		int level;
		String name;

		Level(int level, String name) {
			this.level = level;
			this.name = name;
		}

	}

	private static final Level current = Level.INFO;

	public static Logger getLogger() {
		return new Logger();
	}

	private static void log(String template, Level level, Object... args) {
		String current = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		String prefix = current + "-[" + level.name + "]-" + Thread.currentThread().getName() + " ";
		template = prefix + template.replaceAll("\\{\\}", "%s");
		System.out.println(String.format(template, args));
	}

	public static void error(String template, Object... args) {
		if (current.level <= Level.ERROR.level) {
			log(template, Level.ERROR, args);
		}
	}

	public static void warn(String template, Object... args) {
		if (current.level <= Level.WARN.level) {
			log(template, Level.WARN, args);
		}
	}

	public static void info(String template, Object... args) {
		if (current.level <= Level.INFO.level) {
			log(template, Level.INFO, args);
		}
	}

	public static void debug(String template, Object... args) {
		if (current.level <= Level.DEBUG.level) {
			log(template, Level.DEBUG, args);
		}
	}

}
