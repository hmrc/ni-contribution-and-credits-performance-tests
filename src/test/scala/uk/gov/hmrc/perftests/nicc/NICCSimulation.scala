/*
 * Copyright 2024 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.perftests.nicc

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.nicc.niccdata.NICCRequests._

class NICCSimulation extends PerformanceTestRunner {

  if (runLocal)
    before(JWTRequest.ensureValidAuthToken())

  // NICC Request
  setup("post-nicc", "POST nicc details") withRequests niccRequest

  runSimulation()
}
