package com.zopa.ratecalculationsystem.service;

import java.util.List;

public interface CsvLoader {
    
    <T> List<T> load(Class<T> type, String fileName);

}

