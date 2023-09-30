package com.capstone.datamate.Repository;

import com.capstone.datamate.Config.DatasourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatasourceConfigRepository extends JpaRepository<DatasourceConfig, Long>{
    DatasourceConfig findByName(String name);
}
