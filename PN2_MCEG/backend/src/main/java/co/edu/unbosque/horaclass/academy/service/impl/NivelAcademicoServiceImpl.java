package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.NivelAcademicoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.NivelAcademicoResponseDto;
import co.edu.unbosque.horaclass.academy.model.NivelAcademico;
import co.edu.unbosque.horaclass.academy.repository.NivelAcademicoRepository;
import co.edu.unbosque.horaclass.academy.service.NivelAcademicoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NivelAcademicoServiceImpl implements NivelAcademicoService {

    private final NivelAcademicoRepository repository;
    private final ModelMapper modelMapper;

    public NivelAcademicoServiceImpl(NivelAcademicoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NivelAcademicoResponseDto crear(NivelAcademicoRequestDto request) {
        if (repository.existsById(request.getIdNivel())) {
            throw new RuntimeException("Ya existe un NivelAcademico con ese ID");
        }
        NivelAcademico entity = modelMapper.map(request, NivelAcademico.class);
        repository.save(entity);
        return modelMapper.map(entity, NivelAcademicoResponseDto.class);
    }

    @Override
    public NivelAcademicoResponseDto actualizar(int id, NivelAcademicoRequestDto request) {
        NivelAcademico entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NivelAcademico no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, NivelAcademicoResponseDto.class);
    }

    @Override
    public List<NivelAcademicoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, NivelAcademicoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NivelAcademicoResponseDto obtenerPorId(int id) {
        NivelAcademico entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("NivelAcademico no encontrado"));
        return modelMapper.map(entity, NivelAcademicoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("NivelAcademico no encontrado");
        }
        repository.deleteById(id);
    }
}