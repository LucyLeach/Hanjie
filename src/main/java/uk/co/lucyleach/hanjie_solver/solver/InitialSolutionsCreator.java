package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableSet;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.*;

import static com.google.common.collect.Maps.toMap;

/**
 * Creates the set of initial solutions from the clues given
 *
 * User: Lucy
 * Date: 19/01/2015
 * Time: 21:21
 */
class InitialSolutionsCreator
{
  PossibleSolutions create(List<Integer> clues, int length)
  {
    if(clues.size() == 1)
      return fromSingleClue(clues.get(0), length);
    else
      return fromMultipleClues(clues, length);
  }

  private static PossibleSolutions fromSingleClue(int clue, int length)
  {
    Set<Map<Integer, SquareState>> solutions;
    if (clue == 0)
    {
      solutions = ImmutableSet.of(toMap(keysOfLength(length), integer -> SquareState.BLANK));
    }
    else if (clue == length)
    {
      solutions = ImmutableSet.of(toMap(keysOfLength(length), integer -> SquareState.FULL));
    }
    else
    {
      ImmutableSet.Builder<Map<Integer, SquareState>> bob = ImmutableSet.builder();
      int numberOfBlanks = length - clue;
      for(int blanksBefore = 0; blanksBefore <= numberOfBlanks; blanksBefore++)
      {
        Map<Integer, SquareState> map = new HashMap<>();
        addNumOfStatesToMap(blanksBefore, SquareState.BLANK, map);
        addNumOfStatesToMap(clue, SquareState.FULL, map);
        addNumOfStatesToMap(numberOfBlanks - blanksBefore, SquareState.BLANK, map);
        bob.add(map);
      }
      solutions = bob.build();
    }

    return new PossibleSolutionsImpl(solutions);
  }

  private static PossibleSolutions fromMultipleClues(List<Integer> clues, int length)
  {
    int totalClueLength = sum(clues);
    int totalNumberOfBlanks = length - totalClueLength;
    int numberOfClues = clues.size();
    int numberOfConstrainedBlanks = numberOfClues - 1;

    Set<Map<Integer, SquareState>> solutions;
    if(totalNumberOfBlanks == numberOfConstrainedBlanks) //Only have one possible solution, one blank between each block
    {
      TreeMap<Integer, SquareState> onlySolution = new TreeMap<>();
      for(int clue: clues)
      {
        addNumOfStatesToMap(clue, SquareState.FULL, onlySolution);
        addNumOfStatesToMap(1, SquareState.BLANK, onlySolution);
      }
      onlySolution.remove(onlySolution.lastKey()); //last blank not needed
      solutions = ImmutableSet.of(onlySolution);
    }
    else
    {
      throw new UnsupportedOperationException();
    }
    return new PossibleSolutionsImpl(solutions);
  }

  private static int sum(Iterable<Integer> integers)
  {
    int runningTotal = 0;
    for(int number: integers)
    {
      runningTotal += number;
    }
    return runningTotal;
  }

  private static void addNumOfStatesToMap(int numStates, SquareState state, Map<Integer, SquareState> map)
  {
    int initialSize = map.size();
    for(int i = 1; i <= numStates; i++)
    {
      map.put(i + initialSize, state);
    }
  }

  //One-based set of concurrent integers
  private static Set<Integer> keysOfLength(int length)
  {
    ImmutableSet.Builder<Integer> bob = ImmutableSet.builder();
    for(int i = 1; i <= length; i++)
      bob.add(i);
    return bob.build();
  }
}
