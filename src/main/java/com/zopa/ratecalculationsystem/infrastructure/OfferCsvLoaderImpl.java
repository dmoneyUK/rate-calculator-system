package com.zopa.ratecalculationsystem.infrastructure;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@Component
public class OfferCsvLoaderImpl implements CsvLoader {
    
    @Override
    public <T> List<T> load(Class<T> type, String fileName) throws IOException {
        try {
            // Improvement: Check file name is null or empty and file format
            File file = new File(fileName);
    
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
            throw e;
        }
    }
    
}
