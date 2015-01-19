package uk.co.lucyleach.hanjie_solver.solver;

/**
 * User: Lucy
 * Date: 19/01/2015
 * Time: 21:28
 */
class OperationResult
{
  private final boolean hasChanged;
  private final PossibleSolutions solutions;

  public OperationResult(boolean hasChanged, PossibleSolutions solutions)
  {
    this.hasChanged = hasChanged;
    this.solutions = solutions;
  }

  public boolean hasChanged()
  {
    return hasChanged;
  }

  public PossibleSolutions getSolutions()
  {
    return solutions;
  }
}
