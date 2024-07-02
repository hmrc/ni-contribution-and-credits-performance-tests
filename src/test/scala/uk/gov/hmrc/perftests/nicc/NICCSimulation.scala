package uk.gov.hmrc.perftests

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import CalculationRequests._

class CalculationSimulation extends PerformanceTestRunner {

  setup("initial-calculation",
    "get the initial calculation") withRequests callInitialCalculation

  setup("final-calculation",
    "get the final calculation") withRequests callFinalCalculation

  runSimulation()
}