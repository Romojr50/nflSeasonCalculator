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
			e.printStackTrace();
		}
		
		return scoreStripReturn;
	}

}
