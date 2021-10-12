package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.repository.TerminalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TerminalServiceImpl implements TerminalService {
    private final TerminalRepository terminalRepository;
    private final ModelMapper modelMapper;

    @Override
    public Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException {
        Optional<Terminal> terminalToSave = terminalRepository.findByName(terminalDto.getName());
        if (terminalToSave.isPresent()) {
            log.info("Terminal to save with such name already exists");
            throw new ResourceAlreadyExistsException("Terminal with such name already exists");
        } else {
            try {
                return terminalRepository.save(modelMapper.map(terminalDto, Terminal.class));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Terminal cannot be null");
            }
        }
    }

    @Override
    public Terminal update(TerminalDto terminalDto) throws NotFoundException {
        Optional<Terminal> terminalToUpdate = terminalRepository.findByName(terminalDto.getName());
        Terminal savedTerminal;
        if (terminalToUpdate.isPresent()) {
            Terminal terminal = terminalToUpdate.get();
            log.info("Terminal to update is found");
            terminal.setName(terminalDto.getName());
            terminal.setActive(terminalDto.getActive());
            terminal.setType(terminalDto.getType());
            savedTerminal = terminalRepository.save(modelMapper.map(terminal, Terminal.class));
        } else {
            log.info("Terminal to update is NOT found");
            throw new NotFoundException("Terminal to update is NOT found");
        }
        return savedTerminal;
    }

    @Override
    public void delete(Long terminalId) throws NotFoundException {
        findById(terminalId);
        terminalRepository.deleteById(terminalId);
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
        return terminalRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Terminal is not found")
        );
    }

    @Override
    public Optional<Terminal> findByName(String terminalName) {
        return terminalRepository.findByName(terminalName);
    }

    @Override
    public void deleteByName(String terminalName) throws NotFoundException {
        Terminal task = findByName(terminalName).orElseThrow(
                () -> new NotFoundException("Task to delete is not found"));
        delete(task.getId());
    }
}
