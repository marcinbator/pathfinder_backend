package pl.bator.pathfinder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import pl.bator.pathfinder.infrastructure.common.entity.Record;
import pl.bator.pathfinder.infrastructure.common.repository.RecordRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RecordJpaTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private RecordRepository recordRepository;

    public Record createAndPersistRecord() {
        Record record = new Record();
        record.setName("Alex");
        testEntityManager.persist(record);
        testEntityManager.flush();
        return record;
    }

    @Test
    public void shouldInsertRecord() {
        // given
        Record alex = createAndPersistRecord();
        // when
        Record found = recordRepository.findById(alex.getId()).get();
        // then
        assertThat(found.getName()).isEqualTo(alex.getName());
    }

    @Test
    public void shouldReturnRecords() {
        // given
        Record alex = createAndPersistRecord();
        List<Record> records = Collections.singletonList(alex);
        // when
        List<Record> dbRecords = recordRepository.findAll();
        // then
        assertThat(dbRecords).isEqualTo(records);
    }

    @Test
    public void shouldReturnRecord(){
        //given
        Record alex = createAndPersistRecord();
        //when
        Record result = recordRepository.findById(alex.getId()).get();
        //then
        assertThat(result.getName())
                .isEqualTo(alex.getName());
    }
}

