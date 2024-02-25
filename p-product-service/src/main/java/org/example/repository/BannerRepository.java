package org.example.repository;

import org.example.model.BannerDO;
import org.example.model.CartoonDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends MongoRepository<BannerDO,String> {

    List<BannerDO> findBannerDOByActive(Boolean active);
}
