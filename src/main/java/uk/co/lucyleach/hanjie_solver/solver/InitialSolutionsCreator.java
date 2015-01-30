package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.*;
import java.util.concurrent.RecursiveAction;

import static com.google.common.collect.Maps.toMap;
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

  private static void addNumOfStatesToMap(int numStates, SquareState state, Map<Integer, SquareState> map)
  {
    int initialSize = map.size();
    rangeClosed(1, numStates).forEach(number -> map.put(number + initialSize, state));
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
          solutions = ImmutableSet.of(toMap(rangeClosed(1, length).iterator(), integer -> SquareState.BLANK));
        }
        else if (clue == length)
        {
          solutions = ImmutableSet.of(toMap(rangeClosed(1, length).iterator(), integer -> SquareState.FULL));
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
        int totalClueLength = clues.stream().mapToInt(Integer::valueOf).sum();
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
          int removedClue = cluesWithoutLastEntry.remove(cluesWithoutLastEntry.size() - 1);
          Set<PuzzleSolvingAction> actions = new HashSet<>();
          for(int numberOfBlanksAtEnd = 0; numberOfBlanksAtEnd <= numberOfUnconstrainedBlanks; numberOfBlanksAtEnd++)
          {
            //Create new puzzle to solve without the blanks at the end, the last block (defined by the last clue) and the blank just before it
            actions.add(new PuzzleSolvingAction(length - numberOfBlanksAtEnd - 1 - removedClue, cluesWithoutLastEntry));
          }
          invokeAll(actions);

          solutions = new HashSet<>();
          for(PuzzleSolvingAction action: actions)
          {
            int numberOfBlanksAtEnd = length - action.length - 1 - removedClue;
            for(Map<Integer, SquareState> solution: action.getSolutions())
            {
              Map<Integer, SquareState> clonedMap = new HashMap<>(solution);
              addNumOfStatesToMap(1, SquareState.BLANK, clonedMap);
              addNumOfStatesToMap(removedClue, SquareState.FULL, clonedMap);
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
