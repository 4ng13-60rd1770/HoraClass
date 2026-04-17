package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.TipoCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoCursoResponseDto;
import co.edu.unbosque.horaclass.academy.model.TipoCurso;
import co.edu.unbosque.horaclass.academy.repository.TipoCursoRepository;
import co.edu.unbosque.horaclass.academy.service.TipoCursoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoCursoServiceImpl implements TipoCursoService {

    private final TipoCursoRepository repository;
    private final ModelMapper modelMapper;

    public TipoCursoServiceImpl(TipoCursoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TipoCursoResponseDto crear(TipoCursoRequestDto request) {
        if (repository.existsById(request.getIdTipoCurso())) {
            throw new RuntimeException("Ya existe TipoCurso");
        }
        TipoCurso entity = modelMapper.map(request, TipoCurso.class);
        repository.save(entity);
        return modelMapper.map(entity, TipoCursoResponseDto.class);
    }

    @Override
    public TipoCursoResponseDto actualizar(int id, TipoCursoRequestDto request) {
        TipoCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoCurso no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, TipoCursoResponseDto.class);
    }

    @Override
    public List<TipoCursoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, TipoCursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoCursoResponseDto obtenerPorId(int id) {
        TipoCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoCurso no encontrado"));
        return modelMapper.map(entity, TipoCursoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("TipoCurso no encontrado");
        }
        repository.deleteById(id);
    }
}