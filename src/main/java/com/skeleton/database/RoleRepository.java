package com.skeleton.database;


import com.skeleton.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select  * from security_role where role_name = 'ROLE_BROKER' ", nativeQuery = true)
    Role roleBroker();

    @Query(value = "select  * from security_role where role_name = 'ROLE_STUDENT' ", nativeQuery = true)
    Role roleStudent();

    @Query(value = "select  * from security_role where role_name = 'ROLE_ADMIN' ", nativeQuery = true)
    Role roleAdmin();

}
