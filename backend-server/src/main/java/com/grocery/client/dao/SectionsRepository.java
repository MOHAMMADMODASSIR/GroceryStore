package com.grocery.client.dao;

import com.grocery.client.entity.Sections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionsRepository extends JpaRepository<Sections, Integer> {
    List<Sections> findAll();

    List<Sections> findBySectionNameContaining(String query);

//    @Query(value = "SELECT * FROM sections WHERE section_name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
//    List<Sections> findBySectionNameContaining(@Param("name") String name);

}
