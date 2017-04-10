package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NFLSeasonInputTest {
	
	private NFLSeasonInput nflSeasonInput;
	
	private InputStream inputStream;
	
	@Mock
	private PrintStream printStream;
	
	@Before
	public void setUp() throws IOException {
		String input = "12";
		inputStream = new ByteArrayInputStream(input.getBytes());
		
		nflSeasonInput = new NFLSeasonInput(inputStream, printStream);
	}
	
	@Test
	public void asksForIntAndReturnsInt() {
		String message = "Asking for int";
		
		int returnInt = nflSeasonInput.askForInt(message);
		
		verify(printStream).println(message);
		assertEquals(12, returnInt);
	}
	
}
