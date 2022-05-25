package io.virtualan.idaithalam.test;

import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;

public class RestTestPlanExecutor {

	@org.testng.annotations.Test
	public void workflowExecution_xl(){
		try {
			boolean isSuccess = VirtualanTestPlanExecutor.invoke("rest-get.yml");
 			Assert.assertTrue(isSuccess);
 		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

}
