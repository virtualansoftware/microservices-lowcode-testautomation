package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test
public class GraphqlTestPlanExecutor {

  @Test
  public void graphql() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("graphql.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      Assert.assertTrue(false);
    }
  }


}
