package co.edu.unbosque.horaclass.academy.repository;

import co.edu.unbosque.horaclass.academy.model.TipoSillas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSillasRepository extends JpaRepository<TipoSillas, Integer> {
}