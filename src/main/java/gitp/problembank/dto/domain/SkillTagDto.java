package gitp.problembank.dto.domain;

import gitp.problembank.domain.SkillTag;
import lombok.Getter;

@Getter
public class SkillTagDto {

    private final String title;

    public SkillTagDto(String title) {
        this.title = title;
    }

    public SkillTag toEntity() {
        return SkillTag.of(title);
    }

    public static SkillTagDto toDto(SkillTag entity) {
        return new SkillTagDto(entity.getTitle());
    }
}
