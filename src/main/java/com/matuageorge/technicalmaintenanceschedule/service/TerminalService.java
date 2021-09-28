package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import org.springframework.data.domain.Page;

public interface TerminalService {
    Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException;

    Terminal update(TerminalDto terminalDto) throws ValidationException, NotFoundException;

    void delete(Long terminalId) throws ValidationException, NotFoundException;

    Page<Terminal> findAll(Integer page, Integer pageSize) throws NotFoundException;

    Terminal findById(Long id) throws ValidationException, NotFoundException;
}
