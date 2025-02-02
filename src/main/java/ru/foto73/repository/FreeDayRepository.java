package ru.foto73.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.foto73.model.FreeDay;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FreeDayRepository extends JpaRepository<FreeDay, Long> {
    Optional<FreeDay> findByDate(LocalDate date);
}
