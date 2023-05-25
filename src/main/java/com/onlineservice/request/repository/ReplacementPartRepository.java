package com.onlineservice.request.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.onlineservice.request.model.ReplacementPart;

@Repository
public interface ReplacementPartRepository extends PagingAndSortingRepository<ReplacementPart, Long> {

}
