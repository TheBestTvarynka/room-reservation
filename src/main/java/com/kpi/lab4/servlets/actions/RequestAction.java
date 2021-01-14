package com.kpi.lab4.servlets.actions;

import com.kpi.lab4.dto.CreateRequestDto;
import com.kpi.lab4.enums.RoomType;
import com.kpi.lab4.exception.UnavailableException;
import com.kpi.lab4.services.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RequestAction implements Action {
    private RequestService service;

    public RequestAction(RequestService service) {
        this.service = service;
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/request.jsp").forward(request, response);
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        try {
            service.createRequest(new CreateRequestDto(
                    Integer.parseInt(request.getParameter("seatNumber")),
                    RoomType.valueOf(request.getParameter("type")),
                    request.getParameter("phone"),
                    dateFormat.parse(request.getParameter("dateFrom")),
                    dateFormat.parse(request.getParameter("dateTo"))
            ));
            request.setAttribute(
                    "message",
                    "All success. Our manager will choose the most suitable room for you."
            );
        } catch(IllegalArgumentException | UnavailableException e) {
            request.setAttribute("error", e.getMessage());
        } catch (ParseException | NullPointerException e) {
            request.setAttribute("error", "Wrong request parameters!");
        }
        request.getRequestDispatcher("/jsp/request.jsp").forward(request, response);
    }
}
