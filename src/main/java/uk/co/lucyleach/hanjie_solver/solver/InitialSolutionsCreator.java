package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.*;
import java.util.concurrent.RecursiveAction;

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
    PuzzleSolvingAction action = new PuzzleSolvingAction(length, clues);
    action.compute();
    return new PossibleSolutionsImpl(action.getSolutions());
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
        int clue = Iterables.getOnlyElement(clues);
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
      }
      else
      {
        int totalClueLength = sum(clues);
        int totalNumberOfBlanks = length - totalClueLength;
        int numberOfClues = clues.size();
        int numberOfConstrainedBlanks = numberOfClues - 1;

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
          int numberOfUnconstrainedBlanks = totalNumberOfBlanks - numberOfConstrainedBlanks;
          List<Integer> cluesWithoutLastEntry = new ArrayList<>(clues);
          cluesWithoutLastEntry.remove(cluesWithoutLastEntry.size() - 1);
          Set<PuzzleSolvingAction> actions = new HashSet<>();
          for(int numberOfBlanksAtEnd = 0; numberOfBlanksAtEnd <= numberOfUnconstrainedBlanks; numberOfBlanksAtEnd++)
          {
            //Create new puzzle to solve without the blanks at the end, the last block (defined by the last clue) and the blank just before it
            actions.add(new PuzzleSolvingAction(length - numberOfBlanksAtEnd - 2, cluesWithoutLastEntry));
          }
          invokeAll(actions);

          solutions = new HashSet<>();
          for(PuzzleSolvingAction action: actions)
          {
            int numberOfBlanksAtEnd = length - action.length - 2;
            for(Map<Integer, SquareState> solution: action.getSolutions())
            {
              Map<Integer, SquareState> clonedMap = new HashMap<>(solution);
              addNumOfStatesToMap(1, SquareState.BLANK, clonedMap);
              addNumOfStatesToMap(1, SquareState.FULL, clonedMap);
              addNumOfStatesToMap(numberOfBlanksAtEnd, SquareState.BLANK, clonedMap);
              solutions.add(clonedMap);
            }
          }
        }
      }

      this.solutions = solutions;
    }

    public Set<Map<Integer, SquareState>> getSolutions()
    {
      return solutions;
    }
  }
}
