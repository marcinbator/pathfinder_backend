package pl.bator.pathfinder.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bator.pathfinder.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
