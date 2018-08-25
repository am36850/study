package com.project.batch.springbatchdateconversion.repository;

import com.project.batch.springbatchdateconversion.domain.BseHistoricalData;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BseHistoricalDataRepository extends JpaRepository<BseHistoricalData, Integer> {
}
