package uk.co.lucyleach.hanjie_solver;

import com.google.common.collect.Table;

/**
 * User: Lucy
 * Date: 23/12/2014
 * Time: 15:02
 */
public class PuzzleImpl implements Puzzle
{
  private final Clues clues;
  private final Table<Integer, Integer, SquareState> states;

  public PuzzleImpl(Clues clues, Table<Integer, Integer, SquareState> states)
  {
    this.clues = clues;
    this.states = states;
  }

  @Override
  public Clues getClues()
  {
    return clues;
  }

  @Override
  public Table<Integer, Integer, SquareState> getStates()
  {
    //TODO check if Table is mutable
    return states;
  }
}
