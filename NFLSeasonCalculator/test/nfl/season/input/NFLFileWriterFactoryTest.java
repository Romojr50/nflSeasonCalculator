package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.poi.ss.usermodel.Workbook;
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
		File file = new File(folderPath + "/" + NFLFileWriterFactory.NFL_SEASON_ESTIMATES_FILE_NAME);
		Files.deleteIfExists(file.toPath());
	}
	
	@Test
	public void fileWriterFactoryCreatesXSSFWorkbook() {
		Workbook workbook = fileWriterFactory.createNFLSeasonEstimatesWorkbook();
		assertNotNull(workbook);
	}
	
	@Test
	public void fileWriterFactoryReturnsEstimatesFilepath() {
		String folderPath = "someFolder";
		String expectedFilepath = folderPath + "/" + NFLFileIO.NFL_SEASON_ESTIMATES_FILE_NAME;
		String filepath = fileWriterFactory.getSeasonEstimatesFilepath(folderPath);
		assertEquals(expectedFilepath, filepath);
	}
	
}
