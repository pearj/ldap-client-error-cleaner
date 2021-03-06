package com.github.pearj.ldapclienterrorcleaner;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

/**
 * This is Agent for instrument all constructors and methods
 */
public class Agent {

    public static void premain(String arguments, Instrumentation instrumentation) {

        System.out.println("Initialising LDAP Client Error Cleaner Agent");

        new AgentBuilder.Default()
                .with(new AgentBuilder.InitializationStrategy.SelfInjection.Eager())
                .ignore(ElementMatchers.nameStartsWith("net.bytebuddy."))
//                .with(AgentBuilder.Listener.StreamWriting.toSystemOut())
                .type((ElementMatchers.named("com.sun.jndi.ldap.LdapClient")))
                .transform((builder, typeDescription, classLoader, module) -> builder
                        .method(ElementMatchers.named("getErrorMessage"))
                        .intercept(Advice.to(LdapClientAdvice.class))
                ).installOn(instrumentation);
    }
}