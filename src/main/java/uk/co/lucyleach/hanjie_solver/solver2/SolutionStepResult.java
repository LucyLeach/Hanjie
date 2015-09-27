package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lucy
 * Date: 13/09/2015
 * Time: 17:27
 */
public class SolutionStepResult
{
  private final SquareState[] stepResult;
  private final List<Integer> newlyKnownSquares;

  public SolutionStepResult(SquareState[] stepResult, List<Integer> newlyKnownSquares)
  {
    this.stepResult = stepResult;
    this.newlyKnownSquares = newlyKnownSquares;
  }

  public SolutionStepResult(SquareState[] before, SquareState[] after) {
    this.stepResult = after;
    this.newlyKnownSquares = changedSquares(before, after);
  }

  public SquareState[] getStepResult()
  {
    return stepResult;
  }

  public List<Integer> getNewlyKnownSquares()
  {
    return newlyKnownSquares;
  }

  private static List<Integer> changedSquares(SquareState[] before, SquareState[] after) {
    List<Integer> changedSquares = new ArrayList<>();
    for(int i = 0; i < before.length; i++) {
      if(!before[i].equals(after[i]))
      {
        changedSquares.add(i);
      }
    }
    return changedSquares;
  }
}
