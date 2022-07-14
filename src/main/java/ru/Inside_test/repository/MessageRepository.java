package ru.Inside_test.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.Inside_test.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository
        extends CrudRepository<Message, Integer> {

    List<Message> findByPersonId(int personId);

    @Query(value =
            "SELECT * FROM message WHERE person_id = ?1 LIMIT ?2",
    nativeQuery = true)
    List<Message> findMessageHistoryWithLimit(int person_id, int limit);
}

