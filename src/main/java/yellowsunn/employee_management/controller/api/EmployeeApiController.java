package yellowsunn.employee_management.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import yellowsunn.employee_management.dto.CurEmpInfoDto;
import yellowsunn.employee_management.dto.condition.EmpSearchCondition;
import yellowsunn.employee_management.service.EmployeeService;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class EmployeeApiController {

    private final EmployeeService employeeService;

    /**
     * 주어진 조건에 맞는 가장 최근 직원 정보들을 반환한다.
     * <p>조건은 {@link EmpSearchCondition} 의 필드로 결정되며 해당 필드로 정렬을 수행할 수도 있다.</p>
     * <pre>
     * Example1: 조건 없음
     *
     * localhost:8080/api/employees
     *
     *
     * Example2: empNo가 12로 시작하고 firstName이 f로 시작하는 조건
     *
     * localhost:8080/api/employees?empNo=12&firstName=fa
     *
     *
     * Example3: empNo 내림차순 정렬
     *
     * localhost:8080/api/employees?sort=empNo,desc
     * </pre>
     */
    @GetMapping("/api/employees")
    public Page<CurEmpInfoDto> findCurrentEmployees(EmpSearchCondition condition, Pageable pageable) {
        return employeeService.findCurrentEmployees(condition, pageable);
    }
}
