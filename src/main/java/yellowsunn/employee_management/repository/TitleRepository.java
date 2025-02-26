package yellowsunn.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yellowsunn.employee_management.entity.Employee;
import yellowsunn.employee_management.entity.Title;
import yellowsunn.employee_management.entity.id.TitleId;
import yellowsunn.employee_management.repository.custom.TitleRepositoryCustom;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, TitleId>, TitleRepositoryCustom {
}
