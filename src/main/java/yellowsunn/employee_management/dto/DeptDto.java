package yellowsunn.employee_management.dto;

import lombok.Builder;
import lombok.Data;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.Gender;

import java.time.LocalDate;
import java.util.List;

/**
 * 부서 조회 dto
 */
public class DeptDto {

    /**
     * 모든 부서 목록을 가져온다.
     */
    @Data
    @Builder
    public static class All {
        private List<Department> content;
        private int size;
    }

    /**
     * 부서별로 조회하는데 사용하는 dto
     */
    @Data
    @Builder
    public static class Info {
        private long size;
        private String deptManager;
        private LocalDate fromDate; // 부임일
        private List<SalaryInfo> salaryInfo;
        private List<GenderInfo> genderInfo;
        private List<DeptManagerHistory> deptManagerHistory;
    }

    /**
     * 부서별 연봉 정보
     */
    @Data
    @Builder
    public static class SalaryInfo {
        private long size;
        private String title;
        private int minSalary;
        private int maxSalary;
        private double avgSalary;
    }

    /**
     * 부서별 성별 정보
     */
    @Data
    @Builder
    public static class GenderInfo {
        private Gender gender;
        private long size;
    }

    /**
     * 부서별 역대 부서장 정보
     */
    @Data
    @Builder
    public static class DeptManagerHistory {
        private String deptManager;
        private LocalDate fromDate;
        private LocalDate toDate;
    }
}
