package yellowsunn.employee_management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.repository.DepartmentRepository;
import yellowsunn.employee_management.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DeptDto.All findAll(Sort sort) {
        List<Department> findDepartments = departmentRepository.findAll(sort);

        DeptDto.All dto = new DeptDto.All();
        dto.setContent(findDepartments);
        dto.setSize(findDepartments.size());

        return dto;
    }

    @Override
    public DeptDto.Info findInfoByDeptNo(String DeptNo) {
        return null;
    }
}
