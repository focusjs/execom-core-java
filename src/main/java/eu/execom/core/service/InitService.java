package eu.execom.core.service;

/**
 * Interface with set of methods that are used to initialize system.
 * 
 * @author Dusko Vesin
 * 
 */
public interface InitService {

    /**
     * Initialize User with UserRole#ADMIN if it already doesn't exist in DB.
     */
    void initAdminUser();

}
