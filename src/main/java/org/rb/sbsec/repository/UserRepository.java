package org.rb.sbsec.repository;

import java.io.Serializable;
import org.rb.sbsec.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author raitis
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByUsername(String username);
}
