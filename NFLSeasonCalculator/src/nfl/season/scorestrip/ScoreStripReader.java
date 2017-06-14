package nfl.season.scorestrip;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ScoreStripReader {

	public Ss readScoreStripURL(String scoreStripURL) throws MalformedURLException {
		Ss scoreStripReturn = null;
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Ss.class);
			URL url = new URL(scoreStripURL);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			scoreStripReturn = (Ss) unmarshaller.unmarshal(url);
		} catch (JAXBException e) {
		}
		
		return scoreStripReturn;
	}

	public String generateScoreStripURL(String year, int weekNumber) {
		String url = "http://www.nfl.com/ajax/scorestrip?season=" + year + "&seasonType=REG&week=" + weekNumber;
		
		return url;
	}

}
