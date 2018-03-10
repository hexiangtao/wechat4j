package com.iyuexian.wechat4j.crawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DefaultDocumentListener implements DocumentListener {

	private String host;
	private String savePath;

	public DefaultDocumentListener(String host) {
		this(host, null);
	}

	public DefaultDocumentListener(String host, String savePath) {
		this.host = host;
		this.savePath = savePath;
	}

	@Override
	public void onDownload(String url, Document doc, LinkCollection linkCollection) {
		Logger.info("title:{}", doc.title());

		try {
			if (this.savePath != null && this.savePath.trim().length() > 0) {
				String line = "### [" + doc.title() + "](" + url + ")\n";
				File f = new File(savePath);
				if (!f.exists()) {
					f.createNewFile();
				}
				Files.write(f.toPath(), line.getBytes(), StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Elements links = doc.select("a[href]");
		for (Element element : links) {
			String link = element.attr("abs:href").trim();
			if (link == null || link.trim().length() == 0 || !link.contains(host) || linkCollection.isFetched(link)) {
				continue;
			}
			Logger.debug("入列:{}", link);
			linkCollection.offer(link);
		}
	}

	public void parseContent(Document doc) {
		Elements elements = doc.select("tr");
		for (Element tr : elements) {
			Elements td = tr.select("td[bgcolor=#FFFFFF] a");
			String name = "", code = "";
			if (td != null && td.size() >= 1) {
				name = td.get(0).html();
			}
			if (td != null && td.size() >= 2) {
				code = td.get(1).html();
			}
			Logger.info("地区:{},编码:{}", name, code);

		}

	}
}
