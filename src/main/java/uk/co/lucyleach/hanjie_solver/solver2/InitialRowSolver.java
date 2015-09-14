package uk.co.lucyleach.hanjie_solver.solver2;

import java.util.List;

/**
 * User: Lucy
 * Date: 14/09/2015
 * Time: 21:30
 */
public interface InitialRowSolver
{
  SolutionStepResult solve(List<Integer> clues, int length);
}
