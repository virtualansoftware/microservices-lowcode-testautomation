package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test
public class LakesideMutualExecuteTestPlanExecutor {


  @Test
  public void workflowExecution_xl_db() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("Customer-Self-Service-xl-db.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      Assert.assertTrue(false);
    }
  }


}
