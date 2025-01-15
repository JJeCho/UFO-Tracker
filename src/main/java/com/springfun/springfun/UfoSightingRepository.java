package com.springfun.springfun;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UfoSightingRepository extends JpaRepository<UfoSighting, Long>, UfoSightingRepositoryCustom {
}
