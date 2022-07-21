package io.iamofoe.jobboardservice.client;


import io.iamofoe.jobboardservice.client.response.JobClientResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JobWebGhana {

    private final String jobWebGhanaBaseUrl;

    @Autowired
    public JobWebGhana(@Value("${site.jobwebghana.baseurl}") String jobWebGhanaBaseUrl) {
        this.jobWebGhanaBaseUrl = jobWebGhanaBaseUrl;
    }

    public List<JobClientResponseDto> getJobsFromSite() {
        String url = jobWebGhanaBaseUrl + "/jobs/";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("job");
            return elements.stream().map(element -> {
                Elements title = element.getElementById("titlo").getElementsByTag("a");
                String[] splitTitle = title.text().split("\sat\s");
                String role = splitTitle[0];
                String recruiter = splitTitle[1];
                String jobUrl = title.attr("href");
                Element exc = element.getElementById("exc");
                String imageUrl = "";
                String location = "";
                String description = exc.ownText().isBlank() ? exc.getElementsByClass("lista").text() : exc.ownText();
                return JobClientResponseDto.builder()
                        .role(role)
                        .location(location)
                        .url(jobUrl)
                        .imageUrl(imageUrl)
                        .recruiter(recruiter)
                        .description(description)
                        .source(jobWebGhanaBaseUrl)
                        .build();
            }).toList();
        } catch (IOException e) {
            log.error(String.format("JobWebGhana: Failed to load data from %s", url));
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
