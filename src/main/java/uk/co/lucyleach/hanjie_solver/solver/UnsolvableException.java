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
  private final Optional<Integer> row;
  private final Optional<Integer> column;

  public UnsolvableException(String message, int row, int column, Puzzle puzzle)
  {
    super("Error in row " + row + " and column " + column + ": " + message);
    this.puzzle = Optional.of(puzzle);
    this.row = Optional.of(row);
    this.column = Optional.of(column);
  }

  public UnsolvableException(String message, Puzzle puzzle)
  {
    super(message);
    this.puzzle = Optional.of(puzzle);
    this.row = Optional.empty();
    this.column = Optional.empty();
  }

  public UnsolvableException()
  {
    super("No details of error available");
    this.puzzle = Optional.empty();
    this.row = Optional.empty();
    this.column = Optional.empty();
  }

  public Optional<Puzzle> getPuzzle()
  {
    return puzzle;
  }

  public Optional<Integer> getRow()
  {
    return row;
  }

  public Optional<Integer> getColumn()
  {
    return column;
  }
}
