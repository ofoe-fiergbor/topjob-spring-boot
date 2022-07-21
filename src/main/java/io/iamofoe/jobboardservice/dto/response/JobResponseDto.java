package io.iamofoe.jobboardservice.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JobResponseDto {
    String role;
    String recruiter;
    String description;
    String url;
    String location;
    String imageUrl;
}
