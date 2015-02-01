package uk.co.lucyleach.hanjie_solver.performance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * User: Lucy
 * Date: 01/02/2015
 * Time: 15:54
 */
@Aspect
public class PerformanceAspect
{
  @Around("allTopLevelMethods()")
  public Object timeProfile(ProceedingJoinPoint thisJoinPoint)
      throws Throwable
  {
    long startTime = System.currentTimeMillis();
    Object returnedObject = thisJoinPoint.proceed();
    long endTime = System.currentTimeMillis();
    double timeTaken = (endTime - startTime) / 1000.;
    System.out.println("Time taken on " + getMethodName(thisJoinPoint) + ": " + timeTaken + "secs");
    return returnedObject;
  }

  private static String getMethodName(ProceedingJoinPoint thisJoinPoint)
  {
    return thisJoinPoint.getThis().getClass().getSimpleName() + "." + thisJoinPoint.getSignature().getName();
  }

  @Pointcut("allHanjieSolver() || reader() || writer()")
  private void allTopLevelMethods()
  {}

  @Pointcut("execution(* *..*HanjieSolver.solve(..))")
  private void hanjieSolver()
  {}

  @Pointcut("execution(* *..*HanjieSolver.solve(..)) || execution(* *..*HanjieSolver.createInitialSolutions(..)) " +
      "|| execution(* *..*HanjieSolver.mergeRowsAndColumns(..))")
  private void allHanjieSolver()
  {}

  @Pointcut("execution(* *..*ClueReader_*.readInputWithoutCheck(..))")
  private void reader()
  {}

  @Pointcut("execution(* *..*PuzzleWriter_Spreadsheet.writePuzzle(..))")
  private void writer()
  {}
}
