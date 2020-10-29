package yellowsunn.employee_management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yellowsunn.employee_management.dto.EmpSearchDto;

public interface EmployeeService {

    /**
     * 주어진 조건에 맞는 직원 검색 정보를 가져온다.
     */
    Slice<EmpSearchDto.Info> findSearchInfoByCondition(EmpSearchDto.Condition condition, Pageable pageable);
}
