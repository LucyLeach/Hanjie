package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.base.Splitter;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

/**
 * User: Lucy
 * Date: 18/12/2014
 * Time: 20:55
 */
public class ClueReader_Text implements ClueReader<Path>
{
  private static final String CLUE_SEPARATOR = ";";
  private static final String NUMBER_SEPARATOR = ",";

  private final ClueConsistencyChecker consistencyChecker = new ClueConsistencyChecker();

  @Override
  public Clues readInput(Path input)
  {
    try
    {
      List<String> lines = Files.readAllLines(input);
      if(lines.size() < 2)
        throw new IllegalArgumentException("Need at least two lines in the file");

      Map<Integer, List<Integer>> columnClues = createClueMap(lines.get(0));
      Map<Integer, List<Integer>> rowClues = createClueMap(lines.get(1));
      CluesImpl clues = new CluesImpl(rowClues, columnClues);
      ConsistencyCheckResult checkResult = consistencyChecker.check(clues);
      if(checkResult.anyErrors())
        throw new IllegalArgumentException(checkResult.toString());
      return clues;
    } catch (IOException e)
    {
      throw new IllegalArgumentException("Error reading file: " + e.getMessage(), e);
    }
  }

  private Map<Integer, List<Integer>> createClueMap(String lineFromFile)
  {
    List<List<Integer>> clues = stream(Splitter.on(CLUE_SEPARATOR).split(lineFromFile).spliterator(), false)
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
}
