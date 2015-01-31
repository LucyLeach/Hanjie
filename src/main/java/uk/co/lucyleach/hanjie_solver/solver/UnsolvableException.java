package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.Puzzle;

/**
 * User: Lucy
 * Date: 07/01/2015
 * Time: 14:55
 */
public class UnsolvableException extends Exception
{
  private final Puzzle puzzle;

  public UnsolvableException(String message, int row, int column, Puzzle puzzle)
  {
    super("Error in row " + row + " and column " + column + ": " + message);
    this.puzzle = puzzle;
  }

  public UnsolvableException(String message, Puzzle puzzle)
  {
    super(message);
    this.puzzle = puzzle;
  }

  public Puzzle getPuzzle()
  {
    return puzzle;
  }
}
