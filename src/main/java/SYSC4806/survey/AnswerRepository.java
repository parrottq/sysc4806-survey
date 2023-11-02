package SYSC4806.survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    @Override
    @NonNull Optional<Answer> findById(@NonNull Long id);

    @Override
    <S extends Answer> @NonNull S save(@NonNull S entity);
}
