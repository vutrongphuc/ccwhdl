package iuh.course.hpt.repository;

import iuh.course.hpt.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    // find by youtube id
    Course findByYtId(String ytId);
}
