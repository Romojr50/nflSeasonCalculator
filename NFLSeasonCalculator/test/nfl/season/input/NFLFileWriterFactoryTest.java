package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class NFLFileWriterFactoryTest {

	private NFLFileWriterFactory fileWriterFactory;
	
	@Before
	public void setUp() {
		fileWriterFactory = new NFLFileWriterFactory();
	}
	
	@Test
	public void fileWriterFactoryCreatesFileWriter() throws FileNotFoundException {
		FileOutputStream outputStream = fileWriterFactory.createNFLTeamSettingsWriter();
		assertNotNull(outputStream);
	}
	
	@Test
	public void fileWriterFactoryCreatesNFLSeasonEstimatesWriter() throws IOException {
		String folderPath = "someFolder";
		FileOutputStream outputStream = fileWriterFactory.createNFLSeasonEstimatesWriter(folderPath);
		assertNotNull(outputStream);
		
		outputStream.close();
		File testFile = new File(folderPath + "/" + NFLFileIO.NFL_SEASON_ESTIMATES_FILE_NAME);
		testFile.delete();
	}
	
	@Test
	public void fileWriterFactoryReturnsEstimatesFilepath() {
		String folderPath = "someFolder";
		String expectedFilepath = folderPath + "/" + NFLFileIO.NFL_SEASON_ESTIMATES_FILE_NAME;
		String filepath = fileWriterFactory.getSeasonEstimatesFilepath(folderPath);
		assertEquals(expectedFilepath, filepath);
	}
	
	@Test
	public void fileWriterFactoryReturnsEstimatesFilename() {
		String filename = fileWriterFactory.getSeasonEstimatesFilename();
		assertEquals(NFLFileIO.NFL_SEASON_ESTIMATES_FILE_NAME, filename);
	}
	
}
