package com.skeleton.database;


import com.skeleton.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query(value = "delete from user_roles where user_id=?1", nativeQuery = true)
    void deleteAllByUserName(Integer userId);
}
