package gitp.problembank.dto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import gitp.problembank.domain.Problem;
import gitp.problembank.domain.ProblemSource;
import gitp.problembank.domain.ProblemSourceType;
import gitp.problembank.domain.YearTag;
import java.time.Year;
import org.junit.jupiter.api.Test;

class ProblemDtoTest {

	@Test
	void toDtoRecursive_test() throws Exception {
		//given
		ProblemSource ps = ProblemSource.of("ps", ProblemSourceType.KICE,
			YearTag.of(Year.of(2023)));
		Problem p1 = Problem.of("p1", 1L, ps);
		Problem p2 = Problem.of("p2", 2L, ps);
		Problem p3 = Problem.of("p3", 3L, ps);
		Problem p4 = Problem.of("p4", 4L, ps);
		p1.addRelatedProblem(p2, p3);
		p2.addRelatedProblem(p4);
		//when
		ProblemDto dto = ProblemDto.toDto(p1);
		//then
		assertThat(dto.getRelatedProblemDtoSet()).hasSize(2);
		assertThat(dto.getRelatedProblemDtoSet().stream()
			.map(problemDto -> problemDto.getRelatedProblemDtoSet().size()).toList()).contains(1, 0)
			.hasSize(2);
	}
}