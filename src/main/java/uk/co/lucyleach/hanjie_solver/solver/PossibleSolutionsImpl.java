package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Collections2.transform;

/**
 * User: Lucy
 * Date: 20/01/2015
 * Time: 10:50
 */
class PossibleSolutionsImpl implements PossibleSolutions
{
  private final Set<Map<Integer, SquareState>> allSolutions;
  private final Map<Integer, SquareState> fixedSquares;

  private static final FixedSquareCalculator FIXED_SQUARE_CALCULATOR = new FixedSquareCalculator();

  PossibleSolutionsImpl(Set<Map<Integer, SquareState>> allSolutions)
  {
    this.allSolutions = allSolutions;//makeImmutable(allSolutions);
    this.fixedSquares = FIXED_SQUARE_CALCULATOR.calculate(allSolutions);
  }

  @Override
  public Set<Map<Integer, SquareState>> getAllSolutions()
  {
    return allSolutions;
  }

  @Override
  public Map<Integer, SquareState> getFixedSquares()
  {
    return fixedSquares;
  }

  @Override
  public boolean allSquaresFixed()
  {
    return numberOfSolutions() == 1;
  }

  @Override
  public int numberOfSolutions()
  {
    return allSolutions.size();
  }

  private static Set<Map<Integer, SquareState>> makeImmutable(Set<Map<Integer, SquareState>> mutableMap)
  {
    return ImmutableSet.copyOf(transform(mutableMap, new Function<Map<Integer, SquareState>, Map<Integer, SquareState>>()
    {
      @Override
      public Map<Integer, SquareState> apply(Map<Integer, SquareState> mutableMap)
      {
        return ImmutableMap.copyOf(mutableMap);
      }
    }));
  }
}
