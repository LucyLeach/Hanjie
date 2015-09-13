package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: Lucy
 * Date: 13/09/2015
 * Time: 17:26
 */
public class InitialRowSolver
{
  public SolutionStepResult solve(List<Integer> clues, int length) {
    int numberOfFilledInSquares = clues.parallelStream().reduce(0, (a, b) -> a + b);
    int numberOfGaps = clues.isEmpty() ? length: clues.size() - 1;
    int totalLengthOfClues = numberOfFilledInSquares + numberOfGaps;

    if(totalLengthOfClues > length)
      throw new IllegalArgumentException("Clues " + clues.toString() + " don't fit in length " + length);

    if(clues.isEmpty())
      return new SolutionStepResult(SquareState.arrayOf(length, SquareState.BLANK), IntStream.range(0, length).boxed().collect(Collectors.toList()));

    int spareSquares = length - totalLengthOfClues;
    return fillInKnownSquares(clues, spareSquares);
  }

  private SolutionStepResult fillInKnownSquares(List<Integer> clues, int spareSquares)
  {
    SolutionStepResult stepResult;
    List<SquareState> resultList = new ArrayList<>();
    SquareState gapState = spareSquares == 0 ? SquareState.BLANK : SquareState.UNKNOWN;
    int currentSquare = 0;
    for(int clue: clues) {
      int firstSquareInBlock = currentSquare;
      int firstSquareAfterBlock = firstSquareInBlock + clue;
      if (clue > spareSquares)
      {
        int firstKnownSquareInBlock = currentSquare + spareSquares;
        IntStream.range(firstSquareInBlock, firstKnownSquareInBlock).forEach(i -> resultList.add(SquareState.UNKNOWN));
        IntStream.range(firstKnownSquareInBlock, firstSquareAfterBlock).forEach(i -> resultList.add(SquareState.FULL));
      } else {
        IntStream.range(firstSquareInBlock, firstSquareAfterBlock).forEach(i -> resultList.add(SquareState.UNKNOWN));
      }
      resultList.add(gapState);
      currentSquare = firstSquareAfterBlock + 1;
    }
    resultList.remove(resultList.size() - 1);
    IntStream.range(0, spareSquares).forEach(i -> resultList.add(SquareState.UNKNOWN));

    List<Integer> newlyKnownSquares = new ArrayList<>();
    for(int i = 0; i < resultList.size(); i++) {
      if(!SquareState.UNKNOWN.equals(resultList.get(i)))
        newlyKnownSquares.add(i);
    }

    stepResult = new SolutionStepResult(resultList.toArray(new SquareState[resultList.size()]), newlyKnownSquares);
    return stepResult;
  }

}
