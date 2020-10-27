package yellowsunn.employee_management.dto;

import lombok.Data;
import yellowsunn.employee_management.entity.Department;
import yellowsunn.employee_management.entity.Gender;

import java.util.List;

public class DeptDto {

    @Data
    public static class All {
        private List<Department> content;
        private int size;
    }

    @Data
    public static class Info {
        private long size;
        private String deptManager;
        private SalaryInfo salaryInfo;
        private GenderInfo genderInfo;
    }

    @Data
    public static class SalaryInfo {
        private long size;
        private String title;
        private int minSalary;
        private int maxSalary;
        private double avgSalary;
    }

    @Data
    public static class GenderInfo {
        private Gender gender;
        private long size;
    }
}
