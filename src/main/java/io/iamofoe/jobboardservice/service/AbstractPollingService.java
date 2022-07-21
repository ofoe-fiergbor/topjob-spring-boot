package io.iamofoe.jobboardservice.service;

import io.iamofoe.jobboardservice.client.response.JobClientResponseDto;
import io.iamofoe.jobboardservice.converter.JobClientResponseDtoToJobConverter;
import io.iamofoe.jobboardservice.model.Job;
import io.iamofoe.jobboardservice.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public abstract class AbstractPollingService {

    private final JobRepository jobRepository;
    private final JobClientResponseDtoToJobConverter jobClientResponseDtoToJobConverter;


    @Autowired
    protected AbstractPollingService(JobRepository jobRepository, JobClientResponseDtoToJobConverter jobClientResponseDtoToJobConverter) {
        this.jobRepository = jobRepository;
        this.jobClientResponseDtoToJobConverter = jobClientResponseDtoToJobConverter;
    }

    protected void pollFromClient(List<JobClientResponseDto> jobClientResponseDtoStream, String baseUrl) {
        List<Job> jobs = jobClientResponseDtoStream.stream().map(jobClientResponseDtoToJobConverter::convert)
                .filter(Objects::nonNull)
                .toList();

        if (!jobs.isEmpty()) {
            jobRepository.deleteJobsBySource(baseUrl);
        }
        jobRepository.saveAll(jobs);
    }
}
