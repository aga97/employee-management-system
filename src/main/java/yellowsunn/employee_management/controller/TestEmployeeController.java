package yellowsunn.employee_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yellowsunn.employee_management.dto.EmpDto;
import yellowsunn.employee_management.dto.EmpSearchDto;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.repository.DepartmentRepository;
import yellowsunn.employee_management.service.EmployeeService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestEmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/test/employees")
    public String findCurrentEmployees(EmpSearchDto.Condition condition, Pageable pageable, Model model) {
        List<Department> departments = departmentRepository.findAll();
        Slice<EmpSearchDto.Info> page = employeeService.findSearchInfoByCondition(condition, pageable);
        List<EmpSearchDto.Info> content = page.getContent();

        model.addAttribute("content", content);
        model.addAttribute("departments", departments);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("pageSize", page.getSize());
        model.addAttribute("currentPage", page.getNumber());


        //캐시
        model.addAttribute("empNo", condition.getEmpNo());
        model.addAttribute("firstName", condition.getFirstName());
        model.addAttribute("lastName", condition.getLastName());
        model.addAttribute("gender",
                condition.getGender() != null ? condition.getGender().toString() : "");
//        model.addAttribute("deptNo", condition.getDeptNo());
        model.addAttribute("hireDate", condition.getHireDate());
        page.getSort().stream().findFirst().ifPresentOrElse(order -> {
            model.addAttribute("sort", order.getProperty() + (order.isDescending() ? ",desc" : ""));
        },() -> model.addAttribute("sort", ""));
        return "testEmpSearch";
    }

    /**
     * 특정 직원의 기본정보를 반환한다.
     */
    @GetMapping("/test/employee/{empNo}")
    public String findEmployee(@PathVariable("empNo") Integer empNo, Model model) {
        EmpDto.Info info = employeeService.findInfoByEmpNo(empNo);
        EmpDto.DeptHistory deptHistory = employeeService.findDeptHistoryByEmpNo(empNo);
        EmpDto.TitleHistory titleHistory = employeeService.findTitleHistoryByEmpNo(empNo);
        EmpDto.SalaryHistory salaryHistory = employeeService.findSalaryHistoryByEmpNo(empNo);
        model.addAttribute("info", info);
        model.addAttribute("deptHistory", deptHistory);
        model.addAttribute("titleHistory", titleHistory);
        model.addAttribute("salaryHistory", salaryHistory);
        return "testEmpInfo";
    }
}
