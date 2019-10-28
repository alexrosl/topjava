package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(em.getReference(User.class, userId));
            em.persist(meal);
            em.flush();
            return meal;
        } else {
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaUpdate<Meal> update = builder.createCriteriaUpdate(Meal.class);
            Root<Meal> mealRoot = update.from(Meal.class);
            update.set("dateTime", meal.getDateTime());
            update.set("description", meal.getDescription());
            update.set("calories", meal.getCalories());
            update.where(builder.equal(mealRoot.get("user").get("id"), userId), builder.equal(mealRoot.get("id"), meal.getId()));
            return em.createQuery(update).executeUpdate() == 0 ? null : meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Meal> criteriaQuery = builder.createQuery(Meal.class);
        Root<Meal> mealRoot = criteriaQuery.from(Meal.class);
        criteriaQuery.select(mealRoot)
                .where(builder.equal(mealRoot.get("user").get("id"), userId), builder.equal(mealRoot.get("id"), id));
        List<Meal> list = em.createQuery(criteriaQuery)
                .getResultList();
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.BETWEEN, Meal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userId", userId)
                .getResultList();
    }
}