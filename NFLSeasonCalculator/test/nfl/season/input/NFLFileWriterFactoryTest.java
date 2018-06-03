package nfl.season.input;

import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
	public void fileWriterFactoryCreatesNFLSeasonEstimatesWriter() throws FileNotFoundException {
		FileOutputStream outputStream = fileWriterFactory.createNFLSeasonEstimatesWriter("someFolder");
		assertNotNull(outputStream);
	}
	
	@Test
	public void fileWriterFactoryCreatesXSSFWorkbook() {
		Workbook workbook = fileWriterFactory.createNFLSeasonEstimatesWorkbook();
		assertNotNull(workbook);
	}
	
}
