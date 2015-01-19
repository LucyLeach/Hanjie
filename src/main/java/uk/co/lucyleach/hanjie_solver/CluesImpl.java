package uk.co.lucyleach.hanjie_solver;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 18/12/2014
 * Time: 21:51
 */
public class CluesImpl implements Clues
{
  private final Map<Integer, List<Integer>> rowClues;
  private final Map<Integer, List<Integer>> columnClues;

  public CluesImpl(Map<Integer, List<Integer>> rowClues, Map<Integer, List<Integer>> columnClues)
  {
    this.rowClues = makeImmutable(rowClues);
    this.columnClues = makeImmutable(columnClues);
  }

  @Override
  public Map<Integer, List<Integer>> getRowClues()
  {
    return rowClues;
  }

  @Override
  public Map<Integer, List<Integer>> getColumnClues()
  {
    return columnClues;
  }

  @Override
  public int getRowLength()
  {
    return columnClues.size();
  }

  @Override
  public int getColumnLength()
  {
    return rowClues.size();
  }

  private static Map<Integer, List<Integer>> makeImmutable(Map<Integer, List<Integer>> inputMap)
  {
    ImmutableMap.Builder<Integer, List<Integer>> bob = ImmutableMap.builder();
    for(Map.Entry<Integer, List<Integer>> entry: inputMap.entrySet())
    {
      bob.put(entry.getKey(), ImmutableList.<Integer>builder().addAll(entry.getValue()).build());
    }
    return bob.build();
  }
}
