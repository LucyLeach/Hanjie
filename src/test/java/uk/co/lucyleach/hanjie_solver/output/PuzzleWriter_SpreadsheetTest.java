package uk.co.lucyleach.hanjie_solver.output;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import uk.co.lucyleach.hanjie_solver.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static uk.co.lucyleach.hanjie_solver.SquareState.*;

public class PuzzleWriter_SpreadsheetTest
{
  private static PuzzleWriter<Path> UNDER_TEST = new PuzzleWriter_Spreadsheet();

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test(expected = NullPointerException.class)
  public void testNullPuzzle() throws URISyntaxException, IOException
  {
    UNDER_TEST.writePuzzle(null, getTestPath());
  }

  @Test(expected = NullPointerException.class)
  public void testNullPath() throws IOException
  {
    UNDER_TEST.writePuzzle(createTestPuzzle(), null);
  }

  @Test
  public void testWritesReadableSheetToCorrectDir() throws IOException, URISyntaxException
  {
    Path testFilePath = getTestPath();
    Files.deleteIfExists(testFilePath);
    UNDER_TEST.writePuzzle(createTestPuzzle(), testFilePath);
    assertTrue("No file at location", Files.exists(testFilePath));

    try
    {
      Workbook workbook = WorkbookFactory.create(testFilePath.toFile());
      assertNotNull("Workbook shouldn't be null", workbook);
    } catch (InvalidFormatException|IOException e)
    {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  private static Puzzle createTestPuzzle()
  {
    ImmutableMap<Integer, List<Integer>> columnClues = ImmutableMap.of(
        1, newArrayList(1, 1),
        2, newArrayList(2),
        3, newArrayList(1));
    ImmutableMap<Integer, List<Integer>> rowClues = ImmutableMap.of(
        1, newArrayList(3),
        2, newArrayList(1),
        3, newArrayList(1)
    );
    Clues clues = new CluesImpl(rowClues, columnClues);

    Table<Integer, Integer, SquareState> states = new ImmutableTable.Builder<Integer, Integer, SquareState>()
        .put(1, 1, FULL)
        .put(1, 2, FULL)
        .put(1, 3, FULL)
        .put(2, 1, BLANK)
        .put(2, 2, FULL)
        .put(2, 3, UNKNOWN)
        .put(3, 1, FULL)
        .put(3, 2, BLANK)
        .put(3, 3, UNKNOWN)
        .build();

    return new PuzzleImpl(clues, states);
  }

  private Path getTestPath() throws URISyntaxException
  {
    return Paths.get(tempFolder.getRoot().toURI()).resolve("test_output.xlsx");
  }
}