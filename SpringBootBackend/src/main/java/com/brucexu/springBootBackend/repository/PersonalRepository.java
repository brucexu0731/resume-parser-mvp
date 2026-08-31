package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, Long> {

    @Query("""
    SELECT p.personalId
    FROM Personal p
    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
      AND p.email = :email
      AND p.phoneNumber = :phoneNumber
    """)
    Optional<Long> findPersonalId(
            @Param("name") String name,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber
    );

}
