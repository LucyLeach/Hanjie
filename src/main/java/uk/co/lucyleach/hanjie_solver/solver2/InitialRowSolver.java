package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: Lucy
 * Date: 14/09/2015
 * Time: 21:30
 */
public abstract class InitialRowSolver
{
  public SolutionStepResult solve(List<Integer> clues, int length)
  {
    int numberOfFilledInSquares = clues.parallelStream().reduce(0, (a, b) -> a + b);
    int numberOfGaps = clues.isEmpty() ? length : clues.size() - 1;
    int totalLengthOfClues = numberOfFilledInSquares + numberOfGaps;

    if (totalLengthOfClues > length)
      throw new IllegalArgumentException("Clues " + clues.toString() + " don't fit in length " + length);

    if (clues.isEmpty())
      return new SolutionStepResult(SquareState.arrayOf(length, SquareState.BLANK), IntStream.range(0, length).boxed().collect(Collectors.toList()));

    int spareSquares = length - totalLengthOfClues;
    return fillInKnownSquares(clues, spareSquares, length);
  }

  abstract SolutionStepResult fillInKnownSquares(List<Integer> clues, int spareSquares, int length);
}
