package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.transformValues;

/**
 * User: Lucy
 * Date: 31/01/2015
 * Time: 07:32
 */
public class FixedSquareCalculator
{
  public Map<Integer, SquareState> calculate(Set<Map<Integer, SquareState>> allSolutions)
  {
    Multimap<Integer, SquareState> allSolutionsForSquare = HashMultimap.create();
    allSolutions.forEach(solution -> solution.entrySet().forEach(entry -> allSolutionsForSquare.put(entry.getKey(), entry.getValue())));

    return transformValues(
        filterValues(allSolutionsForSquare.asMap(), coll -> coll.size() == 1),
        Iterables::getOnlyElement
    );
  }
}
