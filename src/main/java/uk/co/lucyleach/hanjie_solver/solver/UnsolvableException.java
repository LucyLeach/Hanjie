package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.Puzzle;

/**
 * User: Lucy
 * Date: 07/01/2015
 * Time: 14:55
 */
public class UnsolvableException extends Exception
{
  //TODO add nullable annotations when you have access to the internet again
  private final Integer row;
  private final Integer column;
  private final Puzzle puzzle;

  public UnsolvableException(String message, int row, int column, Puzzle puzzle)
  {
    super("Error in row " + row + " and column " + column + ": " + message);
    this.row = row;
    this.column = column;
    this.puzzle = puzzle;
  }

  public UnsolvableException(String message, Puzzle puzzle)
  {
    super(message);
    this.row = null;
    this.column = null;
    this.puzzle = puzzle;
  }

  public Integer getRow()
  {
    return row;
  }

  public Integer getColumn()
  {
    return column;
  }

  public Puzzle getPuzzle()
  {
    return puzzle;
  }
}
