package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Lucy
 * Date: 14/09/2015
 * Time: 21:32
 */
public class InitialRowSolver_Ranges extends InitialRowSolver
{
  @Override
  SolutionStepResult fillInKnownSquares(List<Integer> clues, int spareSquares, int length)
  {
    SquareState[] result = SquareState.arrayOf(length, SquareState.UNKNOWN);
    List<Integer> newlyKnownSquares = new ArrayList<>();

    int blockStart = 0;
    for(int block: clues) {
      int earliestEndExcl = blockStart + block;

      if(spareSquares < block) { //Can fill in squares
        int filledInStart = blockStart + spareSquares;
        for(int i = filledInStart; i < earliestEndExcl; i++) {
          result[i] = SquareState.FULL;
          newlyKnownSquares.add(i);
        }
      }

      if(spareSquares == 0 && earliestEndExcl < length) { //no spare squares and not last block
        result[earliestEndExcl] = SquareState.BLANK;
        newlyKnownSquares.add(earliestEndExcl);
      }

      blockStart = earliestEndExcl;
    }

    return new SolutionStepResult(result, newlyKnownSquares);
  }
}
