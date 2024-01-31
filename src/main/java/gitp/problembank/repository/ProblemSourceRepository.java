package gitp.problembank.repository;

import gitp.problembank.domain.ProblemSource;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProblemSourceRepository extends Neo4jRepository<ProblemSource, String> {}
