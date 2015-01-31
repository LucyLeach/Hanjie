package uk.co.lucyleach.hanjie_solver.input;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class ClueReader_SpreadsheetTest extends AbstractClueReaderTest
{
  public ClueReader_SpreadsheetTest()
  {
    super(".xlsx", new ClueReader_Spreadsheet());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoColumnCluesFile() throws URISyntaxException
  {
    underTest.readInputWithoutCheck(Paths.get(this.getClass().getResource("no_column_clues" + fileEnding).toURI()));
  }
}