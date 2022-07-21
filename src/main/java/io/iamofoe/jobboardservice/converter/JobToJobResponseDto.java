package io.iamofoe.jobboardservice.converter;

import io.iamofoe.jobboardservice.dto.response.JobResponseDto;
import io.iamofoe.jobboardservice.model.Job;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JobToJobResponseDto implements Converter<Job, JobResponseDto> {
    @Override
    public JobResponseDto convert(Job source) {
        return JobResponseDto.builder()
                .role(source.getRole())
                .recruiter(source.getRecruiter())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .location(source.getLocation())
                .url(source.getUrl())
                .build();
    }
}
