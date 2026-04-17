package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.RestriccionProfesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestriccionProfesorRepository extends JpaRepository<RestriccionProfesor, Integer> {
}