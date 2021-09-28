package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TerminalDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TerminalServiceImpl implements TerminalService {
    @Override
    public Terminal save(TerminalDto terminalDto) throws ValidationException, ResourceAlreadyExistsException {
        return null;
    }

    @Override
    public Terminal update(Long terminalId, TerminalDto terminalDto) throws ValidationException, NotFoundException {
        return null;
    }

    @Override
    public void delete(Long terminalId) throws ValidationException, NotFoundException {

    }

    @Override
    public Page<Terminal> findAll(Integer page, Integer pageSize) throws NotFoundException {
        return null;
    }

    @Override
    public Terminal findById(Long id) throws ValidationException, NotFoundException {
        return null;
    }
}
