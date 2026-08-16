package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {}
