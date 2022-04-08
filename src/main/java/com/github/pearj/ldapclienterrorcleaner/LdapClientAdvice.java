package com.github.pearj.ldapclienterrorcleaner;

import net.bytebuddy.asm.Advice;

public class LdapClientAdvice {
    @Advice.OnMethodEnter
    static void enterGetErrorMessage(@Advice.Argument(value = 1, readOnly = false) String errorMessage) {
        if (errorMessage != null && errorMessage.length() > 0) {
            errorMessage = errorMessage.replace("\0", "");
        }
    }
}
