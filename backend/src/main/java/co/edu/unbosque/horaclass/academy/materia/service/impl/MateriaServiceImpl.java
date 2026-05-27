package co.edu.unbosque.horaclass.academy.materia.service.impl;

import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.classroom.repository.ClassroomRepository;
import co.edu.unbosque.horaclass.academy.materia.dto.MateriaRequestDto;
import co.edu.unbosque.horaclass.academy.materia.dto.MateriaResponseDto;
import co.edu.unbosque.horaclass.academy.materia.model.Materia;
import co.edu.unbosque.horaclass.academy.materia.repository.MateriaRepository;
import co.edu.unbosque.horaclass.academy.materia.service.MateriaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;
    private final ClassroomRepository classroomRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository,
                               ClassroomRepository classroomRepository) {
        this.materiaRepository = materiaRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public MateriaResponseDto crear(MateriaRequestDto request) {
        Materia m = new Materia();
        mapRequestToEntity(request, m);
        m.setInscritos(0);
        m.setDocente(null);
        return toDto(materiaRepository.save(m));
    }

    @Override
    public MateriaResponseDto actualizar(Long id, MateriaRequestDto request) {
        Materia m = findOrThrow(id);
        mapRequestToEntity(request, m);
        return toDto(materiaRepository.save(m));
    }

    @Override
    @Transactional(readOnly = true)
    public MateriaResponseDto obtenerPorId(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaResponseDto> listarTodas() {
        return materiaRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaResponseDto> listarPorCarrera(String carrera) {
        return materiaRepository.findByCarrera(carrera).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MateriaResponseDto> listarPorDocente(String docente) {
        return materiaRepository.findByDocente(docente).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        if (!materiaRepository.existsById(id))
            throw new RuntimeException("Materia no encontrada con ID: " + id);
        materiaRepository.deleteById(id);
    }

    @Override
    public MateriaResponseDto asignarDocente(Long id, String usernameDocente) {
        Materia m = findOrThrow(id);
        m.setDocente(usernameDocente);
        return toDto(materiaRepository.save(m));
    }

    @Override
    public MateriaResponseDto liberarDocente(Long id) {
        Materia m = findOrThrow(id);
        m.setDocente(null);
        return toDto(materiaRepository.save(m));
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private Materia findOrThrow(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + id));
    }

    private void mapRequestToEntity(MateriaRequestDto req, Materia m) {
        m.setNombre(req.getNombre());
        m.setCarrera(req.getCarrera());
        m.setCodigo(req.getCodigo());
        m.setTipo(req.getTipo());
        m.setModalidad(req.getModalidad());
        m.setCupoMax(req.getCupoMax() != null ? req.getCupoMax() : 30);
        m.setHorario(req.getHorario());

        if (req.getSalonId() != null) {
            Classroom salon = classroomRepository.findById(req.getSalonId())
                    .orElse(null);
            m.setSalon(salon);
        } else {
            m.setSalon(null);
        }
    }

    private MateriaResponseDto toDto(Materia m) {
        MateriaResponseDto dto = new MateriaResponseDto();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setCarrera(m.getCarrera());
        dto.setCodigo(m.getCodigo());
        dto.setTipo(m.getTipo());
        dto.setModalidad(m.getModalidad());
        dto.setCupoMax(m.getCupoMax());
        dto.setInscritos(m.getInscritos() != null ? m.getInscritos() : 0);
        dto.setHorario(m.getHorario());
        dto.setDocente(m.getDocente());
        if (m.getSalon() != null) {
            dto.setSalonId(m.getSalon().getIdSalon());
            dto.setSalonNombre(m.getSalon().getNombre());
        }
        return dto;
    }
}
