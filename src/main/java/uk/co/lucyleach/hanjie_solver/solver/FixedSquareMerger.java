package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.Sets;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Collections2.filter;

/**
 * Eliminates solutions that don't agree with a given fixed square
 *
 * User: Lucy
 * Date: 19/01/2015
 * Time: 21:26
 */
class FixedSquareMerger
{
  OperationResult merge(int squarePosition, SquareState squareState, PossibleSolutions toMergeInto)
    throws UnsolvableException
  {
    OperationResult result;
    if(toMergeInto.getFixedSquares().containsKey(squarePosition))
    {
      if(squareState.equals(toMergeInto.getFixedSquares().get(squarePosition)))
        result = new OperationResult(false, toMergeInto);
      else
        throw new UnsolvableException();
    }
    else
    {
      Set<Map<Integer, SquareState>> allSolutions = toMergeInto.getAllSolutions();
      Set<Map<Integer, SquareState>> retainedSolutions = Sets.newHashSet(filter(allSolutions, (Map<Integer, SquareState> solution) -> squareState.equals(solution.get(squarePosition))));
      result = new OperationResult(true, new PossibleSolutionsImpl(retainedSolutions));
    }
    return result;
  }
}
