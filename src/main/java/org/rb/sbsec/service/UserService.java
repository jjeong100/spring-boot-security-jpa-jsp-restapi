
package org.rb.sbsec.service;

import org.rb.sbsec.model.User;

/**
 * Provide service for registering account 
 * @author raitis
 */
public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
