package io.iamofoe.jobboardservice.client;

import io.iamofoe.jobboardservice.client.response.JobClientResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JobsInGhana {

    private final String jobsInGhanaBaseUrl;

    @Autowired
    public JobsInGhana(@Value("${site.jobsinghana.baseurl}") String jobsInGhanaBaseUrl) {
        this.jobsInGhanaBaseUrl = jobsInGhanaBaseUrl;
    }

    public List<JobClientResponseDto> getJobsFromSite(int category) {
        String url = jobsInGhanaBaseUrl + "/jobs/indexnew.php?device=d&cat=" + category;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("joblistitem");
            return elements.stream().map(element -> {
                String role = element.getElementById("jobtitle").ownText();
                String availableRecruiter = element.getElementsByAttributeValueContaining("title", "view all jobs posted by").text();
                String location = element.getElementsByAttributeValueContaining("property", "address").text();
                String imageUrl = element.getElementsByAttributeValueContaining("class", "jlistlogo").attr("src");
                String jobUrl = element.getElementById("jobtitle").attr("href");
                String recruiter = availableRecruiter.isEmpty() ? "Confidential" : availableRecruiter;
                String description = element.getElementsByTag("p").text();

                return JobClientResponseDto.builder()
                        .role(role)
                        .description(description)
                        .imageUrl(formatUrl(imageUrl))
                        .location(location)
                        .recruiter(recruiter)
                        .url(formatUrl(jobUrl))
                        .source(jobsInGhanaBaseUrl)
                        .category(String.valueOf(category))
                        .build();
            }).toList();

        } catch (IOException e) {
            log.error(String.format("JobsInGhana: Failed to load data from %s", url));
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private String formatUrl(String link) {
        return String.format("%s%s", jobsInGhanaBaseUrl, link.substring(2));
    }
}
