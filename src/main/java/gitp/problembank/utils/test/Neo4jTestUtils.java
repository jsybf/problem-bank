package gitp.problembank.utils.test;

import lombok.RequiredArgsConstructor;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class Neo4jTestUtils {

    private final Neo4jClient neo4jClient;

    /**
     * @return return number of deleted nodes
     */
    public int deleteAllNodes() {
        String query = """
			MATCH(n)
			DETACH DELETE n
			""";

        return neo4jClient.query(query).run().counters().nodesDeleted();
    }

    /**
     * @return key: name of label, value: counted nodes for each node
     */
    public Map<String, Integer> countNodesByLabel() {
        String query =
                """
			MATCH(n)
			WITH DISTINCT LABELS(n) AS label_group, count(n) AS label_group_cnt
			UNWIND label_group AS label
			RETURN label, SUM(label_group_cnt) as cnt
			""";
        return neo4jClient.query(query).fetch().all().stream()
                .flatMap(
                        map -> {
                            return Map.of(
                                    (String) map.get("label"),
                                    Long.valueOf((long) map.get("cnt")).intValue())
                                    .entrySet()
                                    .stream();
                        })
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
}
