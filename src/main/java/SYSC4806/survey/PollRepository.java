package SYSC4806.survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface PollRepository extends CrudRepository<Poll, Long> {
    @Override
    @NonNull Optional<Poll> findById(@NonNull Long id);

    @Override
    @NonNull Iterable<Poll> findAll();

    @Override
    <S extends Poll> @NonNull S save(@NonNull S entity);
}
