package io.iamofoe.jobboardservice.service;

import io.iamofoe.jobboardservice.dto.response.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface JobService {
    Page<JobResponseDto> getJobsPaginated(Pageable pageable);
    Map<String, List<JobResponseDto>> searchJobsByRole(String role);
}
