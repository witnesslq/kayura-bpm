package org.kayura.bpm.engine;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.kayura.bpm.kernel.WorkItem;
import org.kayura.bpm.types.Actor;
import org.kayura.bpm.types.BizData;
import org.kayura.bpm.types.StartArgs;
import org.kayura.bpm.types.StartResult;
import org.kayura.bpm.types.TaskArgs;
import org.kayura.bpm.types.TaskResult;
import org.kayura.utils.KeyUtils;

public class WorkflowThreeNodeTest extends WorkflowEngineTest {

	public WorkflowThreeNodeTest() throws IOException {
		super();
	}

	private String handlerId = "luirenjia";
	private String bizFlowCode = "ThreeNode";
	private String bizDataId = KeyUtils.newId();

	@Test
	public void allTest() {
		startupTest();
		completedWorkItemTest();
	}

	@Test
	public void startupTest() {

		try {

			StartArgs args = new StartArgs();
			args.setCreator(new Actor(handlerId));
			args.setBizData(new BizData(bizDataId, "测试流程"));
			args.setFlowCode(bizFlowCode);

			StartResult startResult = runtime.startup(args);
			System.out.println("startResult: " + startResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void completedWorkItemTest() {

		try {

			Actor actor = new Actor(handlerId);
			WorkItem item = runtime.findWorkItemByFirst(bizFlowCode, bizDataId, actor);
			while (item != null) {

				TaskArgs args = new TaskArgs();
				args.setHandler(actor);
				args.setWorkItemId(item.getId());

				TaskResult taskResult = runtime.completeWorkItem(args);
				System.out.println("taskResult: " + taskResult);

				item = runtime.findWorkItemByFirst(bizFlowCode, bizDataId, actor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
