package gitp.problembank.service;

import gitp.problembank.domain.ProblemSource;
import gitp.problembank.dto.domain.ProblemSourceDto;
import gitp.problembank.repository.ProblemSourceRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemSourceService {
    private final ProblemSourceRepository problemSourceRepository;

    public void update(String id, ProblemSourceDto dto) {
        ProblemSource problemSource = problemSourceRepository.findById(id).orElseThrow();
        problemSource.setName(dto.getName());
        problemSource.setSourceType(dto.getSourceType());
        problemSource.setYearTag(dto.getYearTagDto().toEntity());

        problemSourceRepository.save(problemSource);
    }

    @Transactional(readOnly = true)
    public ProblemSourceDto findById(String id) {
        return ProblemSourceDto.toDto(problemSourceRepository.findById(id).orElseThrow());
    }

    public void save(ProblemSourceDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("dto should be referring un-persisted entity");
        }
        problemSourceRepository.save(dto.toEntity());
    }

    public void deleteById(String id) {
        problemSourceRepository.deleteById(id);
    }
}
