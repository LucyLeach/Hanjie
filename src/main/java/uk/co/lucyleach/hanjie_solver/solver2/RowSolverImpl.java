package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lucy
 * Date: 16/09/2015
 * Time: 20:29
 */
public class RowSolverImpl extends RowSolver
{
  @Override
  public SolutionStepResult solveRow(List<Integer> clues, SquareState[] currentlyKnownState)
  {
    if(clues.size() != 1) {
      throw new IllegalArgumentException("Can't handle more than one block yet");
    }

    int firstFullSquare = -1;
    int lastFullSquare = -1;
    for(int i = 0; i < currentlyKnownState.length; i++) {
      SquareState state = currentlyKnownState[i];
      if(state.equals(SquareState.FULL)) {
        if(firstFullSquare < 0) {
          firstFullSquare = i;
        }
        lastFullSquare = i;
      }
    }

    int block = clues.get(0);

    int earliestStart;
    int latestStart;
    if (firstFullSquare < 0) //i.e. no full squares
    {
      earliestStart = 0;
      latestStart = currentlyKnownState.length - block;
    } else
    {
      earliestStart = withinArrayAndBlockLength(lastFullSquare - block + 1, currentlyKnownState.length, block);
      latestStart = withinArrayAndBlockLength(firstFullSquare, currentlyKnownState.length, block);
    }

    List<Integer> possibleStarts = new ArrayList<>();
    for(int potentialStart = earliestStart; potentialStart <= latestStart; potentialStart++) {
      boolean possible = true;
      for(int squareInBlock = 0; squareInBlock < block; squareInBlock++) {
        possible &= !currentlyKnownState[potentialStart + squareInBlock].equals(SquareState.BLANK);
      }
      if(possible) {
        possibleStarts.add(potentialStart);
      }
    }

    if(possibleStarts.isEmpty()) {
      throw new IllegalArgumentException("Damn, impossible situation");
    }

    SquareState[] newlyKnownState = currentlyKnownState.clone();
    for(int i = 0; i < newlyKnownState.length; i++) {
      boolean mustBeFilled = true;
      boolean cantBeFilled = true;
      for(int possibleStart: possibleStarts) {
        boolean filledIfBlockStartsHere = i >= possibleStart && i < possibleStart + block;
        mustBeFilled &= filledIfBlockStartsHere;
        cantBeFilled &= !filledIfBlockStartsHere;
      }

      if(mustBeFilled) {
        newlyKnownState[i] = SquareState.FULL;
      } else if (cantBeFilled) {
        newlyKnownState[i] = SquareState.BLANK;
      }
    }

    return new SolutionStepResult(currentlyKnownState, newlyKnownState);
  }

  private static int withinArrayAndBlockLength(int number, int length, int blockLength) {
    return Math.max(0, Math.min(number, length - blockLength));
  }

  @Override
  SolutionStepResult fillInKnownSquares(List<Integer> clues, int spareSquares, int length)
  {
    return solveRow(clues, SquareState.arrayOf(length, SquareState.UNKNOWN));
  }
}
