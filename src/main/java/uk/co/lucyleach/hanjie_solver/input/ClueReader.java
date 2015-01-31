package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.base.Splitter;
import uk.co.lucyleach.hanjie_solver.Clues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:42
 */
public interface ClueReader<I>
{
  default Clues readInput(I input)
  {
    Clues clues = readInputWithoutCheck(input);
    checkConsistency(clues);
    return clues;
  }

  Clues readInputWithoutCheck(I input);

  static final String NUMBER_SEPARATOR = ",";
  static final ClueConsistencyChecker CONSISTENCY_CHECKER = new ClueConsistencyChecker();

  default Map<Integer, List<Integer>> createClueMap(Stream<String> lineFromFile)
  {
    List<List<Integer>> clues = lineFromFile
        .map(clue -> Splitter.on(NUMBER_SEPARATOR).splitToList(clue).stream()
            .mapToInt(Integer::parseInt)
            .boxed()
            .collect(Collectors.toList()))
        .collect(Collectors.toList());
    Map<Integer, List<Integer>> clueMap = new HashMap<>();
    int keyCounter = 1;
    for(List<Integer> clueList: clues)
    {
      clueMap.put(keyCounter, clueList);
      keyCounter++;
    }
    return clueMap;
  }

  default void checkConsistency(Clues clues)
  {
    ConsistencyCheckResult checkResult = CONSISTENCY_CHECKER.check(clues);
    if(checkResult.anyErrors())
      throw new IllegalArgumentException(checkResult.toString());
  }
}
