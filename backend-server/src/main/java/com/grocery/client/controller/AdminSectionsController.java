package com.grocery.client.controller;

import com.grocery.client.dto.SectionsDTO;
import com.grocery.client.entity.Products;
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
@RequestMapping("/admin")
public class AdminSectionsController {

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

    @PostMapping("/addSection")
    private ResponseEntity<Map<String, String>> addSection(@RequestBody SectionsDTO addSection) {
        System.out.println(addSection.getSection_id()+" "+addSection.getSection_name());
        if(sectionsService.searchSection(addSection.getSection_id()) == null) {
            Sections newSection = getSection(addSection);
            sectionsService.createSection(newSection);
            return new ResponseEntity<>(
                    Map.of("message", "Added Section succesfully"),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(
                    Map.of("message", "Sections already exists"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping("/editSection")
    private ResponseEntity<Map<String, String>> editSection(@RequestBody SectionsDTO section) {
        Sections editSection = sectionsService.searchSection(section.getSection_id());
        if(editSection!=null) {
            editSection.setSectionName(section.getSection_name());
            sectionsService.createSection(editSection);
            return new ResponseEntity<>(
                    Map.of("message", "section updated"),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(
                    Map.of("message", "Section id does not exist"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/deleteSection/{id}")
    private ResponseEntity<Map<String,String>> deleteSection(@PathVariable int id) {
        System.out.println(id);
        if(sectionsService.searchSection(id)!=null) {
            sectionsService.deleteSectionById(id);
            return new ResponseEntity<>(
                    Map.of("message", "Deleted"),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                Map.of("message", "Error"),
                HttpStatus.NOT_FOUND
        );
    }

    private Sections getSection(SectionsDTO sectionsDTO) {
        Sections section = new Sections();
        section.setSectionId(sectionsDTO.getSection_id());
        section.setSectionName(sectionsDTO.getSection_name());
        return section;
    }
}