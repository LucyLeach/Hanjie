package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.Lists;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.PuzzleImpl;

import java.util.List;

/**
 * User: Lucy
 * Date: 07/01/2015
 * Time: 14:58
 */
public class HanjieSolver
{
  private final List<Strategy> strategies;

  public HanjieSolver(List<Strategy> strategies)
  {
    this.strategies = Lists.newArrayList(strategies);
  }

  //TODO add tests - include all puzzles from strategy tests and one that it can't solve
  public Puzzle solve(Clues clues) throws UnsolvableException
  {
    Puzzle runningPuzzle = new PuzzleImpl(clues);
    for(Strategy strategy: strategies)
    {
      runningPuzzle = strategy.solve(runningPuzzle);
    }

    if(!runningPuzzle.isFullySolved())
      throw new UnsolvableException("Exhausted possible strategies and haven't found solution", runningPuzzle);

    return runningPuzzle;
  }
}
