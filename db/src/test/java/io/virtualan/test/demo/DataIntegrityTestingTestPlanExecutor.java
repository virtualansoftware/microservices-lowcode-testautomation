package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test
public class DataIntegrityTestingTestPlanExecutor {


  @Test
  public void workflowExecution_xl_db() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("data-integrity-testing.yml");
      Assert.assertTrue(isSuccess);
    } catch (Exception e) {
      Assert.assertTrue(false);
    }
  }


}
