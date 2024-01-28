package gitp.problembank.repository;

import gitp.problembank.domain.YearTag;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface YearTagRepository extends Neo4jRepository<YearTag, String> {}
