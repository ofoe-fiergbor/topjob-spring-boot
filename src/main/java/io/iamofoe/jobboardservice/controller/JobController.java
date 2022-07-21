package io.iamofoe.jobboardservice.controller;

import io.iamofoe.jobboardservice.dto.response.JobResponseDto;
import io.iamofoe.jobboardservice.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping
    public ResponseEntity<Page<JobResponseDto>> getJobsPaginated(Pageable pageable){
        log.info("JobController: Request received for getting all jobs paginated");
        return new ResponseEntity<>(jobService.getJobsPaginated(pageable), HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<Map<String, List<JobResponseDto>>> searchJobsByRole(@RequestParam String role) {
        log.info("JobController: Request received for searching for jobs with {} role", role);
        return new ResponseEntity<>(jobService.searchJobsByRole(role), HttpStatus.OK);
    }
}
