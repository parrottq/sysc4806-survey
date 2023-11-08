package SYSC4806.survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface QuestionRepository extends CrudRepository<Question, UUID> {
    @Override
    @NonNull Optional<Question> findById(@NonNull UUID id);

    @Override
    <S extends Question> @NonNull S save(@NonNull S entity);
}
