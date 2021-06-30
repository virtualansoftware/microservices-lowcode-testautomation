package io.virtualan.test.demo;

import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LakesideMutualExecuteTestPlanExecutor {

    @Test
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

}
