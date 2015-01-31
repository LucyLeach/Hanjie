package uk.co.lucyleach.hanjie_solver.input;

import uk.co.lucyleach.hanjie_solver.Clues;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User: Lucy
 * Date: 31/01/2015
 * Time: 14:35
 */
public class ClueConsistencyChecker
{
  public ConsistencyCheckResult check(Clues clues)
  {
    return new ConsistencyCheckResult(
        getEntriesThatAreTooLarge(clues.getRowClues(), clues.getColumnLength()),
        getEntriesThatAreTooLarge(clues.getColumnClues(), clues.getRowLength())
    );
  }

  private static Set<Integer> getEntriesThatAreTooLarge(Map<Integer, List<Integer>> clues, int length)
  {
    return clues.entrySet().stream()
        .filter(entry -> clueLength(entry.getValue()) > length)
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

  private static int clueLength(List<Integer> intList)
  {
    //Sum of clues + number of spaces between each clue
    return intList.stream().mapToInt(Integer::intValue).sum() + intList.size() - 1;
  }
}
