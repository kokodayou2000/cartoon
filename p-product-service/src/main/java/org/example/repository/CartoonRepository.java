package org.example.repository;

import org.example.model.CartoonDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartoonRepository  extends CrudRepository<CartoonDO,String> {
    List<CartoonDO> queryAllByTagsContaining(String tag);

}

