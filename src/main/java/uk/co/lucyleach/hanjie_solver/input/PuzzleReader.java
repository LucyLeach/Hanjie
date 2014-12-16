package uk.co.lucyleach.hanjie_solver.input;

import uk.co.lucyleach.hanjie_solver.Puzzle;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:43
 */
public interface PuzzleReader<I>
{
  Puzzle readPuzzle(I input);
}
