package yellowsunn.employee_management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.DeptManager;
import yellowsunn.employee_management.repository.*;
import yellowsunn.employee_management.service.DepartmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DeptEmpRepository deptEmpRepository;
    private final DeptManagerRepository deptManagerRepository;
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public DeptDto.All findAll(Sort sort) {
        List<Department> findDepartments = departmentRepository.findAll(sort);

        return DeptDto.All.builder()
                .content(findDepartments)
                .size(findDepartments.size())
                .build();
    }

    @Override
    public DeptDto.Info findInfoByDeptNo(String deptNo) {
        // 부서 이름
        String deptName = departmentRepository.findById(deptNo)
                .map(Department::getDeptName).orElse(null);

        // 부서 총 인원
        long size = deptEmpRepository.countCurrentByDeptNo(deptNo);

        // 현직 부서장
        Optional<DeptManager> deptManagerOptional = deptManagerRepository.findCurrentByDeptNo(deptNo);
        String deptManager = deptManagerOptional
                .map(dm -> dm.getEmployee().getFirstName() + " " + dm.getEmployee().getLastName())
                .orElse(null);
        LocalDate fromDate = deptManagerOptional.map(DeptManager::getFromDate).orElse(null);

        // 부서내 직책별 연봉정보
        List<DeptDto.SalaryInfo> salaryInfoList = salaryRepository.findCurByDeptNoGroupByTitle(deptNo);

        // 부서내 성별 비율
        List<DeptDto.GenderInfo> genderInfoList = employeeRepository.findCurGenderByDeptNo(deptNo);

        // 역대 부서장
        List<DeptDto.DeptManagerHistory> deptManagerHistories = deptManagerRepository.findByDeptNo(deptNo)
                .stream().map(dm -> DeptDto.DeptManagerHistory.builder()
                        .deptManager(dm.getEmployee().getFirstName() + " " + dm.getEmployee().getLastName())
                        .fromDate(dm.getFromDate())
                        .toDate(dm.getToDate()).build()).collect(Collectors.toList());

        return DeptDto.Info.builder()
                .deptName(deptName)
                .size(size)
                .deptManager(deptManager)
                .fromDate(fromDate)
                .salaryInfo(salaryInfoList)
                .genderInfo(genderInfoList)
                .deptManagerHistory(deptManagerHistories)
                .build();
    }
}
