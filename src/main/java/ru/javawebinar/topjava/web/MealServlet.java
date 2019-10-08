package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao dao;
    private static final String ADD_OR_EDIT = "/meal.jsp";
    private static final String LIST = "/meals.jsp";

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        action = action == null ? "list" : action;
        String forward = "";
        switch (action.toLowerCase()) {
            case ("insert"):
                Meal meal = new Meal(null, null, 0);
                req.setAttribute("meal", meal);
                forward = ADD_OR_EDIT;
                break;
            case ("delete"):
                int mealId = Integer.parseInt(req.getParameter("mealId"));
                dao.delete(mealId);
                resp.sendRedirect("meals");
                return;
            case ("edit"):
                mealId = Integer.parseInt(req.getParameter("mealId"));
                meal = dao.getById(mealId);
                req.setAttribute("meal", meal);
                forward = ADD_OR_EDIT;
                break;
            default:
                List<MealTo> list = MealsUtil.getFiltered(dao.getAll(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        MealsUtil.DEFAULT_CALORIES_PER_DAY);
                req.setAttribute("meals", list);
                forward = LIST;
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("add or update Meal");
        req.setCharacterEncoding("UTF-8");
        int mealId = 0;
        try {
            mealId = Integer.parseInt(req.getParameter("mealId"));
        } catch (Exception e) {
        }
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime localDateTime = TimeUtil.parseLocalDateTime(req.getParameter("dateTime"));
        Meal meal = new Meal(mealId,
                localDateTime,
                description,
                calories);
        dao.save(meal);
        resp.sendRedirect("meals");
    }
}
