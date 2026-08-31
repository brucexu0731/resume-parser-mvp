package com.brucexu.springBootBackend.services;

import com.brucexu.springBootBackend.dto.resume.ParsedResume;
import com.brucexu.springBootBackend.dto.resume.PersonalDTO;
import com.brucexu.springBootBackend.entity.Education;
import com.brucexu.springBootBackend.entity.Personal;
import com.brucexu.springBootBackend.entity.WorkExperience;
import com.brucexu.springBootBackend.entity.WorkExperienceContent;
import com.brucexu.springBootBackend.repository.PersonalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeSaveService {

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private EmbeddingService embeddingService;


    @Transactional
    public Personal saveParsedResume(ParsedResume parsedResume) {

        PersonalDTO dto = parsedResume.personal();

        Personal personal = new Personal();

        // DO NOT DO .STRIP() HERE! A LOT OF ENTRIES CAN BE NULL
        personal.setName(dto.name());
        personal.setPhoneNumber(dto.phoneNumber());
        personal.setEmail(dto.email());
        //use the one from parsedResume DTO, personalDTO is for returning, it would be null here
        personal.setResumeS3Key(parsedResume.s3Key());

        personal.setLatestCompany(dto.latestCompany());
        personal.setLatestRole(dto.latestRole());
        personal.setHighestDegree(dto.highestDegree());
        personal.setHighestDegreeSchool(dto.highestDegreeSchool());
        personal.setHighestDegreeGraduationDate(dto.highestDegreeGraduationDate());

        personal.setHighestDegreeMajor(dto.highestDegreeMajor());
        personal.setSkills(dto.skills());

        personal.setAge(dto.age());
        personal.setSex(dto.sex());
        personal.setGender(dto.gender());
        personal.setIndustry(dto.Industry());
        personal.setNeedVisa(dto.needVisa());
        personal.setCandidateSource(dto.candidateSource());
        personal.setPreferredLocation(dto.preferredLocation());
        personal.setPreferredIndustry(dto.preferredIndustry());
        personal.setPreferredBaseSalary(dto.preferredBaseSalary());
        personal.setPreferredAnnualPackage(dto.preferredAnnualPackage());
        personal.setNoticePeriod(dto.noticePeriod());
        personal.setAdditionalInfo(dto.additionalInfo());
        personal.setMotivation(dto.motivation());
        personal.setActive(true);

        // Work experience
        List<WorkExperience> workExperiences =
                parsedResume.work().stream()
                        .map(workDTO -> {
                            WorkExperience work = new WorkExperience();

                            work.setCompanyName(workDTO.companyName());
                            work.setTitle(workDTO.title());
                            work.setEmploymentType(workDTO.employmentType());
                            work.setStartDate(workDTO.start());
                            work.setEndDate(workDTO.end());
                            //work.setContents(workDTO.contents());
                            work.setLocation(workDTO.location());

                            List<float[]> embeddedContents = embeddingService.embedAll(workDTO.contents());
                            for (int i = 0; i < workDTO.contents().size(); i++) {

                                WorkExperienceContent content =
                                        new WorkExperienceContent();

                                content.setContent(workDTO.contents().get(i));
                                content.setContentIndex(i);
                                content.setEmbedding(embeddedContents.get(i));

                                //adds the content into work as dependency, while also
                                //passing in the parent reference into the content
                                // so no need to do content.setWorkExperience()
                                work.addContent(content);
                            }

                            return work;
                        })
                        .toList();

        for (WorkExperience workExperience: workExperiences) {
            personal.addWorkExperience(workExperience);
        }

        // Education
        List<Education> educations =
                parsedResume.education().stream()
                        .map(educationDTO -> {
                            Education education = new Education();

                            education.setSchoolName(educationDTO.schoolName());
                            //These two need to be lists
                            education.setDegree(educationDTO.degree());
                            education.setMajor(educationDTO.major());

                            education.setGraduationDate(educationDTO.graduationDate());
                            education.setGrade(educationDTO.grade());
                            education.setHonors(educationDTO.honors());

                            return education;
                        })
                        .toList();

        for (Education education: educations) {
            personal.addEducation(education);
        }

        return personalRepository.save(personal);
    }
}
