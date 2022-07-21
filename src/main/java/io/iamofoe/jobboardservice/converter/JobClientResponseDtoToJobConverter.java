package io.iamofoe.jobboardservice.converter;

import io.iamofoe.jobboardservice.client.response.JobClientResponseDto;
import io.iamofoe.jobboardservice.model.Job;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JobClientResponseDtoToJobConverter implements Converter<JobClientResponseDto, Job> {
    @Override
    public Job convert(JobClientResponseDto source) {
        return new Job()
                .setDescription(source.getDescription())
                .setImageUrl(source.getImageUrl())
                .setLocation(source.getLocation())
                .setRecruiter(source.getRecruiter())
                .setUrl(source.getUrl())
                .setSource(source.getSource())
                .setRole(source.getRole());
    }
}
