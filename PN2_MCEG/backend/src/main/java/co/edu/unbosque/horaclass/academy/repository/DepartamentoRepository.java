package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
}