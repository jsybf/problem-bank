package gitp.problembank.repository;

import gitp.problembank.domain.SkillTag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SkillTagRepository extends Neo4jRepository<SkillTag, String> {

}
