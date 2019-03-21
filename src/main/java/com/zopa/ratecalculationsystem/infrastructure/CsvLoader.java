package com.zopa.ratecalculationsystem.infrastructure;

import java.util.List;

public interface CsvLoader {
    
    <T> List<T> load(Class<T> type, String fileName);

}

