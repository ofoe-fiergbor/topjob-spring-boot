package io.iamofoe.jobboardservice.repository;

import io.iamofoe.jobboardservice.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    void deleteJobsBySource(String source);

    @Query("select j from Job j where lower(j.role) like lower(concat('%', :roleToFind,'%')) ")
    List<Job> findJobsByRole(@Param("roleToFind") String role);
}
