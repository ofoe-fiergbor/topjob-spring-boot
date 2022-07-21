package io.iamofoe.jobboardservice.service;

import io.iamofoe.jobboardservice.converter.JobToJobResponseDto;
import io.iamofoe.jobboardservice.dto.response.JobResponseDto;
import io.iamofoe.jobboardservice.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class JobRepositoryService implements JobService {
    private final JobRepository jobRepository;
    private final JobToJobResponseDto jobToJobResponseDtoConverter;

    @Autowired
    public JobRepositoryService(JobRepository jobRepository, JobToJobResponseDto jobToJobResponseDtoConverter) {
        this.jobRepository = jobRepository;
        this.jobToJobResponseDtoConverter = jobToJobResponseDtoConverter;
    }

    @Override
    public Page<JobResponseDto> getJobsPaginated(Pageable pageable) {
        log.info("JobRepositoryService: Fetching all jobs paginated");
        return jobRepository.findAll(pageable)
                .map(jobToJobResponseDtoConverter::convert);
    }

    @Override
    public Map<String, List<JobResponseDto>> searchJobsByRole(String role) {
        log.info("JobRepositoryService: Searching for all jobs with role -- {}", role);
        Map<String, List<JobResponseDto>> result = new HashMap<>();
        result.put("jobs", jobRepository.findJobsByRole(role).stream()
                .map(jobToJobResponseDtoConverter::convert)
                .filter(Objects::nonNull).toList());
        return result;
    }
}
