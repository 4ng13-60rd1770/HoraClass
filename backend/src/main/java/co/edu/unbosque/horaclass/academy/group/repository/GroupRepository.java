package co.edu.unbosque.horaclass.academy.group.repository;

import co.edu.unbosque.horaclass.academy.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    //Consultas de grupo
    List<Group> findByCursoIdCurso(int idCurso);

    List<Group> findByCursoIdCursoAndEstadoGrupoNombre(int idCurso, String estadoNombre);

    //COnsultas por profesor
    List<Group> findByProfesorIdProfesor(Long idProfesor);

    List<Group> findByProfesorIdProfesorAndEstadoGrupoNombre(Long idProfesor, String estadoNombre);

    List<Group> findByEstadoGrupoNombre(String estadoNombre);


    boolean existsByIdGrupo(String idGrupo);

    boolean existsByCursoIdCursoAndProfesorIdProfesor(int idCurso, Long idProfesor);

}
