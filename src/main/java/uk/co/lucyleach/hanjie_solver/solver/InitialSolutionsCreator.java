package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableSet;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.*;
import java.util.concurrent.RecursiveAction;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;

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
    PuzzleSolvingAction action = new PuzzleSolvingAction(length, clues);
    action.compute();
    return new PossibleSolutionsImpl(action.getSolutions());
  }

  //NB - returns and mutates the map!!
  private static Map<Integer, SquareState> addNumOfStatesToMap(int numStates, SquareState state, Map<Integer, SquareState> map)
  {
    int initialSize = map.size();
    rangeClosed(1, numStates).forEach(number -> map.put(number + initialSize, state));
    return map;
  }

  private static <T> Set<T> setOfOne(T element)
  {
    return ImmutableSet.of(element);
  }

  private static class PuzzleSolvingAction extends RecursiveAction
  {
    private final int length;
    private final List<Integer> clues;

    private Set<Map<Integer, SquareState>> solutions;

    public PuzzleSolvingAction(int length, List<Integer> clues)
    {
      this.length = length;
      this.clues = clues;
    }

    @Override
    protected void compute()
    {
      Set<Map<Integer, SquareState>> solutions;
      if (clues.size() == 1)
      {
        int onlyClue = getOnlyElement(clues);
        if (onlyClue == 0)
        {
          solutions = setOfOne(addNumOfStatesToMap(length, SquareState.BLANK, new HashMap<>()));
        }
        else if (onlyClue == length)
        {
          solutions = setOfOne(addNumOfStatesToMap(length, SquareState.FULL, new HashMap<>()));
        }
        else
        {
          int totalNumberOfBlanks = length - onlyClue;
          solutions = rangeClosed(0, totalNumberOfBlanks).boxed().map(blanksBefore ->
            mapWithNBlanksBeforeBlock(onlyClue, totalNumberOfBlanks, blanksBefore)
          ).collect(toSet());
        }
      }
      else
      {
        int totalClueLength = clues.stream().mapToInt(Integer::valueOf).sum();
        int totalNumberOfBlanks = length - totalClueLength;
        int numberOfConstrainedBlanks = clues.size() - 1;

        if(totalNumberOfBlanks == numberOfConstrainedBlanks)
        {
          solutions = singleBlankBetweenEachClue(clues);
        }
        else
        {
          List<Integer> cluesWithoutLastEntry = new ArrayList<>(clues);
          int removedClue = cluesWithoutLastEntry.remove(cluesWithoutLastEntry.size() - 1);

          Set<PuzzleSolvingAction> actions = getSubActions(totalNumberOfBlanks, numberOfConstrainedBlanks, cluesWithoutLastEntry, removedClue, length);
          invokeAll(actions);
          solutions = joinCompletedSubActions(removedClue, actions, length);
        }
      }

      this.solutions = solutions;
    }

    private static Set<Map<Integer, SquareState>> singleBlankBetweenEachClue(List<Integer> clues)
    {
      TreeMap<Integer, SquareState> onlySolution = new TreeMap<>();
      for(int clue: clues)
      {
        addNumOfStatesToMap(clue, SquareState.FULL, onlySolution);
        addNumOfStatesToMap(1, SquareState.BLANK, onlySolution);
      }
      onlySolution.remove(onlySolution.lastKey()); //last blank not needed
      return setOfOne(onlySolution);
    }

    private static Set<PuzzleSolvingAction> getSubActions(int totalNumberOfBlanks, int numberOfConstrainedBlanks, List<Integer> cluesWithoutLastEntry, int removedClue, int length)
    {
      int numberOfUnconstrainedBlanks = totalNumberOfBlanks - numberOfConstrainedBlanks;
      //Create new puzzles to solve without the blanks at the end, the last block (defined by the last clue) and the blank just before it
      return rangeClosed(0, numberOfUnconstrainedBlanks).boxed().map(
          numBlanksAtEnd -> new PuzzleSolvingAction(length - numBlanksAtEnd - 1 - removedClue, cluesWithoutLastEntry)
      ).collect(toSet());
    }

    private static Set<Map<Integer, SquareState>> joinCompletedSubActions(int removedClue, Set<PuzzleSolvingAction> actions, int length)
    {
      Set<Map<Integer, SquareState>> solutions;
      solutions = actions.stream().flatMap(
          action -> action.getSolutions().stream().map(
              solution -> {
                int numberOfBlanksAtEnd = length - action.length - 1 - removedClue;
                Map<Integer, SquareState> clonedMap = new HashMap<>(solution);
                addNumOfStatesToMap(1, SquareState.BLANK, clonedMap);
                addNumOfStatesToMap(removedClue, SquareState.FULL, clonedMap);
                addNumOfStatesToMap(numberOfBlanksAtEnd, SquareState.BLANK, clonedMap);
                return clonedMap;
              }
          )
      ).collect(toSet());
      return solutions;
    }

    private Map<Integer, SquareState> mapWithNBlanksBeforeBlock(int clue, int numberOfBlanks, Integer blanksBefore)
    {
      Map<Integer, SquareState> map = new HashMap<>();
      addNumOfStatesToMap(blanksBefore, SquareState.BLANK, map);
      addNumOfStatesToMap(clue, SquareState.FULL, map);
      addNumOfStatesToMap(numberOfBlanks - blanksBefore, SquareState.BLANK, map);
      return map;
    }

    public Set<Map<Integer, SquareState>> getSolutions()
    {
      return solutions;
    }
  }
}
