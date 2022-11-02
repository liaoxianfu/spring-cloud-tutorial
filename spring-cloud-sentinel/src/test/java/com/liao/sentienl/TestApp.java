package com.liao.sentienl;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试java版sentinel
 */
public class TestApp {
    /**
     * 定义限流规则
     */

    public static String resourceName = "helloService";

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource(resourceName);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 2.
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }


    @Test
    public void testSentinel() throws InterruptedException {
        initFlowRules();
        for (int i = 0; i < 100; i++) {
            try (Entry entry = SphU.entry(resourceName)) {
                Thread.sleep(200);
                System.out.println("hello world");
            } catch (BlockException e) {
                System.out.println("blocked");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Thread.sleep(100);
        }
    }

}
