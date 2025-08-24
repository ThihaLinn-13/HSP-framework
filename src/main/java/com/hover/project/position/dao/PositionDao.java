package com.hover.project.position.dao;

import com.hover.project.position.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionDao extends JpaRepository<Position, UUID> {

    Page<Position> findAllByRecordStatus(int recordStatus, Pageable pageable);

    Optional<Position> findByIdAndRecordStatus(UUID id, int recordStatus);

}
