package uk.co.lucyleach.hanjie_solver.solver2;

import uk.co.lucyleach.hanjie_solver.Clues;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * User: Lucy
 * Date: 24/08/2015
 * Time: 19:43
 */
public class ClueHelpfulnessSorter
{
  private final KnowableSquareCalculator knowableSquareCalculator = new KnowableSquareCalculator();

  public ClueHelpfulnessScore bestClue(Clues clues) throws IllegalArgumentException {
    Stream<ClueHelpfulnessScore> rowClues = clues.getRowClues().entrySet().stream().map(clue -> scoreClue(clue, true, clues.getColumnLength()));
    Stream<ClueHelpfulnessScore> columnClues = clues.getColumnClues().entrySet().stream().map(clue -> scoreClue(clue, false, clues.getRowLength()));
    return Stream.concat(rowClues, columnClues).max(ClueHelpfulnessScore::compareTo).get();
  }

  private ClueHelpfulnessScore scoreClue(Map.Entry<Integer, List<Integer>> clue, boolean isRow, int length) {
    int number = clue.getKey();
    List<Integer> clues = clue.getValue();

    int knownSquares = knowableSquareCalculator.calculate(length, clues);

    int distanceFromEdge = Math.max(number, length - number);

    return new ClueHelpfulnessScore(isRow, number, knownSquares + distanceFromEdge);
  }

}
