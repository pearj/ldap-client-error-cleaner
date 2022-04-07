package com.github.pearj.ldapclienterrorcleaner;

import com.sun.jndi.ldap.LdapCtx;
import org.testng.Assert;
import org.testng.annotations.Test;
import net.bytebuddy.agent.ByteBuddyAgent;

import javax.naming.NamingException;

public class AgentTest {

    @Test
    public void testAgentInstall() {
        NamingException result = LdapCtx.mapErrorCode(53, "before agent install\0");

        Assert.assertEquals(result.getMessage(), "[LDAP: error code 53 - before agent install\0]");

        Agent.premain(null, ByteBuddyAgent.install());

        result = LdapCtx.mapErrorCode(53, "after agent install\0");

        Assert.assertEquals(result.getMessage(), "[LDAP: error code 53 - after agent install]");
    }
}