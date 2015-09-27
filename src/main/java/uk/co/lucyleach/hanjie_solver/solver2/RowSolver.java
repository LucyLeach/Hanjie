package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.List;

/**
 * User: Lucy
 * Date: 16/09/2015
 * Time: 20:28
 */
public abstract class RowSolver extends InitialRowSolver
{
  abstract SolutionStepResult solveRow(List<Integer> clues, SquareState[] currentlyKnownState);
}
