package yellowsunn.employee_management.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import yellowsunn.employee_management.entity.DeptManager;
import yellowsunn.employee_management.repository.custom.DeptManagerRepositoryCustom;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static yellowsunn.employee_management.entity.QDeptManager.deptManager;

public class DeptManagerRepositoryCustomImpl implements DeptManagerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DeptManagerRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DeptManager> findByDeptNo(String deptNo) {
        return queryFactory
                .selectFrom(deptManager)
                .join(deptManager.employee).fetchJoin()
                .where(deptManager.department.deptNo.eq(deptNo))
                .orderBy(deptManager.toDate.desc())
                .fetch();
    }

    @Override
    public Optional<DeptManager> findCurrentByDeptNo(String deptNo) {
        DeptManager findDeptManger = queryFactory
                .selectFrom(deptManager)
                .join(deptManager.employee).fetchJoin()
                .where(deptManager.department.deptNo.eq(deptNo),
                        deptManager.toDate.eq(LocalDate.of(9999, 1, 1)))
                .fetchOne();

        return Optional.ofNullable(findDeptManger);
    }
}
