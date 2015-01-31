package uk.co.lucyleach.hanjie_solver.input;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 31/01/2015
 * Time: 15:10
 */
public class ClueReader_Spreadsheet implements ClueReader<Path>
{
  @Override
  public Clues readInputWithoutCheck(Path input)
  {
    Workbook workbook;
    try
    {
      workbook = WorkbookFactory.create(input.toFile());
    } catch (IOException | InvalidFormatException e)
    {
      throw new IllegalArgumentException("Could not create workbook from input");
    }

    Sheet sheet = workbook.getSheetAt(0);
    Row topRow = sheet.getRow(0);
    if(topRow == null)
      throw new IllegalArgumentException("No rows in spreadsheet");

    int nextCellIndex = 1;
    Cell nextCell = topRow.getCell(nextCellIndex); //0-based, start at second cell
    List<String> columnCluesAsStrings = new ArrayList<>();
    while(nextCell != null && nextCell.getCellType() != Cell.CELL_TYPE_BLANK)
    {
      columnCluesAsStrings.add(getStringValue(nextCell));
      nextCellIndex++;
      nextCell = topRow.getCell(nextCellIndex);
    }
    Map<Integer, List<Integer>> columnClues = createClueMap(columnCluesAsStrings.stream());

    int nextRowIndex = 1;
    Row nextRow = sheet.getRow(nextRowIndex);
    List<String> rowCluesAsStrings = new ArrayList<>();
    while (nextRow != null && nextRow.getCell(0) != null && nextRow.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK)
    {
      rowCluesAsStrings.add(getStringValue(nextRow.getCell(0)));
      nextRowIndex++;
      nextRow = sheet.getRow(nextRowIndex);
    }
    Map<Integer, List<Integer>> rowClues = createClueMap(rowCluesAsStrings.stream());

    return new CluesImpl(rowClues, columnClues);
  }

  private static String getStringValue(Cell cell)
  {
    if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
      return Double.valueOf(cell.getNumericCellValue()).toString();
    if(cell.getCellType() == Cell.CELL_TYPE_STRING)
      return cell.getStringCellValue();
    else
      throw new IllegalArgumentException("Non-string/numeric cell in spreadsheet");
  }
}
