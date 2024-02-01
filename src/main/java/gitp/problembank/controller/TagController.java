package gitp.problembank.controller;

import gitp.problembank.service.ProblemService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gitp/problem-bank/tag")
public class TagController {
    private final ProblemService problemService;

    @GetMapping
    public String getTagAddPage() {
        return "tag-index-form";
    }
}
