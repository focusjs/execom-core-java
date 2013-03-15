package eu.execom.core.util;

/**
 * {@link Exception} that is thrown by {@link TemplateGenerator} when something went wrong during proccess of generating
 * string from template.
 * 
 * @author dvesin
 */
public class TemplateGeneratorExcpetion extends Exception {

    private static final long serialVersionUID = -7853126325910741365L;

    /**
     * Constructor that wrap other exceptions.
     * 
     * @param e
     *            that is passed.
     */
    public TemplateGeneratorExcpetion(final Exception e) {
        super(e);
    }

}
