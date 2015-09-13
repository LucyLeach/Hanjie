package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

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

  public SquareState[] getStepResult()
  {
    return stepResult;
  }

  public List<Integer> getNewlyKnownSquares()
  {
    return newlyKnownSquares;
  }
}
