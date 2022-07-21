package io.iamofoe.jobboardservice.client.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JobClientResponseDto {
    String role;
    String recruiter;
    String description;
    String url;
    String location;
    String imageUrl;
    String category;
    String source;
}
