package eu.execom.core.util;

import java.util.Map;

/**
 * Interface that should be implemented by all template generators in system. All of them should have same bechavioour
 * 
 * @author dvesin
 */
public interface TemplateGenerator {

    /**
     * Method that will be called to generate specific outport for passed variables.
     * 
     * @param vars
     *            map of variables that should be inputed in template.
     * @return {@link String} format of generated template generator
     * @throws TemplateGeneratorExcpetion
     *             exception that is thrown something went wrong.
     */
    String generetaContent(Map<String, Object> vars) throws TemplateGeneratorExcpetion;

}
