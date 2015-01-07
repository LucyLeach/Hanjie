package uk.co.lucyleach.hanjie_solver;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import java.util.Map;

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

  public PuzzleImpl(Clues clues)
  {
    this.clues = clues;

    ImmutableTable.Builder<Integer, Integer, SquareState> bob = ImmutableTable.builder();
    for(int rowIndex: clues.getRowClues().keySet())
    {
      for(int columnIndex: clues.getColumnClues().keySet())
      {
        bob.put(rowIndex, columnIndex, SquareState.UNKNOWN);
      }
    }
    this.states = bob.build();
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

  @Override
  public boolean isFullySolved()
  {
    boolean hasUnknownSquare = false;
    for(SquareState state: states.values())
    {
      if(SquareState.UNKNOWN.equals(state))
        hasUnknownSquare = true;
    }

    return !hasUnknownSquare;
  }

  @Override
  public Puzzle updateRow(int rowKey, Map<Integer, SquareState> newRowState)
  {
    ImmutableTable.Builder<Integer, Integer, SquareState> bob = ImmutableTable.builder();
    bob.putAll(states);
    for(Map.Entry<Integer, SquareState> entry: newRowState.entrySet())
    {
      bob.put(rowKey, entry.getKey(), entry.getValue());
    }
    return new PuzzleImpl(clues, bob.build());
  }

  @Override
  public Puzzle updateColumn(int columnKey, Map<Integer, SquareState> newColumnState)
  {
    ImmutableTable.Builder<Integer, Integer, SquareState> bob = ImmutableTable.builder();
    bob.putAll(states);
    for(Map.Entry<Integer, SquareState> entry: newColumnState.entrySet())
    {
      bob.put(entry.getKey(), columnKey, entry.getValue());
    }
    return new PuzzleImpl(clues, bob.build());
  }
}
