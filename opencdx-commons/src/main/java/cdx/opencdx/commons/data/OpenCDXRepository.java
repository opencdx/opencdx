package cdx.opencdx.commons.data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;


@NoRepositoryBean
public interface OpenCDXRepository<T> extends MongoRepository<T, ObjectId>{
}
