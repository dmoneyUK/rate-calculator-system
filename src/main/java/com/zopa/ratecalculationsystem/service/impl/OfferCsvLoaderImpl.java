package com.zopa.ratecalculationsystem.service.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zopa.ratecalculationsystem.model.Offer;
import com.zopa.ratecalculationsystem.service.CsvLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
public class OfferCsvLoaderImpl implements CsvLoader {
    
    public static final String DEFAULT_FILE_NAME = "test_market_data.csv";
    
    @Override
    public <T> List<T> load(Class<T> type, String fileName) {
        try {
            if(isEmpty(fileName)){
                log.warn("Offer csv filename is absent. Default filename {} is used.", DEFAULT_FILE_NAME);
                fileName = DEFAULT_FILE_NAME;
            }
            File file = new ClassPathResource(fileName).getFile();
    
            CsvSchema bootstrapSchema = CsvSchema.emptySchema()
                                                 .withHeader();
            CsvMapper mapper = new CsvMapper();
            mapper.configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            MappingIterator<T> readValues =
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
