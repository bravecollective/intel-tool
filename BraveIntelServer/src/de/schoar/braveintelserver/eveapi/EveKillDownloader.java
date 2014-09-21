package de.schoar.braveintelserver.eveapi;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EveKillDownloader extends EveDownloader {

	private Map<String, KillData> kills = new HashMap<String, KillData>();

	public EveKillDownloader() {
		super("https://api.eveonline.com/Map/Kills.xml.aspx");
	}

	public Map<String, KillData> getKills() {
		return new HashMap<String, KillData>(kills);
	}

	@Override
	protected void data(Document doc) {
		Map<String, KillData> killsNew = new HashMap<String, KillData>();

		NodeList nl = doc.getElementsByTagName("row");
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);

			String sid = n.getAttributes().getNamedItem("solarSystemID")
					.getNodeValue();
			int ships = Integer.valueOf(
					n.getAttributes().getNamedItem("shipKills").getNodeValue())
					.intValue();
			int pods = Integer.valueOf(
					n.getAttributes().getNamedItem("podKills").getNodeValue())
					.intValue();
			int rats = Integer.valueOf(
					n.getAttributes().getNamedItem("factionKills")
							.getNodeValue()).intValue();

			killsNew.put(sid, new KillData(ships, pods, rats));
		}

		kills = killsNew;
	}

}
