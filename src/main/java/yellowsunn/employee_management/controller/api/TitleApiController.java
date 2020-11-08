package yellowsunn.employee_management.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yellowsunn.employee_management.dto.TitleDto;
import yellowsunn.employee_management.service.TitleService;

@RestController
@RequiredArgsConstructor
public class TitleApiController {

    private final TitleService titleService;

    /**
     * 전체 직책 목록을 반환한다.
     * <p>title로 정렬 가능</p>
     *
     * <pre>
     * Example1: title 내림차순 정렬
     *
     * localhost:8080/api/departments?sort=title,desc
     * </pre>
     */
    @GetMapping("/api/titles")
    public TitleDto.All findDepartments(Sort sort) {
        return titleService.findAll(sort);
    }
}
