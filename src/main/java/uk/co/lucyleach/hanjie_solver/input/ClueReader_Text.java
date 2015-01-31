package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.base.Splitter;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

/**
 * User: Lucy
 * Date: 18/12/2014
 * Time: 20:55
 */
public class ClueReader_Text implements ClueReader<Path>
{
  private static final String CLUE_SEPARATOR = ";";

  @Override
  public Clues readInputWithoutCheck(Path input)
  {
    try
    {
      List<String> lines = Files.readAllLines(input);
      if(lines.size() < 2)
        throw new IllegalArgumentException("Need at least two lines in the file");

      Map<Integer, List<Integer>> columnClues = createClueMap(getStreamFromLine(lines.get(0)));
      Map<Integer, List<Integer>> rowClues = createClueMap(getStreamFromLine(lines.get(1)));
      return new CluesImpl(rowClues, columnClues);
    } catch (IOException e)
    {
      throw new IllegalArgumentException("Error reading file: " + e.getMessage(), e);
    }
  }

  private Stream<String> getStreamFromLine(String lineFromFile)
  {
    return stream(Splitter.on(CLUE_SEPARATOR).split(lineFromFile).spliterator(), false);
  }
}
