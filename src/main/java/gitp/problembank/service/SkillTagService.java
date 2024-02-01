package gitp.problembank.service;

import gitp.problembank.domain.SkillTag;
import gitp.problembank.domain.YearTag;
import gitp.problembank.dto.domain.SkillTagDto;
import gitp.problembank.dto.domain.YearTagDto;
import gitp.problembank.repository.SkillTagRepository;
import gitp.problembank.repository.YearTagRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillTagService {
    private final SkillTagRepository skillTagRepository;

    @Transactional(readOnly = true)
    public Optional<SkillTagDto> findByTitle(String title) {
        return skillTagRepository.findByTitle(title).map(SkillTagDto::toDto);
    }

    public void deleteById(String id) {
        skillTagRepository.deleteById(id);
    }

    public void updateById(String id, SkillTagDto dto) {
        SkillTag skillTag = skillTagRepository.findById(id).orElseThrow();

        if (!skillTag.getTitle().equals(dto.getTitle())) {
            skillTag.setTitle(dto.getTitle());
        }

        skillTagRepository.save(skillTag);
    }

    public void save(SkillTagDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("dto should not be persisted");
        }

        skillTagRepository.save(dto.toEntity());
    }
}
