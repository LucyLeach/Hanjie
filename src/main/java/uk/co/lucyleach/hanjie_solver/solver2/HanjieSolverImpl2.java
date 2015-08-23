package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.HanjieSolver;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.solver.UnsolvableException;

/**
 * User: Lucy
 * Date: 23/08/2015
 * Time: 21:46
 */
public class HanjieSolverImpl2 implements HanjieSolver
{
  @Override
  public Puzzle solve(Clues clues) throws UnsolvableException
  {
    //Any rows/columns already known?
    //If yes, fill them in
    //If no, find one most likely to yield good results (large number of known squares, near the edge)
    //Keep track of altered columns/rows, fill in what you can on those, keep track of what you've changed etc (alternate columns, rows)
    //If get stuck, start again

    return null;
  }
}
