package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableMap;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Lucy
 * Date: 31/01/2015
 * Time: 07:32
 */
public class FixedSquareCalculator
{
  //Goes through all solutions looking for squares which can only have one value
  public Map<Integer, SquareState> calculate(Set<Map<Integer, SquareState>> allSolutions)
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
    possibleStatesForEachSquare.entrySet().stream()
        .filter(possibleSquareStates -> possibleSquareStates.getValue().size() == 1)
        .forEach(possibleSquareStates -> bob.put(possibleSquareStates.getKey(), possibleSquareStates.getValue().iterator().next()));
    return bob.build();
  }
}
