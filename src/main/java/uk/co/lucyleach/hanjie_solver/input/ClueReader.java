package uk.co.lucyleach.hanjie_solver.input;

import uk.co.lucyleach.hanjie_solver.Clues;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:42
 */
public interface ClueReader<I>
{
  Clues readInput(I input);
}
