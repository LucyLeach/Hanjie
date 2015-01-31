package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.Puzzle;

import java.util.Optional;

/**
 * User: Lucy
 * Date: 07/01/2015
 * Time: 14:55
 */
public class UnsolvableException extends Exception
{
  private final Optional<Puzzle> puzzle;

  public UnsolvableException(String message, int row, int column, Puzzle puzzle)
  {
    super("Error in row " + row + " and column " + column + ": " + message);
    this.puzzle = Optional.of(puzzle);
  }

  public UnsolvableException(String message, Puzzle puzzle)
  {
    super(message);
    this.puzzle = Optional.of(puzzle);
  }

  public UnsolvableException()
  {
    super("No details of error available");
    this.puzzle = Optional.empty();
  }

  public Optional<Puzzle> getPuzzle()
  {
    return puzzle;
  }
}
