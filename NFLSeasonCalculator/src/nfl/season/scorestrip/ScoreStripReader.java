package nfl.season.scorestrip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class ScoreStripReader {

	public Ss readScoreStripURL(String scoreStripURL) throws MalformedURLException {
		Ss scoreStripReturn = null;
		
		Serializer serializer = new Persister();
		URL url = new URL(scoreStripURL);

		try {
	        String xml = getXMLFromURL(url);

			scoreStripReturn = serializer.read(Ss.class, xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return scoreStripReturn;
	}

	public String generateScoreStripURL(String year, int weekNumber) {
		String url = "http://www.nfl.com/ajax/scorestrip?season=" + year + "&seasonType=REG&week=" + weekNumber;
		
		return url;
	}

	private String getXMLFromURL(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		
		StringBuilder response = new StringBuilder();
		String inputLine;
		
		while ((inputLine = in.readLine()) != null) 
			response.append(inputLine);
		
		in.close();
		
		String xml = response.toString();
		int indexOfFirstEndBracket = xml.indexOf('>');
		if (indexOfFirstEndBracket > -1) {
			xml = xml.substring(indexOfFirstEndBracket + 1);
		}
		return xml;
	}
	
}
