package com.quangtoi.flowerstore.repository;

import com.quangtoi.flowerstore.dto.StatisticResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public StatisticResponse avenueByMonthAndYear(int month, int year){
        String jpql = "SELECT SUM(o.totalAmount), SUM(o.totalPrice) FROM Order o " +
        "WHERE FUNCTION('MONTH', o.orderDate) = :month AND FUNCTION('YEAR', o.orderDate) = :year " +
                "AND o.orderStatus = 2";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("month", month);
        query.setParameter("year", year);
        Object[] result = (Object[]) query.getSingleResult();
        StatisticResponse statisticResponse = new StatisticResponse((int) (result[0] == null ? 0 : result[0]), (double)(result[1] == null ? 0.0 : result[1]));
        return statisticResponse;
    }

    public StatisticResponse avenueByFlowerId(Long flowerId){
        String jpql = "SELECT SUM(od.quantity), SUM(od.subTotal)" +
                " FROM Order o" +
                " INNER JOIN OrderDetail od ON od.order.id = o.id" +
                " WHERE od.flower.id = :flowerId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("flowerId", flowerId);
        Object[] result = (Object[]) query.getSingleResult();
        StatisticResponse statisticResponse = new StatisticResponse(Math.toIntExact((result[0] == null ? 0 : (Long) result[0])), (double)(result[1] == null ? 0.0 : result[1]));

        return statisticResponse;
    }
}
