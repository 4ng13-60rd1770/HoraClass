package co.edu.unbosque.horaclass.user.repository;

import co.edu.unbosque.horaclass.user.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {}
