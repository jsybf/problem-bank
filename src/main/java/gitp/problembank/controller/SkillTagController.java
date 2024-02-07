package gitp.problembank.controller;

import gitp.problembank.dto.domain.SkillTagDto;
import gitp.problembank.service.SkillTagService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gitp/problem-bank/tag/skill-tag")
@RequiredArgsConstructor
public class SkillTagController {
    private final SkillTagService skillTagService;

    @GetMapping("/form")
    public String saveForm() {
        return "skill-tag-form";
    }

    @PostMapping("/form")
    public void receiveSaveForm(@RequestParam String title) {
        SkillTagDto skillTagDto = new SkillTagDto(null, title);
        skillTagService.save(skillTagDto);
    }
}
