package yellowsunn.employee_management.service;

import org.springframework.data.domain.Sort;
import yellowsunn.employee_management.dto.TitleDto;

public interface TitleService {

    TitleDto.All findAll(Sort sort);
}
