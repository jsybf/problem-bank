package gitp.problembank.repository;

import gitp.problembank.domain.tag.UnitTag;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UnitTagRepository extends Neo4jRepository<UnitTag, String> {}
