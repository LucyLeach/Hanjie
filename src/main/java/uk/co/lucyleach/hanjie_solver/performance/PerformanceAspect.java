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
  @Around("hanjieSolver()")
  public Object timeProfile(ProceedingJoinPoint thisJoinPoint)
      throws Throwable
  {
    long startTime = System.currentTimeMillis();
    Object returnedObject = thisJoinPoint.proceed();
    long endTime = System.currentTimeMillis();
    double timeTaken = (endTime - startTime) / 1000.;
    System.out.println("Time taken: " + timeTaken + "secs");
    return returnedObject;
  }

  @Pointcut("call(uk.co.lucyleach.hanjie_solver.Puzzle uk.co.lucyleach.hanjie_solver.solver.HanjieSolver.solve(uk.co.lucyleach.hanjie_solver.Clues))")
  private void hanjieSolver()
  {}
}
