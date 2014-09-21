package de.schoar.braveintelserver.eveapi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import de.schoar.braveintelserver.misc.TimerHelper;

abstract public class EveDownloader extends TimerHelper {

	private static SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss zzz");

	private String url;
	private long cachedUntil = 0;

	public EveDownloader(String url) {
		this.url = url;
		timerInit(1000 * 10, 1000 * 60);
	}

	@Override
	protected void timerTick() {
		if (System.currentTimeMillis() < cachedUntil) {
			return;
		}
		System.err.println("Download " + url);

		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(url);

			String dateString = doc.getElementsByTagName("cachedUntil").item(0)
					.getTextContent()
					+ " UTC";
			cachedUntil = SDF.parse(dateString).getTime();

			data(doc);
		} catch (ParseException pe) {
			pe.printStackTrace();
		} catch (DOMException de) {
			de.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	abstract protected void data(Document doc);

}
