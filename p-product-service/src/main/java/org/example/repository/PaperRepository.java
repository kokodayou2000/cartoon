package org.example.repository;

import org.example.model.PaperDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperRepository extends MongoRepository<PaperDO,String> {
    List<PaperDO> findAllByChapterId(String chapterId);

    List<PaperDO> findAllByChapterIdAndStatus(String chapterId,String status);
}
