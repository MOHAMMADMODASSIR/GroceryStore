package com.grocery.client.service;

import com.grocery.client.entity.Sections;

import java.util.List;

public interface SectionsService {

    Sections searchSection(int sectionId);

    void deleteSectionById(int sectionId);

    public Sections createSection(Sections section);

    public List<Sections> fetchAllSections();

    public List<Sections> fetchSearchedSections(String query);

}
