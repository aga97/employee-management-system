package yellowsunn.employee_management.repository.custom.impl;

import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.repository.custom.EmployeeRepositoryCustom;

import java.util.List;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @Override
    public List<DeptDto.GenderInfo> findCurGenderByDeptNo(String DeptNo) {
        return null;
    }
}
