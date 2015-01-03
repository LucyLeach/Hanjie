package uk.co.lucyleach.hanjie_solver.output;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uk.co.lucyleach.hanjie_solver.Puzzle;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * User: Lucy
 * Date: 23/12/2014
 * Time: 14:54
 */
public class PuzzleWriter_Spreadsheet implements PuzzleWriter<Path>
{
  @Override
  public void writePuzzle(Puzzle puzzle, Path pathToOutput) throws IOException
  {
    if(puzzle == null)
      throw new NullPointerException("Need a puzzle to write");

    if(pathToOutput == null)
      throw new NullPointerException("Need a path to write to");

    Workbook workbook = new XSSFWorkbook();
    try(OutputStream outputStream = Files.newOutputStream(pathToOutput))
    {
      workbook.write(outputStream);
    }
  }
}
