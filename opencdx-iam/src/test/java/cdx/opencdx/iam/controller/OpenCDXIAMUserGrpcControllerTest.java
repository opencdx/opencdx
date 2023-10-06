package cdx.opencdx.iam.controller;

import cdx.media.v2alpha.*;
import cdx.opencdx.iam.service.OpenCDXIAMUserService;
import cdx.opencdx.iam.service.impl.OpenCDXIAMUserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import health.safe.api.opencdx.commons.model.OpenCDXIAMUserModel;
import health.safe.api.opencdx.commons.repository.OpenCDXIAMUserRepository;
import health.safe.api.opencdx.commons.service.OpenCDXAuditService;
import io.grpc.stub.StreamObserver;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.cloud.config.enabled=false")
class OpenCDXIAMUserGrpcControllerTest {

    @Autowired
    OpenCDXAuditService openCDXAuditService;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    OpenCDXIAMUserRepository openCDXIAMUserRepository;

    OpenCDXIAMUserService openCDXIAMUserService;

    OpenCDXIAMUserGrpcController openCDXIAMUserGrpcController;

    @BeforeEach
    void setUp() {
        this.openCDXIAMUserRepository = Mockito.mock(OpenCDXIAMUserRepository.class);
        Mockito.when(this.openCDXIAMUserRepository.save(Mockito.any(OpenCDXIAMUserModel.class)))
                .thenAnswer(new Answer<OpenCDXIAMUserModel>() {
                    @Override
                    public OpenCDXIAMUserModel answer(InvocationOnMock invocation) throws Throwable {
                        OpenCDXIAMUserModel argument = invocation.getArgument(0);
                        if (argument.getId() == null) {
                            argument.setId(ObjectId.get());
                        }
                        return argument;
                    }
                });
        this.openCDXIAMUserService = new OpenCDXIAMUserServiceImpl(
                this.objectMapper, this.openCDXAuditService, this.openCDXIAMUserRepository);
        this.openCDXIAMUserGrpcController = new OpenCDXIAMUserGrpcController(this.openCDXIAMUserService);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(this.openCDXIAMUserRepository);
    }
    @Test
    void signUp() {
        StreamObserver<SignUpResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.signUp(SignUpRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(SignUpResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
    @Test
    void listIamUsers() {
        StreamObserver<ListIamUsersResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.listIamUsers(ListIamUsersRequest.getDefaultInstance(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ListIamUsersResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void getIamUser() {
        StreamObserver<GetIamUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.getIamUser(GetIamUserRequest.newBuilder().setId(ObjectId.get().toHexString()).build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(GetIamUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void updateIamUser() {
        StreamObserver<UpdateIamUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.updateIamUser(UpdateIamUserRequest.newBuilder().setIamUser(IamUser.newBuilder().setId(ObjectId.get().toHexString()).build()).build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UpdateIamUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void changePassword() {
        StreamObserver<ChangePasswordResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.changePassword(ChangePasswordRequest.newBuilder().setId(ObjectId.get().toHexString()).build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(ChangePasswordResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void deleteIamUser() {
        StreamObserver<DeleteIamUserResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.deleteIamUser(DeleteIamUserRequest.newBuilder().setId(ObjectId.get().toHexString()).build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(DeleteIamUserResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }

    @Test
    void userExists() {
        StreamObserver<UserExistsResponse> responseObserver = Mockito.mock(StreamObserver.class);
        this.openCDXIAMUserGrpcController.userExists(UserExistsRequest.newBuilder().setId(ObjectId.get().toHexString()).build(), responseObserver);

        Mockito.verify(responseObserver, Mockito.times(1)).onNext(Mockito.any(UserExistsResponse.class));
        Mockito.verify(responseObserver, Mockito.times(1)).onCompleted();
    }
}