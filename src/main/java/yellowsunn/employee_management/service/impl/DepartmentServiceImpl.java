package yellowsunn.employee_management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yellowsunn.employee_management.dto.DepartmentsDto;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.repository.DepartmentRepository;
import yellowsunn.employee_management.service.DepartmentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentsDto findAll(Sort sort) {
        List<Department> findDepartments = departmentRepository.findAll(sort);

        DepartmentsDto departmentsDto = new DepartmentsDto();
        departmentsDto.setContent(findDepartments);
        departmentsDto.setSize(findDepartments.size());

        return departmentsDto;
    }
}
