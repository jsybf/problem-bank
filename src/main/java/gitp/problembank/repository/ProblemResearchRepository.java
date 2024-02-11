package gitp.problembank.repository;

import gitp.problembank.dto.ProblemResearchParamDto;
import gitp.problembank.dto.UnitChainDto;

import lombok.RequiredArgsConstructor;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Conditions;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.ListExpression;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.ResultStatement;
import org.neo4j.cypherdsl.core.renderer.Renderer;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ProblemResearchRepository {
    private final Neo4jClient neo4jClient;
    private final Renderer neo4jRenderer = Renderer.getDefaultRenderer();

    public Set<String> research(ProblemResearchParamDto paramDto) {
        ResultStatement query = generateResearchQuery(paramDto);

        return new HashSet<>(neo4jClient.query(neo4jRenderer.render(query)).fetchAs(String.class).all());
    }

    public ResultStatement generateResearchQuery(ProblemResearchParamDto paramDto) {

        Node p = Cypher.node("problem").named("p");
        Node ps = Cypher.node("problem_source").named("ps");
        Node yt = Cypher.node("year_tag").named("yt");
        Node st = Cypher.node("skill_tag").named("st");
        Node hut = Cypher.node("head_unit_tag").named("hut");
        Node mut = Cypher.node("middle_unit_tag").named("mut");
        Node tut = Cypher.node("tail_unit_tag").named("tut");

        // ##### 1/3
        // build conditions about problem-source(ps) and year-tag(yt)
        List<Condition> psConditions = new ArrayList<>();
        // condition: problem_source.id in $problem_source_id_list
        if (!paramDto.getProblemSourceIds().isEmpty()) {
            ListExpression psIdList =
                    Cypher.listOf(
                            paramDto.getProblemSourceIds().stream()
                                    .map(id -> Cypher.literalOf(id))
                                    .toList());
            psConditions.add(ps.property("id").in(psIdList));
        }
        // condition: problem_source.sourceType in $problem_source_type_list
        if (!paramDto.getProblemSourceTypes().isEmpty()) {
            ListExpression psTypeList =
                    Cypher.listOf(
                            paramDto.getProblemSourceTypes().stream()
                                    .map(type -> Cypher.literalOf(type.toString()))
                                    .toList());

            psConditions.add(ps.property("sourceType").in(psTypeList));
        }
        // condition: problem_source.year ?? $year (??: operator is differed by yearResearchingType)
        if (paramDto.getYear() != null) {
            if (paramDto.getYearResearchingType() == null) {
                throw new IllegalArgumentException(
                        "yearResearchingType should not be null when year isn't null");
            }
            psConditions.add(
                    switch (paramDto.getYearResearchingType()) {
                        case ON_OR_AFTER ->
                                yt.property("year")
                                        .gte(Cypher.literalOf(paramDto.getYear().getValue()));
                        case MATCHING ->
                                yt.property("year")
                                        .eq(Cypher.literalOf(paramDto.getYear().getValue()));
                    });
        }

        Condition psAggregatedCondtion = Conditions.noCondition();
        for (Condition condition : psConditions) {
            psAggregatedCondtion = psAggregatedCondtion.and(condition);
        }

        // ##### 2/3
        // build skill tag conditions
        Condition stConditions = Conditions.noCondition();
        if (!paramDto.getSkillTagIds().isEmpty()) {
            ListExpression stIdList =
                    Cypher.listOf(
                            paramDto.getSkillTagIds().stream()
                                    .map(id -> Cypher.literalOf(id))
                                    .toList());
            stConditions = st.property("id").in(stIdList);
        }


        //##### 3/3
        // build unit tag conditions
        List<UnitChainDto> firstDepth = new ArrayList<>();
        List<UnitChainDto> secondDepth = new ArrayList<>();
        List<UnitChainDto> thirdDepth = new ArrayList<>();

        for (UnitChainDto unitChainDto : paramDto.getUnitChainDtos()) {
            if (unitChainDto.getHeadUnit().getId() != null
                    && unitChainDto.getMiddleUnit().getId() == null
                    && unitChainDto.getTailUnit().getId() == null) {
                firstDepth.add(unitChainDto);
            } else if (unitChainDto.getHeadUnit().getId() != null
                    && unitChainDto.getMiddleUnit().getId() != null
                    && unitChainDto.getTailUnit().getId() == null) {
                secondDepth.add(unitChainDto);
            } else if (unitChainDto.getHeadUnit().getId() != null
                    && unitChainDto.getMiddleUnit().getId() != null
                    && unitChainDto.getTailUnit().getId() != null) {
                thirdDepth.add(unitChainDto);
            } else {
                throw new IllegalArgumentException("unit chain should be concatenate");
            }
        }
        ListExpression firstDepthExp =
                Cypher.listOf(
                        firstDepth.stream()
                                .map(dto -> Cypher.literalOf(dto.getHeadUnit().getId()))
                                .toList());

        ListExpression secondDepthExp =
                Cypher.listOf(
                        secondDepth.stream()
                                .map(dto -> Cypher.literalOf(dto.getMiddleUnit().getId()))
                                .toList());

        ListExpression thirdDepthExp =
                Cypher.listOf(
                        thirdDepth.stream()
                                .map(dto -> Cypher.literalOf(dto.getTailUnit().getId()))
                                .toList());

        Condition unitCondition = Conditions.noCondition();
        if (!firstDepth.isEmpty()) {
            unitCondition = unitCondition.or(hut.property("id").in(firstDepthExp));
        }
        if(!secondDepth.isEmpty()) {
            unitCondition = unitCondition.or(mut.property("id").in(secondDepthExp));
        }
        if(!thirdDepth.isEmpty()) {
            unitCondition = unitCondition.or(tut.property("id").in(thirdDepthExp));
        }

        //##### build final research query
        ResultStatement query =
                Cypher.match(p.relationshipTo(ps, "source").relationshipTo(yt, "tagged"))
                        .where(psAggregatedCondtion)
                        .with(p)
                        .match(p.relationshipTo(st, "tagged"))
                        .where(stConditions)
                        .with(p)
                        .match(
                                hut.relationshipTo(mut, "unit_chain")
                                        .relationshipTo(tut, "unit_chain")
                                        .relationshipFrom(p, "tagged"))
                        .with(p, hut, mut, tut)
                        .where(unitCondition)
                        .returning(p.property("id"))
                        .build();
        return query;
    }
}
