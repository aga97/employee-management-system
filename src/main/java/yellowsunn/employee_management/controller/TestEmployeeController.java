package yellowsunn.employee_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yellowsunn.employee_management.dto.CurEmpInfoDto;
import yellowsunn.employee_management.dto.condition.EmpSearchCondition;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.repository.DepartmentRepository;
import yellowsunn.employee_management.service.EmpInfoService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestEmployeeController {

    private final EmpInfoService empInfoService;
    private final DepartmentRepository departmentRepository;

    @GetMapping("/test/employees")
    public String findCurrentEmployees(EmpSearchCondition condition, Pageable pageable, Model model) {
        List<Department> departments = departmentRepository.findAll();
        Page<CurEmpInfoDto> page = empInfoService.findCurrentEmployees(condition, pageable);
        List<CurEmpInfoDto> content = page.getContent();

        model.addAttribute("content", content);
        model.addAttribute("departments", departments);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("pageSize", page.getSize());
        model.addAttribute("currentPage", page.getNumber());


        //캐시
        model.addAttribute("empNo", condition.getEmpNo());
        model.addAttribute("firstName", condition.getFirstName());
        model.addAttribute("lastName", condition.getLastName());
        model.addAttribute("gender",
                condition.getGender() != null ? condition.getGender().toString() : "");
        model.addAttribute("deptNo", condition.getDeptNo());
        model.addAttribute("hireDate", condition.getHireDate());
        page.getSort().stream().findFirst().ifPresentOrElse(order -> {
            model.addAttribute("sort", order.getProperty() + (order.isDescending() ? ",desc" : ""));
        },() -> model.addAttribute("sort", ""));
        return "testEmpSearch";
    }
}
