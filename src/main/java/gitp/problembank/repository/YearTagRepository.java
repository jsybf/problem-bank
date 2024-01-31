package gitp.problembank.repository;

import gitp.problembank.domain.YearTag;

import java.time.Year;
import java.util.Optional;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface YearTagRepository extends Neo4jRepository<YearTag, String> {
    Optional<YearTag> findYearTagByYear(Year year);

    long deleteYearTagByYear(Year year);
}
