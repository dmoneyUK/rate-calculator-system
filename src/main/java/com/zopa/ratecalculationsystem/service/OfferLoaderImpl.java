package com.zopa.ratecalculationsystem.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

@Slf4j
public class OfferLoaderImpl implements OfferLoader {
    
    @Override public List<Offer> loadOffers(String fileName) {
        try {
            File file = new ClassPathResource(fileName).getFile();
            
            CsvSchema bootstrapSchema = CsvSchema.emptySchema()
                                                 .withHeader();
            CsvMapper mapper = new CsvMapper();
            mapper.configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            MappingIterator<Offer> readValues =
                    mapper.readerWithSchemaFor(Offer.class)
                          .with(bootstrapSchema)
                          .readValues(file);
            
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }
}
