package gitp.problembank.service;

import gitp.problembank.domain.YearTag;
import gitp.problembank.dto.domain.YearTagDto;
import gitp.problembank.repository.YearTagRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class YearTagService {
    private final YearTagRepository yearTagRepository;

    @Transactional(readOnly = true)
    public Optional<YearTagDto> findByYear(Year year) {
        return yearTagRepository.findYearTagByYear(year).map(YearTagDto::toDto);
    }

    public boolean deleteByYear(Year year) {
        return yearTagRepository.deleteYearTagByYear(year) != 0L;
    }

    public void updateByYear(Year year, YearTagDto dto) {
        YearTag yearTag = yearTagRepository.findYearTagByYear(year).orElseThrow();
        if (!yearTag.getYear().equals(dto.getYear())) {
            yearTag.setYear(dto.getYear());
        }

        yearTagRepository.save(yearTag);
    }

    public void save(YearTagDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("dto should not be persisted");
        }

        yearTagRepository.save(dto.toEntity());
    }
}
