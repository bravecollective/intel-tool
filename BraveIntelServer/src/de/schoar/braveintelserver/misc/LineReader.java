package de.schoar.braveintelserver.misc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class LineReader {

	public boolean load(String name, String file) {
		System.out.print("Loading: " + name + " ... ");
		if (file == null) {
			System.out.println("not found!");
			return false;
		}

		try {
			return readlines(name, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println("not found!");
			return false;
		}
	}

	public boolean load(String name, InputStream is) {
		System.out.print("Loading: " + name + " ... ");
		if (is == null) {
			System.out.println("not found!");
			return false;
		}
		return readlines(name, is);
	}

	private boolean readlines(String name, InputStream is) {
		long count = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0) {
					continue;
				}
				if (line.startsWith("#")) {
					continue;
				}
				if (line.startsWith("//")) {
					continue;
				}
				count++;
				line(line);
			}
			br.close();
			System.out.println(count + " records");
			return true;
		} catch (IOException e) {
			System.out.println("failed: " + e.getMessage());
			return false;
		}
	}

	public abstract void line(String line);
}
