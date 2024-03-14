package org.example.repository;

import org.example.model.PaperDO;
import org.example.model.RawPadDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RawPadRepository extends MongoRepository<RawPadDO,String> {
    Optional<List<RawPadDO>> queryAllByUserId(String userId);
}
