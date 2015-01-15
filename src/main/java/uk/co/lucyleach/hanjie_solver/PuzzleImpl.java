package uk.co.lucyleach.hanjie_solver;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import uk.co.lucyleach.hanjie_solver.solver.UnsolvableException;

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
  public Puzzle updateRow(int rowKey, Map<Integer, SquareState> newRowState) throws UnsolvableException
  {
    ImmutableTable.Builder<Integer, Integer, SquareState> bob = ImmutableTable.builder();
    for(Table.Cell<Integer, Integer, SquareState> cell: states.cellSet())
    {
      if(cell.getRowKey() == rowKey && newRowState.containsKey(cell.getColumnKey()))
      {
        if(!SquareState.UNKNOWN.equals(cell.getValue()))
          throw new UnsolvableException("Tried to edit value to " + newRowState.get(cell.getColumnKey()) + " but value is already " + cell.getValue(), cell.getRowKey(), cell.getColumnKey(), this);
        else
          bob.put(cell.getRowKey(), cell.getColumnKey(), newRowState.get(cell.getColumnKey()));
      }
      else
      {
        bob.put(cell);
      }
    }
    return new PuzzleImpl(clues, bob.build());
  }

  @Override
  public Puzzle updateColumn(int columnKey, Map<Integer, SquareState> newColumnState) throws UnsolvableException
  {
    ImmutableTable.Builder<Integer, Integer, SquareState> bob = ImmutableTable.builder();
    for(Table.Cell<Integer, Integer, SquareState> cell: states.cellSet())
    {
      if(cell.getColumnKey() == columnKey && newColumnState.containsKey(cell.getRowKey()))
      {
        if(!SquareState.UNKNOWN.equals(cell.getValue()))
          throw new UnsolvableException("Tried to edit value to " + newColumnState.get(cell.getRowKey()) + " but value is already " + cell.getValue(), cell.getRowKey(), cell.getColumnKey(), this);
        else
          bob.put(cell.getRowKey(), cell.getColumnKey(), newColumnState.get(cell.getRowKey()));
      }
      else
      {
        bob.put(cell);
      }
    }
    return new PuzzleImpl(clues, bob.build());
  }
}
