package ua.dokat.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dokat.test.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {

}
