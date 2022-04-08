package com.github.pearj.ldapclienterrorcleanertest;

import com.github.pearj.ldapclienterrorcleaner.Agent;
import org.testng.Assert;
import org.testng.annotations.Test;
import net.bytebuddy.agent.ByteBuddyAgent;

import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class AgentTest {

    @Test
    public void testAgentInstall() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Agent.premain(null, ByteBuddyAgent.install());

        Optional<Module> module = ModuleLayer.boot().findModule("java.naming");

        Class<?> ldapCtx = Class.forName(module.get(), "com.sun.jndi.ldap.LdapCtx");
        Method mapErrorCode = ldapCtx.getDeclaredMethod("mapErrorCode", int.class, String.class);
        NamingException result = (NamingException) mapErrorCode.invoke(null,53, "after agent install\0");

        Assert.assertEquals(result.getMessage(), "[LDAP: error code 53 - after agent install]");
    }
}