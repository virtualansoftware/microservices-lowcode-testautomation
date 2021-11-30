package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class LakesideMutualExecuteTestPlanExecutor {


  @BeforeClass
  public void setUp() throws Exception {
    try {

      RestAssured.config().encoderConfig(
          EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));
    } catch (Exception ex) {
    }
  }

  @Test
  public void workflowExecution_xl() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("Customer-Self-Service-xl.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }

//   Using VIRTUALAN Collection
//   To run Performance testing of the system
//  @Test(threadPoolSize = 1, invocationCount = 1)
  public void workflowExecution_1() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
          .invoke("lakeside-Mutual-Customer-Self-Service.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }


//Uncomment and try
//  @Test
  public void workflowExecution_xl_db() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("Customer-Self-Service-xl-db.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }


}
