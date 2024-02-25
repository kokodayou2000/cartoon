package org.example.repository;

import org.example.model.CartoonDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartoonRepository  extends PagingAndSortingRepository<CartoonDO,String> {


}

