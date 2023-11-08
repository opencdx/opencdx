package cdx.opencdx.connected.test.repository;

import cdx.opencdx.connected.test.model.OpenCDXDeviceModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpenCDXDeviceRepository extends MongoRepository<OpenCDXDeviceModel, ObjectId> {
}
