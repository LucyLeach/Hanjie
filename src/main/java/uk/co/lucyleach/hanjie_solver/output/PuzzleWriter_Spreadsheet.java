package uk.co.lucyleach.hanjie_solver.output;

import com.google.common.base.Joiner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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
    Sheet sheet = workbook.createSheet();

    int cellSize = 30;

    Row topRow = sheet.createRow(0);
    for(Map.Entry<Integer, List<Integer>> entry: puzzle.getClues().getColumnClues().entrySet())
    {
      topRow.createCell(entry.getKey()).setCellValue(Joiner.on(",").join(entry.getValue()));
      sheet.setColumnWidth(entry.getKey(), cellSize * 50);
    }

    for(Map.Entry<Integer, List<Integer>> entry: puzzle.getClues().getRowClues().entrySet())
    {
      Row row = sheet.createRow(entry.getKey());
      row.createCell(0).setCellValue(Joiner.on(",").join(entry.getValue()));
      for(Integer cellKey: puzzle.getClues().getColumnClues().keySet())
      {
        row.createCell(cellKey).setCellStyle(getCellStyleForState(puzzle.getStates().get(entry.getKey(), cellKey), workbook));
      }
      row.setHeightInPoints(cellSize);
    }

    try(OutputStream outputStream = Files.newOutputStream(pathToOutput))
    {
      workbook.write(outputStream);
    }
  }

  private static CellStyle getCellStyleForState(SquareState state, Workbook workbook)
  {
    if(state == null)
      state = SquareState.BLANK;
    CellStyle cellStyle = workbook.createCellStyle();
    switch (state)
    {
      case BLANK:
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        break;
      case FULL:
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        break;
      //Unknown just stays default style
    }
    return cellStyle;
  }
}
