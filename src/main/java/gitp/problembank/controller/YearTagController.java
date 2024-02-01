package gitp.problembank.controller;

import gitp.problembank.dto.domain.SkillTagDto;
import gitp.problembank.dto.domain.YearTagDto;
import gitp.problembank.service.SkillTagService;

import gitp.problembank.service.YearTagService;
import java.time.Year;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gitp/problem-bank/tag/year-tag")
@RequiredArgsConstructor
public class YearTagController {
    private final YearTagService yearTagService;

    @GetMapping("/form")
    public String saveForm() {
        return "year-tag-form";
    }

    @PostMapping("/form")
    public void receiveSaveForm(@RequestParam Integer year) {
        YearTagDto yearTagDto = new YearTagDto(null, Year.of(year));
        yearTagService.save(yearTagDto);
    }
}
