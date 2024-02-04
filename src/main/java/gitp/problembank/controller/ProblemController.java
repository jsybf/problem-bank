package gitp.problembank.controller;

import gitp.problembank.dto.domain.ProblemDto;
import gitp.problembank.service.ProblemService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gitp/problem-bank/problem")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping("/{id}")
    public String detailView(@PathVariable String id, Model model) {
        ProblemDto dto = problemService.findById(id).orElseThrow();
        model.addAttribute("source", dto.getProblemSourceDto());
        model.addAttribute("year", dto.getProblemSourceDto().getYearTagDto().getYear());
        model.addAttribute("skillTags", dto.getSkillTagDtoSet());
        model.addAttribute("relatedProblems", dto.getRelatedProblemDtoSet());
        model.addAttribute("title", dto.getName());

        return "problem-detail-view";
    }
}
