package io.iamofoe.jobboardservice.client;

import io.iamofoe.jobboardservice.client.response.JobClientResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class GhanaJob {

    private final String ghanaJobBaseUrl;

    public GhanaJob(@Value("${site.ghanajob.baseurl}") String ghanaJobBaseUrl) {
        this.ghanaJobBaseUrl = ghanaJobBaseUrl;
    }

    public List<JobClientResponseDto> getJobsFromSite(int page) {
        String url = ghanaJobBaseUrl + "/job-vacancies-search-ghana?page=" + page;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("job-title");
            return elements.stream().map(element -> {
                var titles = element.getElementsByTag("h5")
                        .stream().map(title -> title.getElementsByTag("a")).findFirst().get();
                String jobUrl = String.format("%s%s", ghanaJobBaseUrl, titles.attr("href"));
                String role = titles.text();
                String recruiter = element.getElementsByClass("company-name").text();
                String imageUrl = "";
                String location = "";
                String category = "";
                String description = element.getElementsByClass("search-description").text();
                return JobClientResponseDto.builder()
                        .role(role)
                        .location(location)
                        .url(jobUrl)
                        .imageUrl(imageUrl)
                        .recruiter(recruiter)
                        .description(description)
                        .source(ghanaJobBaseUrl)
                        .category(category)
                        .build();
            }).toList();
        } catch (Exception e) {
            log.error(String.format("GhanaJob: Failed to load data from %s", url));
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
