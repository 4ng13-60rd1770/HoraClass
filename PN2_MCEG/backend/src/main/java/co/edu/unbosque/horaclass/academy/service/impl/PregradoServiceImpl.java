package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.PregradoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.PregradoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Pregrado;
import co.edu.unbosque.horaclass.academy.repository.PregradoRepository;
import co.edu.unbosque.horaclass.academy.service.PregradoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PregradoServiceImpl implements PregradoService {

    private final PregradoRepository repository;
    private final ModelMapper modelMapper;

    public PregradoServiceImpl(PregradoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PregradoResponseDto crear(PregradoRequestDto request) {
        if (repository.existsById(request.getIdPregrado())) {
            throw new RuntimeException("Ya existe un Pregrado con ese ID");
        }
        Pregrado entity = modelMapper.map(request, Pregrado.class);
        repository.save(entity);
        return modelMapper.map(entity, PregradoResponseDto.class);
    }

    @Override
    public PregradoResponseDto actualizar(int id, PregradoRequestDto request) {
        Pregrado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregrado no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, PregradoResponseDto.class);
    }

    @Override
    public List<PregradoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, PregradoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PregradoResponseDto obtenerPorId(int id) {
        Pregrado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregrado no encontrado"));
        return modelMapper.map(entity, PregradoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pregrado no encontrado");
        }
        repository.deleteById(id);
    }
}