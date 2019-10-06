package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.controller.MealController;
import ru.javawebinar.topjava.controller.MemoryMealController;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import sun.rmi.runtime.Log;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealController mealController = new MemoryMealController();

    private static String INSERT_OR_EDIT_MEAL = "/editmeal.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            log.debug("delete meal id:{}", request.getParameter("mealId"));
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealController.delete(mealId);
            log.debug("forward to meals");
            forward = LIST_MEAL;
            List<MealTo> mealToList = MealsUtil.getFiltered(mealController.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
            request.setAttribute("mealToList", mealToList);
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("edit meal");
            log.debug("forward to editmeal");
            forward = INSERT_OR_EDIT_MEAL;
            int mealId = Integer.parseInt(request.getParameter("mealId"));

            Meal meal = mealController.get(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("mealToList")) {
            log.debug("forward to meals");
            forward = LIST_MEAL;
            List<MealTo> mealToList = MealsUtil.getFiltered(mealController.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
            request.setAttribute("mealToList", mealToList);
        } else {
            log.debug("forward to editmeal");
            forward = INSERT_OR_EDIT_MEAL;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealIdString = request.getParameter("mealId");
        log.debug("mealIdForUpdate:{}", request.getParameter("mealId"));
        if (mealIdString == null || mealIdString.isEmpty()) {
            mealController.save(dateTime, description, calories);
        } else {
            Meal meal = new Meal(Integer.parseInt(mealIdString), dateTime, description, calories);
            mealController.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        List<MealTo> mealToList = MealsUtil.getFiltered(mealController.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        request.setAttribute("mealToList", mealToList);
        view.forward(request, response);
    }

}
