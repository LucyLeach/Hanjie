package uk.co.lucyleach.hanjie_solver;

import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.solver.UnsolvableException;

/**
 * User: Lucy
 * Date: 23/08/2015
 * Time: 21:47
 */
public interface HanjieSolver
{
  Puzzle solve(Clues clues) throws UnsolvableException;
}
