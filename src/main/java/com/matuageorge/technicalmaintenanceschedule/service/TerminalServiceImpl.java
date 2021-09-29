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
    public Terminal update(TerminalDto terminalDto) {
        terminalRepository.findById(terminalDto.getId());
        log.info("Terminal to update is found");
        return terminalRepository.save(modelMapper.map(terminalDto, Terminal.class));
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
}
