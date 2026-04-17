package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.InstitucionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.InstitucionResponseDto;
import co.edu.unbosque.horaclass.academy.model.Institucion;
import co.edu.unbosque.horaclass.academy.repository.InstitucionRepository;
import co.edu.unbosque.horaclass.academy.service.InstitucionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstitucionServiceImpl implements InstitucionService {

    private final InstitucionRepository repository;
    private final ModelMapper modelMapper;

    public InstitucionServiceImpl(InstitucionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public InstitucionResponseDto crear(InstitucionRequestDto request) {
        if (repository.existsById(request.getIdInstitucion())) {
            throw new RuntimeException("Ya existe una Institucion con ese ID");
        }
        Institucion entity = modelMapper.map(request, Institucion.class);
        repository.save(entity);
        return modelMapper.map(entity, InstitucionResponseDto.class);
    }

    @Override
    public InstitucionResponseDto actualizar(int id, InstitucionRequestDto request) {
        Institucion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Institucion no encontrada"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, InstitucionResponseDto.class);
    }

    @Override
    public List<InstitucionResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, InstitucionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InstitucionResponseDto obtenerPorId(int id) {
        Institucion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Institucion no encontrada"));
        return modelMapper.map(entity, InstitucionResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Institucion no encontrada");
        }
        repository.deleteById(id);
    }
}