package health.safe.api.opencdx.commons.model;

import cdx.media.v2alpha.IamUser;
import cdx.media.v2alpha.IamUserStatus;
import cdx.media.v2alpha.IamUserType;
import com.google.protobuf.Timestamp;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenCDXIAMUserModelTest {

    @Test
    void getProtobufMessage_1() {
        IamUser user = IamUser.newBuilder()
                .setId(ObjectId.get().toHexString())
                .setCreatedAt(Timestamp.getDefaultInstance())
                .setUpdatedAt(Timestamp.getDefaultInstance())
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setSystemName("system")
                .setEmailVerified(false)
                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                .setPhone("123-456-7890")
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        Assertions.assertEquals(user,model.getProtobufMessage());
    }
    @Test
    void getProtobufMessage_2() {
        IamUser user = IamUser.newBuilder()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email")
                .setSystemName("system")
                .setEmailVerified(false)
                .setStatus(IamUserStatus.IAM_USER_STATUS_ACTIVE)
                .setType(IamUserType.IAM_USER_TYPE_REGULAR)
                .setPhone("123-456-7890")
                .build();

        OpenCDXIAMUserModel model = new OpenCDXIAMUserModel(user);

        Assertions.assertNotEquals(user,model.getProtobufMessage());
    }

    @Test
    void getProtobufMessage_3() {

        OpenCDXIAMUserModel model = OpenCDXIAMUserModel.builder().build();

        Assertions.assertDoesNotThrow(() -> model.getProtobufMessage());
    }
}