package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;

    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = getId(request);

        Meal meal = new Meal(id,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (id == null) {
            controller.create(meal);
        } else {
            controller.update(meal, id);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        if (userId != null && !userId.isEmpty()) {
            SecurityUtil.setAuthUserId(Integer.parseInt(userId));
        }
        switch (action == null ? "all" : action) {
            case "delete":
                Integer id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String paramStartDate = request.getParameter("startDate");
                String paramEndDate = request.getParameter("endDate");
                String paramStartTime = request.getParameter("startTime");
                String paramEndTime = request.getParameter("endTime");
                LocalDate startDate = paramStartDate == null || paramStartDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(paramStartDate);
                LocalDate endDate = paramEndDate == null || paramEndDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(paramEndDate);
                LocalTime startTime = paramStartTime == null || paramStartTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(paramStartTime);
                LocalTime endTime = paramEndTime == null || paramEndTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(paramEndTime);
                request.setAttribute("meals",
                        controller.getList(startDate, endDate, startTime, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private Integer getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        if (paramId.isEmpty()) {
            return null;
        }
        return Integer.parseInt(paramId);
    }
}
