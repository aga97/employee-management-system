package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import yellowsunn.employee_management.dto.DeptDto;
import yellowsunn.employee_management.repository.custom.EmployeeRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static yellowsunn.employee_management.entity.QDeptEmp.deptEmp;
import static yellowsunn.employee_management.entity.QEmployee.employee;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public EmployeeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DeptDto.GenderInfo> findCurGenderByDeptNo(String DeptNo) {
        List<Tuple> tuples = queryFactory
                .select(employee.count(), employee.gender)
                .from(employee)
                .where(employee.empNo.in(
                        select(deptEmp.employee.empNo)
                                .from(deptEmp)
                                .where(deptEmp.department.deptNo.eq(DeptNo),
                                        deptEmp.toDate.eq(LocalDate.of(9999, 1, 1)))
                        )
                ).groupBy(employee.gender)
                .fetch();

        List<DeptDto.GenderInfo> genderInfoList = new ArrayList<>();

        tuples.forEach(tuple -> {
            Long size = tuple.get(employee.count());
            if (size != null) {
                genderInfoList.add(DeptDto.GenderInfo.builder()
                        .gender(tuple.get(employee.gender))
                        .size(size)
                        .build());
            }
        });

        return genderInfoList;
    }
}
