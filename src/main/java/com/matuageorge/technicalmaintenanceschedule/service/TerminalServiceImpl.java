package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.repository.TerminalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TerminalServiceImpl implements TerminalService {

    private final TerminalRepository terminalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TerminalServiceImpl(TerminalRepository terminalRepository, ModelMapper modelMapper) {
        this.terminalRepository = terminalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException {
        Optional<Terminal> terminalToSave = terminalRepository.findById(terminalDto.getId());
        if (terminalToSave.isEmpty()) {
            try {
                return terminalRepository.save(modelMapper.map(terminalDto, Terminal.class));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Terminal cannot be null");
            }
        } else {
            log.info("Terminal to save with such id already exists");
            throw new ResourceAlreadyExistsException("Terminal with such id already exists");
        }
    }

    @Override
    public Terminal update(TerminalDto terminalDto) throws ValidationException, NotFoundException {
        Optional<Terminal> terminalToUpdate = terminalRepository.findById(terminalDto.getId());
        Terminal savedTerminal;
        if (terminalToUpdate.isPresent()) {
            log.info("Terminal to update is found");
            savedTerminal = terminalRepository.save(modelMapper.map(terminalDto, Terminal.class));
        } else {
            log.info("Terminal to update is NOT found");
            throw new NotFoundException("Terminal to update is NOT found");
        }
        return savedTerminal;
    }

    @Override
    public void delete(Long terminalId) throws ValidationException, NotFoundException {
        findById(terminalId);
        try {
            terminalRepository.deleteById(terminalId);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Terminal not found");
        }
    }

    @Override
    public Page<Terminal> findAll(Integer page, Integer pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Terminal> terminalsPage = terminalRepository.findAll(pageable);

        if (terminalsPage.hasContent()) {
            return terminalsPage;
        } else {
            throw new NotFoundException("No terminals found");
        }
    }

    @Override
    public Terminal findById(Long id) throws NotFoundException {
        Optional<Terminal> terminalToFind = terminalRepository.findById(id);
        if (terminalToFind.isPresent()) {
            return terminalToFind.get();
        } else {
            throw new NotFoundException("Terminal is not found");
        }
    }
}
