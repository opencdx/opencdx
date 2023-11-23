package cdx.opencdx.iam.repository;

import cdx.opencdx.iam.model.OpenCDXIAMOrganizationModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenCDXIAMOrganizationRepository extends MongoRepository<OpenCDXIAMOrganizationModel, ObjectId> {
}
