package gitp.problembank.repository;

import gitp.problembank.domain.tag.AbstractUnitTag;
import gitp.problembank.domain.tag.HeadUnitTag;
import gitp.problembank.domain.tag.MiddleUnitTag;
import gitp.problembank.domain.tag.TailUnitTag;
import gitp.problembank.dto.UnitChainDto;

import lombok.RequiredArgsConstructor;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UnitTagCustomRepositoryImpl implements UnitTagCustomRepository {
    private final Neo4jTemplate neo4jTemplate;
    private final Neo4jClient neo4jClient;

    @Override
    public Optional<UnitChainDto> getUnitChainDtoByTailUnitTagId(String tailId) {
        Optional<TailUnitTag> foundTail = neo4jTemplate.findById(tailId, TailUnitTag.class);
        if (foundTail.isEmpty()) {
            return Optional.empty();
        }

        String query =
                """
            MATCH(head:head_unit_tag)-[:unit_chain]->(middle:middle_unit_tag)-[:unit_chain]->(tail:tail_unit_tag)
            WHERE tail.id = $id
            RETURN
                head.unitName as headUnitName
                , head.unitNum as headUnitNum
                , middle.unitName as middleUnitName
                , middle.unitNum as middleUnitNum
                , tail.unitName as tailUnitName
                , tail.unitNum as tailUnitNum
            """;

        Optional<UnitChainDto> unitChainDto =
                neo4jClient
                        .query(query)
                        .bind(tailId)
                        .to("id")
                        .fetchAs(UnitChainDto.class)
                        .mappedBy(
                                ((typeSystem, record) ->
                                        new UnitChainDto(
                                                record.get("headUnitName").asString(),
                                                        record.get("headUnitNum").asInt(),
                                                record.get("middleUnitName").asString(),
                                                        record.get("middleUnitNum").asInt(),
                                                record.get("tailUnitName").asString(),
                                                        record.get("tailUnitNum").asInt())))
                        .one();

        return unitChainDto;
    }

    @Override
    public void saveHead(HeadUnitTag headUnitTag) {
        neo4jTemplate.save(headUnitTag);
    }

    @Override
    public void saveMiddle(MiddleUnitTag middleUnitTag) {
        neo4jTemplate.save(middleUnitTag);
    }

    @Override
    public void saveTail(TailUnitTag tailUnitTag) {
        neo4jTemplate.save(tailUnitTag);
    }

    @Override
    public void delete(String id) {
        neo4jTemplate.deleteById(id, AbstractUnitTag.class);
    }

    @Override
    public Optional<HeadUnitTag> findHeadById(String id) {
        return neo4jTemplate.findById(id, HeadUnitTag.class);
    }

    @Override
    public Optional<MiddleUnitTag> findMiddleById(String id) {
        return neo4jTemplate.findById(id, MiddleUnitTag.class);
    }

    @Override
    public Optional<TailUnitTag> findTailById(String id) {
        return neo4jTemplate.findById(id, TailUnitTag.class);
    }
}
