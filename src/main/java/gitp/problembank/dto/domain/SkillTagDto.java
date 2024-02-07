package gitp.problembank.dto.domain;

import gitp.problembank.domain.tag.SkillTag;

import lombok.Getter;

@Getter
public class SkillTagDto {

    private final String id;

    private final String title;

    public SkillTagDto(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public SkillTag toEntity() {
        return SkillTag.of(title);
    }

    public static SkillTagDto toDto(SkillTag entity) {
        return new SkillTagDto(entity.getId(), entity.getTitle());
    }
}
