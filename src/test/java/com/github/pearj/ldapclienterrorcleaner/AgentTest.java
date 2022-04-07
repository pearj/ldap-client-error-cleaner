package com.github.pearj.ldapclienterrorcleaner;

import org.testng.annotations.Test;
import net.bytebuddy.agent.ByteBuddyAgent;

public class AgentTest {

    @Test
    public void testAgentInstall() {
        Agent.premain(null, ByteBuddyAgent.install());
    }
}