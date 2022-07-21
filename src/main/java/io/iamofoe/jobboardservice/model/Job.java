package io.iamofoe.jobboardservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Jobs")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String role;
    private String recruiter;
    private String description;
    private String url;
    private String location;
    private String imageUrl;
    private String source;
}
