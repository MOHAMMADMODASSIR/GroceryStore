package com.grocery.client.service.impl;

import com.grocery.client.dao.SectionsRepository;
import com.grocery.client.entity.Sections;
import com.grocery.client.service.SectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionsServiceImpl implements SectionsService {

    @Autowired
    SectionsRepository sectionsRepository;

    @Override
    public Sections searchSection(int sectionId) {
        Sections section = sectionsRepository.findById(sectionId).orElse(null);
        return section;
    }

    @Override
    public void deleteSectionById(int sectionId) {
        sectionsRepository.deleteById(sectionId);
    }

    @Override
    public Sections createSection(Sections section) {
        return sectionsRepository.save(section);
    }

    @Override
    public List<Sections> fetchAllSections() {
        List<Sections> sectionsList = sectionsRepository.findAll();
        return sectionsList;
    }

    @Override
    public List<Sections> fetchSearchedSections(String query) {
        List<Sections> sectionsList = sectionsRepository.findBySectionNameContaining(query);
        return sectionsList;
    }
}
