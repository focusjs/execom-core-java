package eu.execom.core.service;

/**
 * Authentication exception, occurs in process of user authentication.
 * 
 * @author Dusko Vesin
 * 
 */
public class AuthenticationException extends Exception {

    private static final long serialVersionUID = -3893717717428846708L;

    /**
     * Constructor.
     * 
     * @param message
     *            that describes exception.
     */
    public AuthenticationException(final String message) {
        super(message);
    }

}
