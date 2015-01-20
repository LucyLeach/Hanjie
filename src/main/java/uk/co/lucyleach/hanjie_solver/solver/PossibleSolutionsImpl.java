package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.HashMap;
import java.util.HashSet;
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

  PossibleSolutionsImpl(Set<Map<Integer, SquareState>> allSolutions)
  {
    this.allSolutions = makeImmutable(allSolutions);
    this.fixedSquares = calculateFixedSquares(allSolutions);
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

  //TODO test, possibly abstract out
  private static Map<Integer, SquareState> calculateFixedSquares(Set<Map<Integer, SquareState>> allSolutions)
  {
    Map<Integer, Set<SquareState>> possibleStatesForEachSquare = new HashMap<>();
    for(Map<Integer, SquareState> solution: allSolutions)
    {
      for(Map.Entry<Integer, SquareState> entry: solution.entrySet())
      {
        if(!possibleStatesForEachSquare.containsKey(entry.getKey()))
          possibleStatesForEachSquare.put(entry.getKey(), new HashSet<>());

        possibleStatesForEachSquare.get(entry.getKey()).add(entry.getValue());
      }
    }

    ImmutableMap.Builder<Integer, SquareState> bob = ImmutableMap.builder();
    for(Map.Entry<Integer, Set<SquareState>> possibleSquareStates: possibleStatesForEachSquare.entrySet())
    {
      if(possibleSquareStates.getValue().size() == 1)
        bob.put(possibleSquareStates.getKey(), possibleSquareStates.getValue().iterator().next());
    }
    return bob.build();
  }
}
