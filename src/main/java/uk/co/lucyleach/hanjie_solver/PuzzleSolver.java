package uk.co.lucyleach.hanjie_solver;

import uk.co.lucyleach.hanjie_solver.input.ClueReader_Text;
import uk.co.lucyleach.hanjie_solver.output.PuzzleWriter_Spreadsheet;
import uk.co.lucyleach.hanjie_solver.solver.HanjieSolver;
import uk.co.lucyleach.hanjie_solver.solver.UnsolvableException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * User: Lucy
 * Date: 20/01/2015
 * Time: 16:12
 */
public class PuzzleSolver
{
  public static void main(String[] args)
  {
    String inputFilePath = args[0];
    String outputFilePath = args[1];
    HanjieSolver solver = new HanjieSolver();

    Clues clues = new ClueReader_Text().readInput(Paths.get(inputFilePath));

    Puzzle puzzle;
    try
    {
      puzzle = solver.solve(clues);
    } catch (UnsolvableException e)
    {
      System.out.println(e.getMessage());
      puzzle = e.getPuzzle();
    }

    try
    {
      new PuzzleWriter_Spreadsheet().writePuzzle(puzzle, Paths.get(outputFilePath));
    } catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }
}