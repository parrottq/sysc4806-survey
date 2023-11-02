package SYSC4806.survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Override
    @NonNull Optional<Question> findById(@NonNull Long id);

    @Override
    <S extends Question> @NonNull S save(@NonNull S entity);
}
