package org.example.repository;

import org.example.model.TempStorageDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempStorageRepository extends MongoRepository<TempStorageDO,String> {


    List<TempStorageDO> queryAllByUserId(String userId);
}
