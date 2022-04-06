package io.virtualan.test;

import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;

public class KafkaTestPlanExecutor {


    @org.testng.annotations.Test
    public void execute_orderEvent_workflow() {
        try {
            boolean isSuccess = VirtualanTestPlanExecutor.invoke("workflow/work-flow.yaml");
            org.junit.Assert.assertTrue(isSuccess);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.testng.annotations.Test
    public void execute_orderEvent_protobuff_workflow() {
        try {
            boolean isSuccess = VirtualanTestPlanExecutor.invoke("workflow/work-flow-proto.yaml");
            org.junit.Assert.assertTrue(isSuccess);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}