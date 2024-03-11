package org.example.repository;

import org.example.model.CollaborateDO;
import org.example.model.PaperDO;
import org.example.model.TempStorageDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborateRepository extends MongoRepository<CollaborateDO,String> {

    List<CollaborateDO> queryAllByPatternId(String userId);

    List<CollaborateDO> queryAllByCartoonIdAndPassAndDel(String cartoonId,Boolean pass,Boolean del);
}
