package gitp.problembank.repository;

import gitp.problembank.domain.Problem;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProblemRepository extends Neo4jRepository<Problem, String> {}
