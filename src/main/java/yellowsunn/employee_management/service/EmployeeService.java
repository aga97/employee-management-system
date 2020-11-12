package yellowsunn.employee_management.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import yellowsunn.employee_management.dto.EmpDto;
import yellowsunn.employee_management.dto.EmpSearchDto;

public interface EmployeeService {

    /**
     * 주어진 조건에 맞는 직원 검색 정보를 가져온다.
     */
    Slice<EmpSearchDto.Info> findSearchInfoByCondition(EmpSearchDto.Condition condition, Pageable pageable);

    /**
     * 특정 직원의 기본 정보를 가져온다.
     */
    EmpDto.Info findInfoByEmpNo(Integer empNo);

    /**
     * 특정 직원이 근무했던 부서 내역을 가져온다.
     */
    EmpDto.DeptHistory findDeptHistoryByEmpNo(Integer empNo);

    /**
     * 특정 직원이 맡았던 직책 내역을 가져온다.
     */
    EmpDto.TitleHistory findTitleHistoryByEmpNo(Integer empNo);

    /**
     * 특정 직원이 받았던 연봉 내역을 가져온다.
     */
    EmpDto.SalaryHistory findSalaryHistoryByEmpNo(Integer empNo);

    /**
     * 직원 생성
     */
    EmpDto.Success create(EmpDto.Create dto);

    /**
     * 직원 정보 수정
     */
    EmpDto.Success update(EmpDto.Update dto);

    EmpDto.Success retire(Integer empNo);
}
