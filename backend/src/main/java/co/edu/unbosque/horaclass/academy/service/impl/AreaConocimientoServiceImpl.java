package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.AreaConocimientoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.AreaConocimientoResponseDto;
import co.edu.unbosque.horaclass.academy.model.AreaConocimiento;
import co.edu.unbosque.horaclass.academy.repository.AreaConocimientoRepository;
import co.edu.unbosque.horaclass.academy.service.AreaConocimientoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AreaConocimientoServiceImpl implements AreaConocimientoService {

    private final AreaConocimientoRepository repository;
    private final ModelMapper modelMapper;

    public AreaConocimientoServiceImpl(AreaConocimientoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AreaConocimientoResponseDto crear(AreaConocimientoRequestDto request) {
        if (repository.existsById(request.getIdArea())) {
            throw new RuntimeException("Ya existe un AreaConocimiento con ese ID");
        }
        AreaConocimiento entity = modelMapper.map(request, AreaConocimiento.class);
        repository.save(entity);
        return modelMapper.map(entity, AreaConocimientoResponseDto.class);
    }

    @Override
    public AreaConocimientoResponseDto actualizar(int id, AreaConocimientoRequestDto request) {
        AreaConocimiento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AreaConocimiento no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, AreaConocimientoResponseDto.class);
    }

    @Override
    public List<AreaConocimientoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, AreaConocimientoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AreaConocimientoResponseDto obtenerPorId(int id) {
        AreaConocimiento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AreaConocimiento no encontrado"));
        return modelMapper.map(entity, AreaConocimientoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("AreaConocimiento no encontrado");
        }
        repository.deleteById(id);
    }
}