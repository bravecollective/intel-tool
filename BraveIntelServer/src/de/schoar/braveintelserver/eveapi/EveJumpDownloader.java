package de.schoar.braveintelserver.eveapi;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EveJumpDownloader extends EveDownloader {

	private Map<String, Integer> jumps = new HashMap<String, Integer>();

	public EveJumpDownloader() {
		super("https://api.eveonline.com/Map/Jumps.xml.aspx");
	}

	public Map<String, Integer> getJumps() {
		return new HashMap<String, Integer>(jumps);
	}

	@Override
	protected void data(Document doc) {
		Map<String, Integer> jumpsNew = new HashMap<String, Integer>();

		NodeList nl = doc.getElementsByTagName("row");
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);

			String sid = n.getAttributes().getNamedItem("solarSystemID")
					.getNodeValue();
			Integer jumps = Integer.valueOf(n.getAttributes()
					.getNamedItem("shipJumps").getNodeValue());
			jumpsNew.put(sid, jumps);
		}

		jumps = jumpsNew;
	}

}
