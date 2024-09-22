package org.rb.sbsec.repository;

import java.io.Serializable;
import org.rb.sbsec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author raitis
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
