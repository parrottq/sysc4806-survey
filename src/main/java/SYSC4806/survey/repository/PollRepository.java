package SYSC4806.survey.repository;

import SYSC4806.survey.model.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface PollRepository extends CrudRepository<Poll, UUID> {

    @Override
    @NonNull Optional<Poll> findById(@NonNull UUID id);

    @Override
    @NonNull Iterable<Poll> findAll();

    @Override
    <S extends Poll> @NonNull S save(@NonNull S entity);
}
