package com.brucexu.springBootBackend.repository;

import com.brucexu.springBootBackend.entity.Personal;
import com.brucexu.springBootBackend.repository.projections.SimplePersonProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query(value = """
            SELECT 
                p.personal_id as id,
                p.name AS name,
                p.phone_number AS phoneNumber,
                p.email AS email,
                (
                    SELECT ARRAY_AGG(ps.skills)
                    FROM personal_skills ps
                    WHERE ps.personal_personal_id = p.personal_id
                ) AS skills,
                            
                p.industry AS industry,
                p.need_visa AS needVisa,
                p.add_date AS addDate,
                p.add_by AS addBy,
                p.update_date AS updateDate,
                p.update_by AS updateBy,
                p.candidate_source AS candidateSource,
                p.preferred_location AS preferredLocation,
                p.preferred_industry AS preferredIndustry,
                p.preferred_base_salary AS preferredBaseSalary,
                p.preferred_annual_package AS preferredAnnualPackage,
                p.notice_period AS noticePeriod,
                p.additional_info AS additionalInfo,
                p.motivation AS motivation,
        
                CAST(
                    (
                        SELECT JSONB_AGG(
                            JSONB_BUILD_OBJECT(
                                'company', we.company_name,
                                'startDate', we.start_date,
                                'endDate', we.end_date
                            )
                        )
                        FROM work_experience we
                        WHERE we.personal_id = p.personal_id
                    )
                    AS text
                ) AS workExperiences,
        
                CAST(
                    (
                        SELECT JSONB_AGG(
                            JSONB_BUILD_OBJECT(
                                'schoolName', e.school_name,
                                'degree', e.degree,
                                'major', e.major,
                                'graduationDate', e.graduation_date
                            )
                        )
                        FROM education e
                        WHERE e.personal_id = p.personal_id
                    )
                    AS text
                ) AS educations
        
            FROM personal p
            WHERE p.personal_id IN (:ids)
              AND p.active = true
            """,
            nativeQuery = true)
    List<SimplePersonProfileProjection> getPersonProfile(
            @Param("ids") List<Long> ids
    );

}
