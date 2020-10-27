package yellowsunn.employee_management.repository.custom;

import yellowsunn.employee_management.dto.DeptDto;

import java.util.List;

public interface EmployeeRepositoryCustom {

    List<DeptDto.GenderInfo> findCurGenderByDeptNo(String DeptNo);
}
