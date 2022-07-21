package io.iamofoe.jobboardservice.service;

import io.iamofoe.jobboardservice.client.GhanaJob;
import io.iamofoe.jobboardservice.client.JobWebGhana;
import io.iamofoe.jobboardservice.client.JobsInGhana;
import io.iamofoe.jobboardservice.converter.JobClientResponseDtoToJobConverter;
import io.iamofoe.jobboardservice.repository.JobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
public class PollingService extends AbstractPollingService {
    private final JobsInGhana jobsInGhanaClient;
    private final JobWebGhana jobWebGhanaClient;
    private final GhanaJob ghanaJobClient;
    private final String jobsInGhanaBaseUrl;
    private final String jobWebGhanaBaseUrl;
    private final String ghanaJobBaseUrl;
    private final List<Integer> jobsInGhanaCategories;

    @Autowired
    public PollingService(JobsInGhana jobsInGhanaClient, JobWebGhana jobWebGhanaClient, GhanaJob ghanaJobClient, JobRepository jobRepository, JobClientResponseDtoToJobConverter jobClientResponseDtoToJobConverter, @Value("${site.jobsinghana.baseurl}") String jobsInGhanaBaseUrl, @Value("${site.jobwebghana.baseurl}") String jobWebGhanaBaseUrl, @Value("${site.ghanajob.baseurl}") String ghanaJobBaseUrl, @Value("#{${site.jobsinghana.category}}") List<Integer> jobsInGhanaCategories) {
        super(jobRepository, jobClientResponseDtoToJobConverter);
        this.jobsInGhanaClient = jobsInGhanaClient;
        this.jobWebGhanaClient = jobWebGhanaClient;
        this.ghanaJobClient = ghanaJobClient;
        this.jobsInGhanaBaseUrl = jobsInGhanaBaseUrl;
        this.jobWebGhanaBaseUrl = jobWebGhanaBaseUrl;
        this.ghanaJobBaseUrl = ghanaJobBaseUrl;
        this.jobsInGhanaCategories = jobsInGhanaCategories;
    }

    @Transactional
    @Scheduled(fixedRate = 86_400_000)
    public void pollAllData() {
        pollJobsFromJobsInGhana();
        pollJobsFromJobWebGhana();
        pollJobsFromGhanaJob();
    }

    private void pollJobsFromJobWebGhana() {
        log.info(String.format("PollingService: Polling jobs from %s",jobWebGhanaBaseUrl));
        pollFromClient(jobWebGhanaClient.getJobsFromSite(), jobWebGhanaBaseUrl);
    }

    private void pollJobsFromJobsInGhana() {
        log.info(String.format("PollingService: Polling jobs from %s",jobsInGhanaBaseUrl));
        pollFromClient(jobsInGhanaCategories.stream()
                .map(jobsInGhanaClient::getJobsFromSite)
                .flatMap(List::stream).toList(), jobsInGhanaBaseUrl);
    }

    private void pollJobsFromGhanaJob() {
        log.info(String.format("PollingService: Polling jobs from %s",ghanaJobBaseUrl));
        pollFromClient(IntStream.range(0,3).mapToObj(ghanaJobClient::getJobsFromSite)
                .flatMap(List::stream).toList(), ghanaJobBaseUrl);
    }

}
