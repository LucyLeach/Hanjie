package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * User: Lucy
 * Date: 13/09/2015
 * Time: 16:38
 */
public class KnowableSquareCalculator
{
  int calculate(int length, List<Integer> clues)
  {
    SquareState[] knownState = new SquareState[length];
    Arrays.fill(knownState, SquareState.UNKNOWN);
    return calculate(clues, knownState);
  }

  int calculate(List<Integer> clues, SquareState[] knownState) {
    int knownSquares = Arrays.stream(knownState).map(isUnknown()).reduce(0, sumInts());

    int knowableSquares;
    if (clues.isEmpty())
    {
      knowableSquares = knownSquares;
    }
    else if(knownSquares == 0)
    {
      int numberOfFilledInSquares = clues.parallelStream().reduce(0, sumInts());
      int numberOfGaps = clues.size() - 1;
      int totalLengthOfClues = numberOfFilledInSquares + numberOfGaps;
      int spareSquares = knownState.length - totalLengthOfClues;
      if (spareSquares > 0)
      {
        knowableSquares = clues.parallelStream().reduce(0, (totalSoFar, newClue) -> totalSoFar + Math.max(0, newClue - spareSquares));
      }
      else
      {
        knowableSquares = knownState.length;
      }
    }
    else {
      throw new IllegalArgumentException("Haven't coded path where squares are known"); //TODO
    }
    return knowableSquares;
  }

  private static BinaryOperator<Integer> sumInts()
  {
    return (a, b) -> a + b;
  }

  private static Function<SquareState, Integer> isUnknown()
  {
    return a -> a.equals(SquareState.UNKNOWN) ? 0 : 1;
  }
}
