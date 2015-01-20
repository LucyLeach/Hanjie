package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Map;
import java.util.Set;

/**
 * User: Lucy
 * Date: 19/01/2015
 * Time: 20:04
 */
interface PossibleSolutions
{
  Set<Map<Integer, SquareState>> getAllSolutions();

  Map<Integer, SquareState> getFixedSquares();

  boolean allSquaresFixed();

  int numberOfSolutions();
}
