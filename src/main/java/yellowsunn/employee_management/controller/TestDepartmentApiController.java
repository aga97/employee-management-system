package yellowsunn.employee_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.service.DepartmentService;

@Controller
@RequiredArgsConstructor
public class TestDepartmentApiController {

    private final DepartmentService departmentService;

    /**
     * 전체 부서 목록을 반환한다.
     * <p>deptNo, detName으로 정렬 가능</p>
     *
     * <pre>
     * Example1: detNo 오름차순 정렬
     *
     * localhost:8080/api/departments?sort=deptNo
     *
     *
     * Example2: detName 내림차순 정렬
     *
     * localhost:8080/api/departments?sort=deptName,desc
     * </pre>
     */
    @GetMapping("/test/departments")
    public String findDepartments(Sort sort, Model model) {
        DeptDto.All all = departmentService.findAll(sort);
        model.addAttribute("departments", all);
        return "testDepartments";
    }

    /**
     * 부서별 부서 정보를 반환한다. <br/>
     * 부서 조회에는 deptNo 사용
     * <pre>
     * Example: Marketing(d001) 부서 조회
     *
     * localhost:8080/api/departments/d001
     * </pre>
     */
    @GetMapping("/test/departments/{deptNo}")
    public String findDepartmentInfo(@PathVariable("deptNo") String deptNo, Model model) {
        DeptDto.Info info = departmentService.findInfoByDeptNo(deptNo);
        model.addAttribute("info", info);
        return "testDeptInfo";
    }
}
