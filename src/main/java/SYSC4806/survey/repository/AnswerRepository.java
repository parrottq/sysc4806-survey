package SYSC4806.survey.repository;

import SYSC4806.survey.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface AnswerRepository extends CrudRepository<Answer, UUID> {

    @Override
    @NonNull Optional<Answer> findById(@NonNull UUID id);

    @Override
    <S extends Answer> @NonNull S save(@NonNull S entity);
}
