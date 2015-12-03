package chartgenerator.outputs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import chartgenerator.model.Links;

public class ArticleOutputTest {

	@Test
	public void testGetCsvString() {
		ArticleOutput output = new ArticleOutput(LocalDate.now(),
				"KG1SBCAR001-A1", Links.NO, new ArrayList<String>(),
				new ArrayList<String>(), 4, 0, 1, 0);
		assertEquals(
				"Article ID,LINKS,ANCHOR 1,ANCHOR 2,ANCHOR 3,ANCHOR 4,LINK 1,LINK 2,LINK 3,LINK 4,IMAGES,VIDEOS,AUDIOS"
						+ System.lineSeparator()
						+ "KG1SBCAR001-A1,NO,,,,,,,,,0,1,0"
						+ System.lineSeparator(),
				output.getCsvString(LocalDate.now()));

		output = new ArticleOutput(LocalDate.now(), "KG1SBCAR001-A1",
				Links.YES, Arrays.asList("1", "2", "3", "4"), Arrays.asList(
						"5", "6", "7", "8"), 4, 0, 1, 2);
		assertEquals(
				"Article ID,LINKS,ANCHOR 1,ANCHOR 2,ANCHOR 3,ANCHOR 4,LINK 1,LINK 2,LINK 3,LINK 4,IMAGES,VIDEOS,AUDIOS"
						+ System.lineSeparator()
						+ "KG1SBCAR001-A1,YES,1,2,3,4,5,6,7,8,0,1,2"
						+ System.lineSeparator(),
				output.getCsvString(LocalDate.now()));
	}
}
