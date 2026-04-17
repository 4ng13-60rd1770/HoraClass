package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.ModalidadContratoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ModalidadContratoResponseDto;
import co.edu.unbosque.horaclass.academy.model.ModalidadContrato;
import co.edu.unbosque.horaclass.academy.repository.ModalidadContratoRepository;
import co.edu.unbosque.horaclass.academy.service.ModalidadContratoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModalidadContratoServiceImpl implements ModalidadContratoService {

    private final ModalidadContratoRepository repository;
    private final ModelMapper modelMapper;

    public ModalidadContratoServiceImpl(ModalidadContratoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ModalidadContratoResponseDto crear(ModalidadContratoRequestDto request) {
        if (repository.existsById(request.getIdModalidad())) {
            throw new RuntimeException("Ya existe una ModalidadContrato con ese ID");
        }
        ModalidadContrato entity = modelMapper.map(request, ModalidadContrato.class);
        repository.save(entity);
        return modelMapper.map(entity, ModalidadContratoResponseDto.class);
    }

    @Override
    public ModalidadContratoResponseDto actualizar(int id, ModalidadContratoRequestDto request) {
        ModalidadContrato entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModalidadContrato no encontrada"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, ModalidadContratoResponseDto.class);
    }

    @Override
    public List<ModalidadContratoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ModalidadContratoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ModalidadContratoResponseDto obtenerPorId(int id) {
        ModalidadContrato entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModalidadContrato no encontrada"));
        return modelMapper.map(entity, ModalidadContratoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ModalidadContrato no encontrada");
        }
        repository.deleteById(id);
    }
}