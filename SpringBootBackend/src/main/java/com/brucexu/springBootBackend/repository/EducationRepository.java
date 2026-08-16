package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {}
