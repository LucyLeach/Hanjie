package uk.co.lucyleach.hanjie_solver.output;

import uk.co.lucyleach.hanjie_solver.Puzzle;

import java.io.IOException;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:44
 */
public interface PuzzleWriter<I>
{
  void writePuzzle(Puzzle puzzle, I extraInput) throws IOException;
}
