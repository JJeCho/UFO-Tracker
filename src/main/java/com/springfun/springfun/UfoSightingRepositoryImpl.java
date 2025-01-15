package com.springfun.springfun;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UfoSightingRepositoryImpl implements UfoSightingRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<UfoSighting> findByFilters(String city, String state, LocalDate startDate, LocalDate endDate, String shape, Sort sort) {
        StringBuilder query = new StringBuilder("SELECT u FROM UfoSighting u WHERE 1=1");

        if (city != null && !city.isEmpty()) query.append(" AND u.city = :city");
        if (state != null && !state.isEmpty()) query.append(" AND u.state = :state");
        if (startDate != null) query.append(" AND u.date >= :startDate");
        if (endDate != null) query.append(" AND u.date <= :endDate");
        if (shape != null && !shape.isEmpty()) query.append(" AND u.shape = :shape");

        query.append(sort.isSorted() ? " ORDER BY " + getOrderString(sort) : "");

        TypedQuery<UfoSighting> typedQuery = entityManager.createQuery(query.toString(), UfoSighting.class);
        setQueryParameters(typedQuery, city, state, startDate, endDate, shape);

        return typedQuery.getResultList();
    }

    private void setQueryParameters(TypedQuery<UfoSighting> query, String city, String state, LocalDate startDate, LocalDate endDate, String shape) {
        if (city != null && !city.isEmpty()) query.setParameter("city", city);
        if (state != null && !state.isEmpty()) query.setParameter("state", state);
        if (startDate != null) query.setParameter("startDate", startDate);
        if (endDate != null) query.setParameter("endDate", endDate);
        if (shape != null && !shape.isEmpty()) query.setParameter("shape", shape);
    }

    private String getOrderString(Sort sort) {
        return sort.stream()
                .map(order -> "u." + order.getProperty() + " " + order.getDirection().name())
                .collect(Collectors.joining(", "));
    }

}
