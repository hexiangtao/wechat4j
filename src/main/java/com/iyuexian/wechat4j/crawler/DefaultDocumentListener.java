package com.iyuexian.wechat4j.crawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DefaultDocumentListener implements DocumentListener {

	private String host;
	private LinkCollection linkCollection;

	public DefaultDocumentListener(LinkCollection linkCollection, String host) {
		this.linkCollection = linkCollection;
		this.host = host;
	}

	@Override
	public void onDownload(String url, Document doc) {
		Logger.info("title:{}", doc.title());

		String line = "### [" + doc.title() + "](" + url + ")\n";

		try {
			Path path = Paths.get("D:/vqq.md");
			if (!path.toFile().exists()) {
				File f = new File("D:/vqq.md");
				f.createNewFile();
			}
			Files.write(Paths.get("D:/vqq.md"), line.getBytes(), StandardOpenOption.APPEND);
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
