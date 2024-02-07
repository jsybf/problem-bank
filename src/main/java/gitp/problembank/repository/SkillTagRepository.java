package gitp.problembank.repository;

import gitp.problembank.domain.tag.SkillTag;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface SkillTagRepository extends Neo4jRepository<SkillTag, String> {
    Optional<SkillTag> findByTitle(String title);
}
