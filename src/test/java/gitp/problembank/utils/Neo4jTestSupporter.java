package gitp.problembank.utils;

import gitp.problembank.utils.test.Neo4jTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class Neo4jTestSupporter {
	@BeforeAll
	public static void beforeAll(@Autowired Neo4jTestUtils neo4jTestUtils) {
		int deletedNodesCount = neo4jTestUtils.deleteAllNodes();
		log.info("clear neo4j test server");
		log.info("deleted_nodes: {}", deletedNodesCount);
	}
}
