package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.HanjieSolver;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.SquareState;
import uk.co.lucyleach.hanjie_solver.solver.UnsolvableException;

import java.util.Arrays;
import java.util.List;

/**
 * User: Lucy
 * Date: 23/08/2015
 * Time: 21:46
 */
public class HanjieSolverImpl2 implements HanjieSolver
{
  private final ClueHelpfulnessSorter helpfulnessSorter = new ClueHelpfulnessSorter();

  @Override
  public Puzzle solve(Clues clues) throws UnsolvableException
  {
    SquareState[][] solutionGrid = createUnknownInitialGrid(clues);

    //Find row/column most likely to yield good results (large number of known squares, near the edge)
    ClueHelpfulnessScore bestStartingPoint = helpfulnessSorter.bestClue(clues);
    if(bestStartingPoint.isRow()) {
      //is complicated
    } else {
      int colNumber = bestStartingPoint.getNumber();
      List<Integer> columnClues = clues.getColumnClues().get(colNumber);
      SquareState[] column = solutionGrid[colNumber];
    }
    //Keep track of altered columns/rows, fill in what you can on those, keep track of what you've changed etc (alternate columns, rows)
    //If get stuck, start again

    return null;
  }

  private SquareState[][] createUnknownInitialGrid(Clues clues) {
    SquareState[][] grid = new SquareState[clues.getRowLength()][];
    for(int i = 0; i < clues.getRowLength(); i++) {
      SquareState[] column = new SquareState[clues.getColumnLength()];
      Arrays.fill(column, SquareState.UNKNOWN);
      grid[i] = column;
    }
    return grid;
  }

  private List<Integer> fillInKnownSquares(List<Integer> clues, SquareState[] column) {
    return null; //TODO
  }
}
