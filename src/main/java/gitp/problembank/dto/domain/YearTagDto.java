package gitp.problembank.dto.domain;

import gitp.problembank.domain.tag.YearTag;

import lombok.Getter;

import java.time.Year;

@Getter
public class YearTagDto {

    private final String id;

    private final Year year;

    public YearTagDto(String id, Year year) {
        this.id = id;
        this.year = year;
    }

    public YearTag toEntity() {
        return YearTag.of(year);
    }

    public static YearTagDto toDto(YearTag entity) {
        return new YearTagDto(entity.getId(), entity.getYear());
    }
}
