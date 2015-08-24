package uk.co.lucyleach.hanjie_solver.solver2;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * User: Lucy
 * Date: 24/08/2015
 * Time: 19:44
 */
public class ClueHelpfulnessScore implements Comparable<ClueHelpfulnessScore>
{
  private final boolean isRow;
  private final int number;
  private final int score;

  public ClueHelpfulnessScore(boolean isRow, int number, int score)
  {
    this.isRow = isRow;
    this.number = number;
    this.score = score;
  }

  public boolean isRow()
  {
    return isRow;
  }

  public int getNumber()
  {
    return number;
  }

  public int getScore()
  {
    return score;
  }

  @Override
  public int compareTo(ClueHelpfulnessScore o)
  {
    return new CompareToBuilder()
        .append(this.score, o.getScore())
        .append(this.number, o.getNumber())
        .append(this.isRow, o.isRow())
        .toComparison();
  }
}
