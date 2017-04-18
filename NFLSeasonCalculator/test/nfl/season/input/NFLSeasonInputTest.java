package nfl.season.input;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
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
	}
	
	@Test
	public void asksForIntAndReturnsInt() {
		String input = 12 + "\n";
		inputStream = new ByteArrayInputStream(input.getBytes());
		nflSeasonInput = new NFLSeasonInput(inputStream, printStream);
		
		String message = "Asking for int";
		
		int returnInt = nflSeasonInput.askForInt(message);
		
		verify(printStream).println(message);
		assertEquals(12, returnInt);
	}
	
	@Test
	public void asksForStringAndReturnsString() {
		String input = "Dozen 12";
		inputStream = new ByteArrayInputStream(input.getBytes());
		nflSeasonInput = new NFLSeasonInput(inputStream, printStream);
		
		String message = "Asking for string";
		
		String returnString = nflSeasonInput.askForString(message);
		
		verify(printStream).println(message);
		assertEquals(input, returnString);
	}
	
	@Test
	public void asksForIntButGetsNonIntSoMessageIsRepeatedUntilIntIsInput() {
		String message = "message";
		
		String input = "Not an int\nAlso not an int\n56";
		inputStream = new ByteArrayInputStream(input.getBytes());
		nflSeasonInput = new NFLSeasonInput(inputStream, printStream);
		
		int returnInt = nflSeasonInput.askForInt(message);
		
		verify(printStream, times(3)).println(message);
		assertEquals(56, returnInt);
	}
	
	@Test
	public void asksForIntTwiceGetsOneIntThenNonIntThenIntSoEachIntReturned() {
		String message = "message";
		
		String input = "1\n3a\n3\n";
		inputStream = new ByteArrayInputStream(input.getBytes());
		nflSeasonInput = new NFLSeasonInput(inputStream, printStream);
		
		int returnInt1 = nflSeasonInput.askForInt(message);
		int returnInt2 = nflSeasonInput.askForInt(message);
		
		verify(printStream, times(3)).println(message);
		assertEquals(1, returnInt1);
		assertEquals(3, returnInt2);
	}
	
}
