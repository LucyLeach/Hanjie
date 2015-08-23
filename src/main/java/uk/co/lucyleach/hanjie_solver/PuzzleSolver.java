package uk.co.lucyleach.hanjie_solver;

import uk.co.lucyleach.hanjie_solver.input.ClueReader_Spreadsheet;
import uk.co.lucyleach.hanjie_solver.output.PuzzleWriter_Spreadsheet;
import uk.co.lucyleach.hanjie_solver.solver.HanjieSolverImpl;
import uk.co.lucyleach.hanjie_solver.solver.UnsolvableException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

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
    HanjieSolver solver = new HanjieSolverImpl();

    Clues clues = new ClueReader_Spreadsheet().readInput(Paths.get(inputFilePath));

    Optional<Puzzle> puzzle;
    try
    {
      puzzle = Optional.of(solver.solve(clues));
    } catch (UnsolvableException e)
    {
      System.out.println(e.getMessage());
      puzzle = e.getPuzzle();
    }

    if (puzzle.isPresent())
    {
      try
      {
        new PuzzleWriter_Spreadsheet().writePuzzle(puzzle.get(), Paths.get(outputFilePath));
      } catch (IOException e)
      {
        System.out.println(e.getMessage());
      }
    }
    else
    {
      System.out.println("No puzzle was produced");
    }
  }
}
