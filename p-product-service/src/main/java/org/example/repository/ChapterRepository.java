package org.example.repository;

import org.example.model.ChapterDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends MongoRepository<ChapterDO,String> {

    List<ChapterDO> findChapterDOSByCartoonId(String cartoonDOId);

    List<ChapterDO> findAllByCartoonIdAndStatus(String cartoonDOId,String status);
}
