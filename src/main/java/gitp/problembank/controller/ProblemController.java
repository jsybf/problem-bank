package gitp.problembank.controller;

import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.dto.ProblemResearchParamDto;
import gitp.problembank.dto.YearResearchingType;
import gitp.problembank.dto.domain.ProblemDto;
import gitp.problembank.service.ProblemService;
import gitp.problembank.service.UnitTagService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DateTimeException;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/gitp/problem-bank/problem")
@RequiredArgsConstructor
@Slf4j
public class ProblemController {
    private final ProblemService problemService;
    private final UnitTagService unitTagService;

    @GetMapping("/{id}")
    public String detailView(@PathVariable String id, Model model) {
        ProblemDto dto = problemService.findById(id).orElseThrow();
        model.addAttribute("source", dto.getProblemSourceDto());
        model.addAttribute("year", dto.getProblemSourceDto().getYearTagDto().getYear());
        model.addAttribute("skillTags", dto.getSkillTagDtoSet());
        model.addAttribute("relatedProblems", dto.getRelatedProblemDtoSet());
        model.addAttribute("title", dto.getName());
        model.addAttribute(
                "unit",
                unitTagService.getUnitChainDtoByTailUnitTagId(dto.getTailUnitTagDto().getId()));

        return "problem-detail-view";
    }

    @GetMapping
    public String detailProblemResearch(
            @RequestParam List<String> skillTagIds,
            @RequestParam Integer yearInt,
            @RequestParam String yearResearchingType,
            @RequestParam List<String> problemSourceTypes,
            @RequestParam List<String> problemSourceIds,
            @RequestParam List<String> unitTagsId) {
        // string to enum and year
        ProblemResearchParamDto problemResearchParamDto;
        try {
            problemResearchParamDto =
                    ProblemResearchParamDto.of(
                            skillTagIds.stream().collect(Collectors.toSet()),
                            Year.of(yearInt),
                            YearResearchingType.valueOf(yearResearchingType),
                            problemSourceTypes.stream()
                                    .map(string -> ProblemSourceType.valueOf(string))
                                    .collect(Collectors.toSet()),
                            problemSourceIds.stream().collect(Collectors.toSet()),
                            unitTagsId.stream().collect(Collectors.toSet()));
        } catch (DateTimeException e) {
            log.info(String.format("invalid integer year value %d", yearInt));
            // TODO: return http response
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            // TODO: return http response
        }
        return null;
    }
}
