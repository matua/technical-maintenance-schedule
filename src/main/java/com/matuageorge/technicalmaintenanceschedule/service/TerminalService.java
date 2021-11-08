package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TerminalService {
    Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException;

    Terminal update(TerminalDto terminalDto) throws ValidationException, NotFoundException;

    void delete(Long terminalId) throws ValidationException, NotFoundException;

    Page<Terminal> findAllByPage(Integer page, Integer pageSize) throws NotFoundException;

    Terminal findById(Long id) throws ValidationException, NotFoundException;

    Optional<Terminal> findByName(String terminalName);

    void deleteByName(String terminalName) throws NotFoundException;

    List<Terminal> updateListOfTerminalsInDb(TerminalType terminalType) throws NotFoundException;

    List<Terminal> findAll();
}
