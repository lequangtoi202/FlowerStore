package com.quangtoi.flowerstore.service;

import com.quangtoi.flowerstore.dto.ImportDto;
import com.quangtoi.flowerstore.model.Import;

import java.util.List;

public interface ImportService {
    ImportDto save(ImportDto importInfo);
    List<ImportDto> getAll();
    ImportDto getById(Long id);
    ImportDto update(ImportDto importInfo, Long id);
    void delete(Long id);

}
