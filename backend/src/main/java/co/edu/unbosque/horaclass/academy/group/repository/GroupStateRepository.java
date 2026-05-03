package co.edu.unbosque.horaclass.academy.group.repository;

import co.edu.unbosque.horaclass.academy.group.model.GroupState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupStateRepository extends JpaRepository<GroupState,Integer> {
}
