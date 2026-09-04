package com.brucexu.springBootBackend.services;

import com.brucexu.springBootBackend.dto.personalProfile.SimpleEducationDTO;
import com.brucexu.springBootBackend.dto.personalProfile.SimplePersonProfileDTO;
import com.brucexu.springBootBackend.dto.personalProfile.SimpleWorkExperienceDTO;
import com.brucexu.springBootBackend.repository.PersonalRepository;
import com.brucexu.springBootBackend.repository.projections.SimplePersonProfileProjection;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CandidateInfoService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PersonalRepository personalRepo;


    public List<SimplePersonProfileDTO> getSimpleProfiles(List<Long> ids){
        List<SimplePersonProfileProjection> profiles = personalRepo.getPersonProfile(ids);

        List<SimplePersonProfileDTO> results = profiles.stream()
                .map(p -> {
                    try {
                        List<SimpleWorkExperienceDTO> workExperiences =
                                objectMapper.readValue(
                                        p.getWorkExperiences(),
                                        new TypeReference<List<SimpleWorkExperienceDTO>>() {
                                        }
                                );

                        List<SimpleEducationDTO> educations =
                                objectMapper.readValue(
                                        p.getEducations(),
                                        new TypeReference<List<SimpleEducationDTO>>() {
                                        }
                                );


                        return new SimplePersonProfileDTO(
                                p.getName(),
                                p.getPhoneNumber(),
                                p.getEmail(),

                                p.getSkills() == null
                                        ? List.of()
                                        : Arrays.asList(p.getSkills()),

                                p.getIndustry(),
                                p.getNeedVisa(),
                                p.getAddDate(),
                                p.getAddBy(),
                                p.getUpdateDate(),
                                p.getUpdateBy(),
                                p.getCandidateSource(),
                                p.getPreferredLocation(),
                                p.getPreferredIndustry(),
                                p.getPreferredBaseSalary(),
                                p.getPreferredAnnualPackage(),
                                p.getNoticePeriod(),
                                p.getAdditionalInfo(),
                                p.getMotivation(),

                                workExperiences,
                                educations
                        );
                    } catch (JacksonException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        return results;
    }
}
