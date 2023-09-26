package health.safe.api.opencdx.communications.repository;

import health.safe.api.opencdx.communications.model.OpenCDXConnectedTestModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public class OpenCDXConnectedTestsRepository {
    /**
     * MongoRepository for the email-template collection.
     */
    @Repository
    public interface OpenCDXConnnectedTestsRepository extends MongoRepository<OpenCDXConnectedTestModel, ObjectId> {}

}
