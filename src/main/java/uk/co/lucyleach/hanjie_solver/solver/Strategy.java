package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 07/01/2015
 * Time: 14:53
 */
public interface Strategy
{
  default Puzzle solve(Puzzle puzzle) throws UnsolvableException
  {
    Clues clues = puzzle.getClues();

    Puzzle runningPuzzle = puzzle;
    for(Map.Entry<Integer, List<Integer>> rowClues: clues.getRowClues().entrySet())
    {
      int rowKey = rowClues.getKey();
      Map<Integer, SquareState> row = puzzle.getStates().row(rowKey);
      Map<Integer, SquareState> solvedRow = solveRow(rowClues.getValue(), row);
      runningPuzzle = puzzle.updateRow(rowKey, solvedRow);
    }

    for(Map.Entry<Integer, List<Integer>> columnClues: clues.getColumnClues().entrySet())
    {
      int columnKey = columnClues.getKey();
      Map<Integer, SquareState> column = puzzle.getStates().column(columnKey);
      Map<Integer, SquareState> solvedColumn = solveRow(columnClues.getValue(), column);
      runningPuzzle = puzzle.updateColumn(columnKey, solvedColumn);
    }

    return runningPuzzle;
  }

  //Calls things a "row" but interchangeable with columns
  Map<Integer, SquareState> solveRow(List<Integer> cluesForRow, Map<Integer, SquareState> row);
}
