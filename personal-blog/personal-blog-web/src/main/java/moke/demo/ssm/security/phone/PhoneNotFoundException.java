package moke.demo.ssm.security.phone;

import org.springframework.security.core.AuthenticationException;

public class PhoneNotFoundException extends AuthenticationException {
    public PhoneNotFoundException(String msg, Throwable t) {
        super( msg, t );
    }

    public PhoneNotFoundException(String msg) {
        super( msg );
    }
}