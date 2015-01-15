package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Where the clues add up to the length of the row/column being assessed
 *
 * User: Lucy
 * Date: 07/01/2015
 * Time: 15:15
 */
public class FullyKnownStrategy implements Strategy
{
  @Override
  public Map<Integer, SquareState> solveRow(List<Integer> cluesForRow, Map<Integer, SquareState> row)
  {
    int rowSize = row.size();
    int sumOfClues = 0;
    for(int clue: cluesForRow)
    {
      sumOfClues += clue;
    }
    int lengthOfKnownSquares = sumOfClues + cluesForRow.size() - 1; //all the clues plus one space between each

    if(lengthOfKnownSquares == rowSize)
      return applyClues(cluesForRow);
    else
      return row;
  }

  private Map<Integer, SquareState> applyClues(List<Integer> clues)
  {
    Map<Integer, SquareState> row = new HashMap<>();
    for(int clue: clues)
    {
      int currentSize = row.size();
      for(int i = currentSize; i < currentSize + clue; i++)
      {
        row.put(i + 1, SquareState.FULL);
      }
      row.put(row.size() + 1, SquareState.BLANK);
    }

    row.remove(row.size());
    return row;
  }
}
