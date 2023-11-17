package pl.bator.pathfinder.infrastructure.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bator.pathfinder.infrastructure.common.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
