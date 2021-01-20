package com.kpi.lab4.services;

import com.kpi.lab4.dao.SessionDao;
import com.kpi.lab4.dto.SessionDto;
import com.kpi.lab4.entities.Session;
import com.kpi.lab4.exception.UnavailableException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class SessionService implements Service {
    private static Logger logger = LogManager.getLogger(SessionService.class);
    private final SessionDao sessionDao;

    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public void saveSession(SessionDto sessionDto) throws UnavailableException {
        try {
            LocalDateTime validUntil = LocalDateTime.now();
            validUntil = validUntil.plusHours(1);
            sessionDto.setValidUntil(validUntil);
            sessionDao.save(sessionDto);
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }
    }

    public void update(Session session) {
        try {
            sessionDao.update(session);
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }
    }

    public Optional<Session> findById(String sessionId) throws UnavailableException {
        try {
            return sessionDao.findById(sessionId);
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }
    }

    public void delete(String sessionId) throws UnavailableException {
        try {
            sessionDao.delete(sessionId);
        } catch (SQLException e) {
            logger.error("SQLException: " + e.getMessage());
            throw new UnavailableException();
        }
    }

    @Override
    public void finish() {
        sessionDao.releaseConnection();
    }
}
