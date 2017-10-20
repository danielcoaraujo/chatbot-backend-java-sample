package oi.digital.chatbot.repository;

import oi.digital.chatbot.model.Iteration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by daniel on 19/10/17.
 */
@Repository
public interface IterationRepository extends CrudRepository<Iteration, String> {
}
