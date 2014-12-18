package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.base.Splitter;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 18/12/2014
 * Time: 20:55
 */
public class ClueReader_Text implements ClueReader<Path>
{
  private static final String CLUE_SEPARATOR = ";";
  private static final String NUMBER_SEPARATOR = ",";

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
      return new CluesImpl(rowClues, columnClues);
    } catch (IOException e)
    {
      throw new IllegalArgumentException("Error reading file: " + e.getMessage(), e);
    }
  }

  private Map<Integer, List<Integer>> createClueMap(String lineFromFile)
  {
    //TODO see if you can Java 8-ify this method.  Who needs for loops any more?
    Iterable<String> clues = Splitter.on(CLUE_SEPARATOR).split(lineFromFile);
    Map<Integer, List<Integer>> clueMap = new HashMap<>();
    int keyCounter = 1;
    for(String clue: clues)
    {
      Iterable<String> potentialNumbers = Splitter.on(NUMBER_SEPARATOR).split(clue);
      List<Integer> clueList = new ArrayList<>();
      for(String potentialNumber: potentialNumbers)
      {
        clueList.add(Integer.valueOf(potentialNumber));
      }
      clueMap.put(keyCounter, clueList);
      keyCounter++;
    }
    return clueMap;
  }
}
