package com.quickship.logisticshub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickship.logisticshub.model.Packages;

@Repository
public interface PackageRepository extends JpaRepository<Packages, Integer> {
}