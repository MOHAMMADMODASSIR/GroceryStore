package com.grocery.client.controller;

import com.grocery.client.dto.SectionsDTO;
import com.grocery.client.entity.Sections;
import com.grocery.client.service.SectionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserSectionsController {

    @Autowired
    SectionsService sectionsService;

    @GetMapping("/sections")
    private ResponseEntity<Map<String, List<SectionsDTO>>> sectionsList() {
        System.out.println("Sections Retrieved");
        List<Sections> sectionsList = sectionsService.fetchAllSections();
        List<SectionsDTO> sectionsDTOList = new ArrayList<>();
        for(Sections sections: sectionsList) {
            SectionsDTO sectionsDTO = new SectionsDTO();
            sectionsDTO.setSection_id(sections.getSectionId());
            sectionsDTO.setSection_name(sections.getSectionName());
            sectionsDTOList.add(sectionsDTO);
        }
        return new ResponseEntity<>(
                Map.of("data", sectionsDTOList),
                HttpStatus.OK
        );
    }

    @PostMapping("/search_section")
    private ResponseEntity<Map<String, List<SectionsDTO>>> searchedSectionList(@RequestBody String query) {
        List<Sections> sectionsList = sectionsService.fetchSearchedSections(query);
        List<SectionsDTO> sectionsDTOList = new ArrayList<>();
        for(Sections sections: sectionsList) {
            SectionsDTO sectionsDTO = new SectionsDTO();
            sectionsDTO.setSection_id(sections.getSectionId());
            sectionsDTO.setSection_name(sections.getSectionName());
            sectionsDTOList.add(sectionsDTO);
        }
        return new ResponseEntity<>(
                Map.of("data", sectionsDTOList),
                HttpStatus.OK
        );
    }
}