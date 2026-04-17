package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.ModalidadCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ModalidadCursoResponseDto;
import co.edu.unbosque.horaclass.academy.model.ModalidadCurso;
import co.edu.unbosque.horaclass.academy.repository.ModalidadCursoRepository;
import co.edu.unbosque.horaclass.academy.service.ModalidadCursoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModalidadCursoServiceImpl implements ModalidadCursoService {

    private final ModalidadCursoRepository repository;
    private final ModelMapper modelMapper;

    public ModalidadCursoServiceImpl(ModalidadCursoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ModalidadCursoResponseDto crear(ModalidadCursoRequestDto request) {
        if (repository.existsById(request.getIdModalidadCurso())) {
            throw new RuntimeException("Ya existe una ModalidadCurso con ese ID");
        }
        ModalidadCurso entity = modelMapper.map(request, ModalidadCurso.class);
        repository.save(entity);
        return modelMapper.map(entity, ModalidadCursoResponseDto.class);
    }

    @Override
    public ModalidadCursoResponseDto actualizar(int id, ModalidadCursoRequestDto request) {
        ModalidadCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModalidadCurso no encontrada"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, ModalidadCursoResponseDto.class);
    }

    @Override
    public List<ModalidadCursoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ModalidadCursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ModalidadCursoResponseDto obtenerPorId(int id) {
        ModalidadCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModalidadCurso no encontrada"));
        return modelMapper.map(entity, ModalidadCursoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ModalidadCurso no encontrada");
        }
        repository.deleteById(id);
    }
}