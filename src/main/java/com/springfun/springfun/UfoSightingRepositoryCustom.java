package com.springfun.springfun;

import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface UfoSightingRepositoryCustom {
    List<UfoSighting> findByFilters(String city, String state, LocalDate startDate, LocalDate endDate, String shape, Sort sort);
}
