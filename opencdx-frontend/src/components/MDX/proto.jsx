import React from 'react';
// import exampleHTML from './doc/javadoc/index.html';
// import exampleHTML from './example.html';
// import './nats.css';
function Proto () {

  
    // useEffect(() => {
    //     const fetchHTMLFile = async () => {
    //       try {
    //         const response = await fetch('doc/javadoc/index.html');
    //         const data = await response.text();
    //         setHtmlContent(data);
    //       } catch (error) {
    //         console.error('Error loading HTML file:', error);
    //       }
    //     };
    
    //     fetchHTMLFile();
    //   }, []);
   
   
    
    return (
        <div>
            <h1 id="title">Protocol Documentation</h1>

<h2>Table of Contents</h2>

<div id="toc-container">
  <ul id="toc">
    
      
      <li>
        <a href="#anf_statement.proto">anf_statement.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.ANFStatement"><span className="badge">M</span>ANFStatement</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.AssociatedStatement"><span className="badge">M</span>AssociatedStatement</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.CreatePerformanceCircumstanceRequest"><span className="badge">M</span>CreatePerformanceCircumstanceRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.CreatePerformanceCircumstanceResponse"><span className="badge">M</span>CreatePerformanceCircumstanceResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.CreateRequestCircumstanceRequest"><span className="badge">M</span>CreateRequestCircumstanceRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.CreateRequestCircumstanceResponse"><span className="badge">M</span>CreateRequestCircumstanceResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Identifier"><span className="badge">M</span>Identifier</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.LogicalExpression"><span className="badge">M</span>LogicalExpression</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Measure"><span className="badge">M</span>Measure</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.NarrativeCircumstance"><span className="badge">M</span>NarrativeCircumstance</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Participant"><span className="badge">M</span>Participant</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.PerformanceCircumstance"><span className="badge">M</span>PerformanceCircumstance</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Practitioner"><span className="badge">M</span>Practitioner</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Reference"><span className="badge">M</span>Reference</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Repetition"><span className="badge">M</span>Repetition</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.RequestCircumstance"><span className="badge">M</span>RequestCircumstance</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.Timing"><span className="badge">M</span>Timing</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.HealthRisk"><span className="badge">E</span>HealthRisk</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.NormalRange"><span className="badge">E</span>NormalRange</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.ResultStateStatus"><span className="badge">E</span>ResultStateStatus</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.anf.ANFService"><span className="badge">S</span>ANFService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#audit.proto">audit.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.Actor"><span className="badge">M</span>Actor</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AuditEntity"><span className="badge">M</span>AuditEntity</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AuditEvent"><span className="badge">M</span>AuditEvent</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AuditSource"><span className="badge">M</span>AuditSource</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AuditStatus"><span className="badge">M</span>AuditStatus</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.DataObject"><span className="badge">M</span>DataObject</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AgentType"><span className="badge">E</span>AgentType</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AuditEventType"><span className="badge">E</span>AuditEventType</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.SensitivityLevel"><span className="badge">E</span>SensitivityLevel</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.audit.AuditService"><span className="badge">S</span>AuditService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#communications.proto">communications.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.Attachment"><span className="badge">M</span>Attachment</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.CommunicationAuditRecord"><span className="badge">M</span>CommunicationAuditRecord</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.EmailTemplate"><span className="badge">M</span>EmailTemplate</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.EmailTemplateListRequest"><span className="badge">M</span>EmailTemplateListRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.EmailTemplateListResponse"><span className="badge">M</span>EmailTemplateListResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.Notification"><span className="badge">M</span>Notification</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.Notification.CustomDataEntry"><span className="badge">M</span>Notification.CustomDataEntry</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.Notification.VariablesEntry"><span className="badge">M</span>Notification.VariablesEntry</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.NotificationEvent"><span className="badge">M</span>NotificationEvent</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.NotificationEventListRequest"><span className="badge">M</span>NotificationEventListRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.NotificationEventListResponse"><span className="badge">M</span>NotificationEventListResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.SMSTemplate"><span className="badge">M</span>SMSTemplate</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.SMSTemplateListRequest"><span className="badge">M</span>SMSTemplateListRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.SMSTemplateListResponse"><span className="badge">M</span>SMSTemplateListResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.SuccessResponse"><span className="badge">M</span>SuccessResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.TemplateRequest"><span className="badge">M</span>TemplateRequest</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.NotificationPriority"><span className="badge">E</span>NotificationPriority</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.NotificationStatus"><span className="badge">E</span>NotificationStatus</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.TemplateType"><span className="badge">E</span>TemplateType</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.communication.CommunicationService"><span className="badge">S</span>CommunicationService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#connected_test.proto">connected_test.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.BasicInfo"><span className="badge">M</span>BasicInfo</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.ConnectedTest"><span className="badge">M</span>ConnectedTest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.ConnectedTestListByNHIDRequest"><span className="badge">M</span>ConnectedTestListByNHIDRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.ConnectedTestListByNHIDResponse"><span className="badge">M</span>ConnectedTestListByNHIDResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.ConnectedTestListRequest"><span className="badge">M</span>ConnectedTestListRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.ConnectedTestListResponse"><span className="badge">M</span>ConnectedTestListResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.Metadata"><span className="badge">M</span>Metadata</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.OrderInfo"><span className="badge">M</span>OrderInfo</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.OrderableTest"><span className="badge">M</span>OrderableTest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.OrderableTestResult"><span className="badge">M</span>OrderableTestResult</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.PaymentDetails"><span className="badge">M</span>PaymentDetails</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.ProviderInfo"><span className="badge">M</span>ProviderInfo</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.StatusMessageAction"><span className="badge">M</span>StatusMessageAction</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestDetails"><span className="badge">M</span>TestDetails</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestIdRequest"><span className="badge">M</span>TestIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestKitMetadata"><span className="badge">M</span>TestKitMetadata</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestNotes"><span className="badge">M</span>TestNotes</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestSubmissionResponse"><span className="badge">M</span>TestSubmissionResponse</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.LabTestType"><span className="badge">E</span>LabTestType</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.SpecimenType"><span className="badge">E</span>SpecimenType</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestclassNameification"><span className="badge">E</span>TestclassNameification</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.TestFormat"><span className="badge">E</span>TestFormat</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.connected.HealthcareService"><span className="badge">S</span>HealthcareService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#helloworld.proto">helloworld.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.helloworld.HelloReply"><span className="badge">M</span>HelloReply</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.helloworld.HelloRequest"><span className="badge">M</span>HelloRequest</a>
            </li>
          
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.helloworld.Greeter"><span className="badge">S</span>Greeter</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#iam_organization.proto">iam_organization.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.ContactInfo"><span className="badge">M</span>ContactInfo</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.CreateOrganizationRequest"><span className="badge">M</span>CreateOrganizationRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.CreateOrganizationResponse"><span className="badge">M</span>CreateOrganizationResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.CreateWorkspaceRequest"><span className="badge">M</span>CreateWorkspaceRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.CreateWorkspaceResponse"><span className="badge">M</span>CreateWorkspaceResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.Department"><span className="badge">M</span>Department</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.Employee"><span className="badge">M</span>Employee</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.Empty"><span className="badge">M</span>Empty</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdRequest"><span className="badge">M</span>GetOrganizationDetailsByIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdResponse"><span className="badge">M</span>GetOrganizationDetailsByIdResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdRequest"><span className="badge">M</span>GetWorkspaceDetailsByIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdResponse"><span className="badge">M</span>GetWorkspaceDetailsByIdResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.ListOrganizationsResponse"><span className="badge">M</span>ListOrganizationsResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.ListWorkspacesResponse"><span className="badge">M</span>ListWorkspacesResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.Organization"><span className="badge">M</span>Organization</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.UpdateOrganizationRequest"><span className="badge">M</span>UpdateOrganizationRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.UpdateOrganizationResponse"><span className="badge">M</span>UpdateOrganizationResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.UpdateWorkspaceRequest"><span className="badge">M</span>UpdateWorkspaceRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.UpdateWorkspaceResponse"><span className="badge">M</span>UpdateWorkspaceResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.Workspace"><span className="badge">M</span>Workspace</a>
            </li>
          
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.OrganizationService"><span className="badge">S</span>OrganizationService</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.organization.WorkspaceService"><span className="badge">S</span>WorkspaceService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#iam_profile.proto">iam_profile.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Address"><span className="badge">M</span>Address</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.ContactInfo"><span className="badge">M</span>ContactInfo</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.DateOfBirth"><span className="badge">M</span>DateOfBirth</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.DeleteUserProfileRequest"><span className="badge">M</span>DeleteUserProfileRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.DeleteUserProfileResponse"><span className="badge">M</span>DeleteUserProfileResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Demographics"><span className="badge">M</span>Demographics</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Education"><span className="badge">M</span>Education</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.EducationEntry"><span className="badge">M</span>EducationEntry</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.EmergencyContact"><span className="badge">M</span>EmergencyContact</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.EmployeeIdentity"><span className="badge">M</span>EmployeeIdentity</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.FullName"><span className="badge">M</span>FullName</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.KnownAllergy"><span className="badge">M</span>KnownAllergy</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Medication"><span className="badge">M</span>Medication</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Pharmacy"><span className="badge">M</span>Pharmacy</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.PhoneNumber"><span className="badge">M</span>PhoneNumber</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.PlaceOfBirth"><span className="badge">M</span>PlaceOfBirth</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Preferences"><span className="badge">M</span>Preferences</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.SystemSettings"><span className="badge">M</span>SystemSettings</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.UpdateUserProfileRequest"><span className="badge">M</span>UpdateUserProfileRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.UpdateUserProfileResponse"><span className="badge">M</span>UpdateUserProfileResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.UserProfile"><span className="badge">M</span>UserProfile</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.UserProfileRequest"><span className="badge">M</span>UserProfileRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.UserProfileResponse"><span className="badge">M</span>UserProfileResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Vaccine"><span className="badge">M</span>Vaccine</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.Gender"><span className="badge">E</span>Gender</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.PhoneType"><span className="badge">E</span>PhoneType</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.profile.UserProfileService"><span className="badge">S</span>UserProfileService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#iam_user.proto">iam_user.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.ChangePasswordRequest"><span className="badge">M</span>ChangePasswordRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.ChangePasswordResponse"><span className="badge">M</span>ChangePasswordResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.CurrentUserRequest"><span className="badge">M</span>CurrentUserRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.CurrentUserResponse"><span className="badge">M</span>CurrentUserResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.DeleteIamUserRequest"><span className="badge">M</span>DeleteIamUserRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.DeleteIamUserResponse"><span className="badge">M</span>DeleteIamUserResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.GetIamUserRequest"><span className="badge">M</span>GetIamUserRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.GetIamUserResponse"><span className="badge">M</span>GetIamUserResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.IamUser"><span className="badge">M</span>IamUser</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.ListIamUsersRequest"><span className="badge">M</span>ListIamUsersRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.ListIamUsersResponse"><span className="badge">M</span>ListIamUsersResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.LoginRequest"><span className="badge">M</span>LoginRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.LoginResponse"><span className="badge">M</span>LoginResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.SignUpRequest"><span className="badge">M</span>SignUpRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.SignUpResponse"><span className="badge">M</span>SignUpResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.UpdateIamUserRequest"><span className="badge">M</span>UpdateIamUserRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.UpdateIamUserResponse"><span className="badge">M</span>UpdateIamUserResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.UserExistsRequest"><span className="badge">M</span>UserExistsRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.UserExistsResponse"><span className="badge">M</span>UserExistsResponse</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.IamUserStatus"><span className="badge">E</span>IamUserStatus</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.IamUserType"><span className="badge">E</span>IamUserType</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.iam.IamUserService"><span className="badge">S</span>IamUserService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#inventory.proto">inventory.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.Address"><span className="badge">M</span>Address</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.Country"><span className="badge">M</span>Country</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.CountryIdRequest"><span className="badge">M</span>CountryIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.DeleteResponse"><span className="badge">M</span>DeleteResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.Device"><span className="badge">M</span>Device</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.DeviceIdRequest"><span className="badge">M</span>DeviceIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.Manufacturer"><span className="badge">M</span>Manufacturer</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.ManufacturerIdRequest"><span className="badge">M</span>ManufacturerIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.TestCase"><span className="badge">M</span>TestCase</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.TestCaseIdRequest"><span className="badge">M</span>TestCaseIdRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.Vendor"><span className="badge">M</span>Vendor</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.VendorIdRequest"><span className="badge">M</span>VendorIdRequest</a>
            </li>
          
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.CountryService"><span className="badge">S</span>CountryService</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.DeviceService"><span className="badge">S</span>DeviceService</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.ManufacturerService"><span className="badge">S</span>ManufacturerService</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.TestCaseService"><span className="badge">S</span>TestCaseService</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.inventory.VendorService"><span className="badge">S</span>VendorService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#media.proto">media.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.CreateMediaRequest"><span className="badge">M</span>CreateMediaRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.CreateMediaResponse"><span className="badge">M</span>CreateMediaResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.DeleteMediaRequest"><span className="badge">M</span>DeleteMediaRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.DeleteMediaResponse"><span className="badge">M</span>DeleteMediaResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.GetMediaRequest"><span className="badge">M</span>GetMediaRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.GetMediaResponse"><span className="badge">M</span>GetMediaResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.ListMediaRequest"><span className="badge">M</span>ListMediaRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.ListMediaResponse"><span className="badge">M</span>ListMediaResponse</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.Media"><span className="badge">M</span>Media</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaFilter"><span className="badge">M</span>MediaFilter</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.UpdateMediaRequest"><span className="badge">M</span>UpdateMediaRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.UpdateMediaResponse"><span className="badge">M</span>UpdateMediaResponse</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaStatus"><span className="badge">E</span>MediaStatus</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaType"><span className="badge">E</span>MediaType</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaService"><span className="badge">S</span>MediaService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#media_preprocessor.proto">media_preprocessor.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaPreprocessor"><span className="badge">M</span>MediaPreprocessor</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.PreprocessMediaRequest"><span className="badge">M</span>PreprocessMediaRequest</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.PreprocessMediaResponse"><span className="badge">M</span>PreprocessMediaResponse</a>
            </li>
          
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaPreprocessorCommands"><span className="badge">E</span>MediaPreprocessorCommands</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaPreprocessorEvents"><span className="badge">E</span>MediaPreprocessorEvents</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaPreprocessorStatus"><span className="badge">E</span>MediaPreprocessorStatus</a>
            </li>
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.media.MediaPreprocessorService"><span className="badge">S</span>MediaPreprocessorService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#routine.proto">routine.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution"><span className="badge">M</span>ClinicalProtocolExecution</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.DeliveryTracking"><span className="badge">M</span>DeliveryTracking</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.Diagnosis"><span className="badge">M</span>Diagnosis</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.LabOrder"><span className="badge">M</span>LabOrder</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.LabResult"><span className="badge">M</span>LabResult</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.Medication"><span className="badge">M</span>Medication</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.Routine"><span className="badge">M</span>Routine</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis"><span className="badge">M</span>SuspectedDiagnosis</a>
            </li>
          
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.routine.RoutineSystemService"><span className="badge">S</span>RoutineSystemService</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#tinkar.proto">tinkar.proto</a>
        <ul>
          
            <li>
              <a href="#cdx.opencdx.grpc.tinkar.TinkarReply"><span className="badge">M</span>TinkarReply</a>
            </li>
          
            <li>
              <a href="#cdx.opencdx.grpc.tinkar.TinkarRequest"><span className="badge">M</span>TinkarRequest</a>
            </li>
          
          
          
          
            <li>
              <a href="#cdx.opencdx.grpc.tinkar.Tinkar"><span className="badge">S</span>Tinkar</a>
            </li>
          
        </ul>
      </li>
    
      
      <li>
        <a href="#unit_test.proto">unit_test.proto</a>
        <ul>
          
            <li>
              <a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestRequest"><span className="badge">M</span>UnitTestRequest</a>
            </li>
          
            <li>
              <a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestResponse"><span className="badge">M</span>UnitTestResponse</a>
            </li>
          
          
          
          
            <li>
              <a href="#health.safe.api.opencdx.grpc.unit.test.UnitTest"><span className="badge">S</span>UnitTest</a>
            </li>
          
        </ul>
      </li>
    
    <li><a href="#scalar-value-types">Scalar Value Types</a></li>
  </ul>
</div>


  
  <div className="file-heading">
    <h2 id="anf_statement.proto">anf_statement.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.anf.ANFStatement">ANFStatement</h3>
    <p>Main representation of an ANFStatement.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#cdx.opencdx.grpc.anf.Identifier">Identifier</a></td>
              <td></td>
              <td><p>The unique ID of the ANFStatement </p></td>
            </tr>
          
            <tr>
              <td>time</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Time of the statement
Rule: Timing - past, present, or future
For a Performance of Action, the Timing can represent a time in the past or a current time.
For a Request of Action, the Timing will always represent a future time. </p></td>
            </tr>
          
            <tr>
              <td>subject_of_record</td>
              <td><a href="#cdx.opencdx.grpc.anf.Participant">Participant</a></td>
              <td></td>
              <td><p>Subject of the record, usually the patient </p></td>
            </tr>
          
            <tr>
              <td>author</td>
              <td><a href="#cdx.opencdx.grpc.anf.Practitioner">Practitioner</a></td>
              <td>repeated</td>
              <td><p>Authors of the statement </p></td>
            </tr>
          
            <tr>
              <td>subject_of_information</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Subject or topic of the information </p></td>
            </tr>
          
            <tr>
              <td>associated_statement</td>
              <td><a href="#cdx.opencdx.grpc.anf.AssociatedStatement">AssociatedStatement</a></td>
              <td>repeated</td>
              <td><p>Associated statements or conditions </p></td>
            </tr>
          
            <tr>
              <td>topic</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Main topic of the statement </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Type of the statement, e.g., &#34;Request&#34;, &#34;Performance&#34; </p></td>
            </tr>
          
            <tr>
              <td>request_circumstance</td>
              <td><a href="#cdx.opencdx.grpc.anf.RequestCircumstance">RequestCircumstance</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
            <tr>
              <td>performance_circumstance</td>
              <td><a href="#cdx.opencdx.grpc.anf.PerformanceCircumstance">PerformanceCircumstance</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
            <tr>
              <td>narrative_circumstance</td>
              <td><a href="#cdx.opencdx.grpc.anf.NarrativeCircumstance">NarrativeCircumstance</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.AssociatedStatement">AssociatedStatement</h3>
    <p>Representation of associated statements like preconditions or interpretations.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description or content of the associated statement </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.CreatePerformanceCircumstanceRequest">CreatePerformanceCircumstanceRequest</h3>
    <p>Request message for creating a new PerformanceCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>performance_circumstance</td>
              <td><a href="#cdx.opencdx.grpc.anf.PerformanceCircumstance">PerformanceCircumstance</a></td>
              <td></td>
              <td><p>The PerformanceCircumstance to be created </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.CreatePerformanceCircumstanceResponse">CreatePerformanceCircumstanceResponse</h3>
    <p>Response message for creating a new PerformanceCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Status of the PerformanceCircumstance creation </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.CreateRequestCircumstanceRequest">CreateRequestCircumstanceRequest</h3>
    <p>Request message for creating a new RequestCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>request_circumstance</td>
              <td><a href="#cdx.opencdx.grpc.anf.RequestCircumstance">RequestCircumstance</a></td>
              <td></td>
              <td><p>The RequestCircumstance to be created </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.CreateRequestCircumstanceResponse">CreateRequestCircumstanceResponse</h3>
    <p>Response message for creating a new RequestCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Status of the RequestCircumstance creation </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Identifier">Identifier</h3>
    <p>Unique identifier for an ANFStatement.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The unique ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</h3>
    <p>Representation of a logical expression.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>expression</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The logical expression as a string </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Measure">Measure</h3>
    <p>Representation of a measurable element, such as time or test results.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>upper_bound</td>
              <td><a href="#float">float</a></td>
              <td></td>
              <td><p>The upper bound of the measure </p></td>
            </tr>
          
            <tr>
              <td>lower_bound</td>
              <td><a href="#float">float</a></td>
              <td></td>
              <td><p>The lower bound of the measure </p></td>
            </tr>
          
            <tr>
              <td>include_upper_bound</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates if the upper bound is inclusive </p></td>
            </tr>
          
            <tr>
              <td>include_lower_bound</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates if the lower bound is inclusive </p></td>
            </tr>
          
            <tr>
              <td>semantic</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>The semantic meaning of the measure, e.g., &#34;Countable quantity&#34; </p></td>
            </tr>
          
            <tr>
              <td>resolution</td>
              <td><a href="#float">float</a></td>
              <td></td>
              <td><p>The resolution or precision of the measure, optional </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.NarrativeCircumstance">NarrativeCircumstance</h3>
    <p>Representation of a NarrativeCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>text</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Textual description or narrative </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Participant">Participant</h3>
    <p>Representation of a participant, typically a patient.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The unique ID of the participant </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.PerformanceCircumstance">PerformanceCircumstance</h3>
    <p>Representation of a PerformanceCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>status</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Current status of the performance </p></td>
            </tr>
          
            <tr>
              <td>result</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Result of the performance </p></td>
            </tr>
          
            <tr>
              <td>health_risk</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Health risk level, optional </p></td>
            </tr>
          
            <tr>
              <td>normal_range</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Normal range for the result, optional </p></td>
            </tr>
          
            <tr>
              <td>participant</td>
              <td><a href="#cdx.opencdx.grpc.anf.Participant">Participant</a></td>
              <td>repeated</td>
              <td><p>Participants in the performance </p></td>
            </tr>
          
            <tr>
              <td>timing</td>
              <td><a href="#cdx.opencdx.grpc.anf.Timing">Timing</a></td>
              <td></td>
              <td><p>Timing information, represents a past or current time </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Practitioner">Practitioner</h3>
    <p>Representation of a practitioner or authoring entity.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The unique ID of the practitioner </p></td>
            </tr>
          
            <tr>
              <td>practitioner</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Reference to the participating practitioner </p></td>
            </tr>
          
            <tr>
              <td>code</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Roles the practitioner is authorized to perform </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Reference">Reference</h3>
    <p>Representation of a reference to other entities like patient records or health practitioners.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The unique ID of the reference </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Type of the reference, e.g., &#34;Patient&#34;, &#34;Practitioner&#34; </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Repetition">Repetition</h3>
    <p>Representation of repetition details for a RequestCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>period_start</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>When the repetition should start </p></td>
            </tr>
          
            <tr>
              <td>period_duration</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Duration of the repetition period </p></td>
            </tr>
          
            <tr>
              <td>event_separation</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Time between events </p></td>
            </tr>
          
            <tr>
              <td>event_duration</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Duration of each event, optional </p></td>
            </tr>
          
            <tr>
              <td>event_frequency</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Frequency of the events </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.RequestCircumstance">RequestCircumstance</h3>
    <p>Representation of a RequestCircumstance.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>conditional_trigger</td>
              <td><a href="#cdx.opencdx.grpc.anf.AssociatedStatement">AssociatedStatement</a></td>
              <td>repeated</td>
              <td><p>Conditions that trigger the request </p></td>
            </tr>
          
            <tr>
              <td>requested_participant</td>
              <td><a href="#cdx.opencdx.grpc.anf.Reference">Reference</a></td>
              <td>repeated</td>
              <td><p>Participants in the request </p></td>
            </tr>
          
            <tr>
              <td>priority</td>
              <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
              <td></td>
              <td><p>Priority level of the request </p></td>
            </tr>
          
            <tr>
              <td>requested_result</td>
              <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
              <td></td>
              <td><p>Expected result of the request </p></td>
            </tr>
          
            <tr>
              <td>repetition</td>
              <td><a href="#cdx.opencdx.grpc.anf.Repetition">Repetition</a></td>
              <td></td>
              <td><p>Details about the repetition, optional </p></td>
            </tr>
          
            <tr>
              <td>timing</td>
              <td><a href="#cdx.opencdx.grpc.anf.Timing">Timing</a></td>
              <td></td>
              <td><p>Timing information, always represents a future time </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.anf.Timing">Timing</h3>
    <p>Representation of timing information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>time</td>
              <td><a href="#int64">int64</a></td>
              <td></td>
              <td><p>The time as a Unix timestamp </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description of the timing, e.g., &#34;past&#34;, &#34;present&#34;, &#34;future&#34; </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.anf.HealthRisk">HealthRisk</h3>
    <p>Enum to represent different levels of health risk.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>HEALTH_RISK_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Default, unspecified value</p></td>
          </tr>
        
          <tr>
            <td>HEALTH_RISK_LOW</td>
            <td>1</td>
            <td><p>Low level of health risk</p></td>
          </tr>
        
          <tr>
            <td>HEALTH_RISK_NORMAL</td>
            <td>2</td>
            <td><p>Normal level of health risk</p></td>
          </tr>
        
          <tr>
            <td>HEALTH_RISK_HIGH</td>
            <td>3</td>
            <td><p>High level of health risk</p></td>
          </tr>
        
          <tr>
            <td>HEALTH_RISK_CRITICAL</td>
            <td>4</td>
            <td><p>Critical level of health risk</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.anf.NormalRange">NormalRange</h3>
    <p>Enum to represent the normal range of a result.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>NORMAL_RANGE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Default, unspecified value</p></td>
          </tr>
        
          <tr>
            <td>NORMAL_RANGE_LOW</td>
            <td>1</td>
            <td><p>The result is low</p></td>
          </tr>
        
          <tr>
            <td>NORMAL_RANGE_NORMAL</td>
            <td>2</td>
            <td><p>The result is within the normal range</p></td>
          </tr>
        
          <tr>
            <td>NORMAL_RANGE_HIGH</td>
            <td>3</td>
            <td><p>The result is high</p></td>
          </tr>
        
          <tr>
            <td>NORMAL_RANGE_CRITICAL</td>
            <td>4</td>
            <td><p>The result is critically high or low</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.anf.ResultStateStatus">ResultStateStatus</h3>
    <p>Enum to represent the different states a result can be in.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>RESULT_STATE_STATUS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Default, unspecified value</p></td>
          </tr>
        
          <tr>
            <td>RESULT_STATE_STATUS_ON_HOLD</td>
            <td>1</td>
            <td><p>The action is currently on hold</p></td>
          </tr>
        
          <tr>
            <td>RESULT_STATE_STATUS_NEEDED</td>
            <td>2</td>
            <td><p>The action is needed but not yet started</p></td>
          </tr>
        
          <tr>
            <td>RESULT_STATE_STATUS_REJECTED</td>
            <td>3</td>
            <td><p>The action was rejected</p></td>
          </tr>
        
          <tr>
            <td>RESULT_STATE_STATUS_COMPLETED</td>
            <td>4</td>
            <td><p>The action was completed</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.anf.ANFService">ANFService</h3>
    <p>Service definition for ANF operations.</p><p>Provides CRUD operations for ANFStatement and circumstances.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>CreateANFStatement</td>
            <td><a href="#cdx.opencdx.grpc.anf.ANFStatement">ANFStatement</a></td>
            <td><a href="#cdx.opencdx.grpc.anf.Identifier">Identifier</a></td>
            <td><p>Create a new ANFStatement</p></td>
          </tr>
        
          <tr>
            <td>GetANFStatement</td>
            <td><a href="#cdx.opencdx.grpc.anf.Identifier">Identifier</a></td>
            <td><a href="#cdx.opencdx.grpc.anf.ANFStatement">ANFStatement</a></td>
            <td><p>Retrieve an existing ANFStatement by its Identifier</p></td>
          </tr>
        
          <tr>
            <td>UpdateANFStatement</td>
            <td><a href="#cdx.opencdx.grpc.anf.ANFStatement">ANFStatement</a></td>
            <td><a href="#cdx.opencdx.grpc.anf.Identifier">Identifier</a></td>
            <td><p>Update an existing ANFStatement</p></td>
          </tr>
        
          <tr>
            <td>DeleteANFStatement</td>
            <td><a href="#cdx.opencdx.grpc.anf.Identifier">Identifier</a></td>
            <td><a href="#cdx.opencdx.grpc.anf.Identifier">Identifier</a></td>
            <td><p>Delete an ANFStatement by its Identifier</p></td>
          </tr>
        
          <tr>
            <td>CreatePerformanceCircumstance</td>
            <td><a href="#cdx.opencdx.grpc.anf.CreatePerformanceCircumstanceRequest">CreatePerformanceCircumstanceRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.anf.CreatePerformanceCircumstanceResponse">CreatePerformanceCircumstanceResponse</a></td>
            <td><p>Create a new PerformanceCircumstance</p></td>
          </tr>
        
          <tr>
            <td>CreateRequestCircumstance</td>
            <td><a href="#cdx.opencdx.grpc.anf.CreateRequestCircumstanceRequest">CreateRequestCircumstanceRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.anf.CreateRequestCircumstanceResponse">CreateRequestCircumstanceResponse</a></td>
            <td><p>Create a new RequestCircumstance</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="audit.proto">audit.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.audit.Actor">Actor</h3>
    <p>Identifies the identity that initiated this audit.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>identity</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>UUID identifying the identity. </p></td>
            </tr>
          
            <tr>
              <td>role</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The role the actor had when initiated. </p></td>
            </tr>
          
            <tr>
              <td>network_address</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Network address from actor when initiated. </p></td>
            </tr>
          
            <tr>
              <td>agent_type</td>
              <td><a href="#cdx.opencdx.grpc.audit.AgentType">AgentType</a></td>
              <td></td>
              <td><p>Indicates if the actor is a Human, System or another entity. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.audit.AuditEntity">AuditEntity</h3>
    <p>Identifies whose information is being acted on.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>patient_identifier</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>National Health Identifier </p></td>
            </tr>
          
            <tr>
              <td>user_identifier</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>UUID for the patient&#39;s user ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.audit.AuditEvent">AuditEvent</h3>
    <p>Audit Event information to record.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>created</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp when this event occurred. </p></td>
            </tr>
          
            <tr>
              <td>event_type</td>
              <td><a href="#cdx.opencdx.grpc.audit.AuditEventType">AuditEventType</a></td>
              <td></td>
              <td><p>Identifies the type of event. </p></td>
            </tr>
          
            <tr>
              <td>actor</td>
              <td><a href="#cdx.opencdx.grpc.audit.Actor">Actor</a></td>
              <td></td>
              <td><p>Identifies who is causing the action </p></td>
            </tr>
          
            <tr>
              <td>data_object</td>
              <td><a href="#cdx.opencdx.grpc.audit.DataObject">DataObject</a></td>
              <td></td>
              <td><p>Data that is being acted on. </p></td>
            </tr>
          
            <tr>
              <td>purpose_of_use</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description of use of this data. </p></td>
            </tr>
          
            <tr>
              <td>audit_source</td>
              <td><a href="#cdx.opencdx.grpc.audit.AuditSource">AuditSource</a></td>
              <td></td>
              <td><p>Where did the event originate </p></td>
            </tr>
          
            <tr>
              <td>audit_entity</td>
              <td><a href="#cdx.opencdx.grpc.audit.AuditEntity">AuditEntity</a></td>
              <td></td>
              <td><p>Identifies whose data is being acted on. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.audit.AuditSource">AuditSource</h3>
    <p>Source of the Audit event.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>system_info</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>System Name </p></td>
            </tr>
          
            <tr>
              <td>configuration</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Relevant configuration information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.audit.AuditStatus">AuditStatus</h3>
    <p>Indicates the status of an operation</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>success</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.audit.DataObject">DataObject</h3>
    <p>Contains the relevant data for this audit.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>resource</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
            <tr>
              <td>data</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Data block recorded for Audit System </p></td>
            </tr>
          
            <tr>
              <td>sensitivity</td>
              <td><a href="#cdx.opencdx.grpc.audit.SensitivityLevel">SensitivityLevel</a></td>
              <td></td>
              <td><p>Indicates the sensitivity level of this data. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.audit.AgentType">AgentType</h3>
    <p>Indicates if the actor is a Human, System or another entity.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>AGENT_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified agent type</p></td>
          </tr>
        
          <tr>
            <td>AGENT_TYPE_HUMAN_USER</td>
            <td>1</td>
            <td><p>The actor is a human.</p></td>
          </tr>
        
          <tr>
            <td>AGENT_TYPE_SYSTEM</td>
            <td>2</td>
            <td><p>The actor is a system.</p></td>
          </tr>
        
          <tr>
            <td>AGENT_TYPE_OTHER_ENTITY</td>
            <td>3</td>
            <td><p>The actor is a separate entity.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.audit.AuditEventType">AuditEventType</h3>
    <p>Indicates the type of Audit event that is being recorded.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified audit event type</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_LOGIN_SUCCEEDED</td>
            <td>1</td>
            <td><p>User successfully logged in.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_LOG_OUT</td>
            <td>2</td>
            <td><p>User logged out from system.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_LOGIN_FAIL</td>
            <td>3</td>
            <td><p>User failed to login</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_ACCESS_CHANGE</td>
            <td>4</td>
            <td><p>User access changed</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PASSWORD_CHANGE</td>
            <td>5</td>
            <td><p>User Password Change</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_SYSTEM_LOGIN_SUCCEEDED</td>
            <td>6</td>
            <td><p>System successfully logged in.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_SYSTEM_LOG_OUT</td>
            <td>7</td>
            <td><p>System logged out.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_SYSTEM_LOGIN_FAIL</td>
            <td>8</td>
            <td><p>System failed to login.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PII_ACCESSED</td>
            <td>9</td>
            <td><p>Personal Identifiable Information accessed.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PII_UPDATED</td>
            <td>10</td>
            <td><p>Personal Identifiable Information updated.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PII_CREATED</td>
            <td>11</td>
            <td><p>Personal Identifiable Information created.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PII_DELETED</td>
            <td>12</td>
            <td><p>Personal Identifiable Information deleted.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PHI_ACCESSED</td>
            <td>13</td>
            <td><p>Personal Health Information accessed.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PHI_UPDATED</td>
            <td>14</td>
            <td><p>Personal Health Information updated.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PHI_CREATED</td>
            <td>15</td>
            <td><p>Personal Health Information created.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_PHI_DELETED</td>
            <td>16</td>
            <td><p>Personal Health Information deleted.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_USER_COMMUNICATION</td>
            <td>17</td>
            <td><p>Configuration information changed.</p></td>
          </tr>
        
          <tr>
            <td>AUDIT_EVENT_TYPE_CONFIG_CHANGE</td>
            <td>18</td>
            <td><p>Configuration information changed.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.audit.SensitivityLevel">SensitivityLevel</h3>
    <p>Indicates the sensitivity level of the data contained</p><p>in this audit message.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>SENSITIVITY_LEVEL_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified sensitivity level</p></td>
          </tr>
        
          <tr>
            <td>SENSITIVITY_LEVEL_LOW</td>
            <td>1</td>
            <td><p>Information is not sensitive.</p></td>
          </tr>
        
          <tr>
            <td>SENSITIVITY_LEVEL_MEDIUM</td>
            <td>2</td>
            <td><p>Information could be used to identify an individual.</p></td>
          </tr>
        
          <tr>
            <td>SENSITIVITY_LEVEL_HIGH</td>
            <td>3</td>
            <td><p>Personal information is contained.</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.audit.AuditService">AuditService</h3>
    <p>Service calls to the Audit Service</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>Event</td>
            <td><a href="#cdx.opencdx.grpc.audit.AuditEvent">AuditEvent</a></td>
            <td><a href="#cdx.opencdx.grpc.audit.AuditStatus">AuditStatus</a></td>
            <td><p>Register an AuditEvent.</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="communications.proto">communications.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.communication.Attachment">Attachment</h3>
    <p>Defines the structure for attachments.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>filename</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Filename of the attachment. </p></td>
            </tr>
          
            <tr>
              <td>data</td>
              <td><a href="#bytes">bytes</a></td>
              <td></td>
              <td><p>Binary data of the attachment. </p></td>
            </tr>
          
            <tr>
              <td>mime_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>MIME type of the attachment. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.CommunicationAuditRecord">CommunicationAuditRecord</h3>
    <p>Audit record for communication messages se</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>notification</td>
              <td><a href="#cdx.opencdx.grpc.communication.Notification">Notification</a></td>
              <td></td>
              <td><p>The notification to trigger the communication </p></td>
            </tr>
          
            <tr>
              <td>emailContent</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Content of the email </p></td>
            </tr>
          
            <tr>
              <td>smsContent</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Content of the SMS Notification. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</h3>
    <p>Defines the structure for representing email templates.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>template_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the email template. </p></td>
            </tr>
          
            <tr>
              <td>subject</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Subject line of the email template. </p></td>
            </tr>
          
            <tr>
              <td>content</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Content of the email template. </p></td>
            </tr>
          
            <tr>
              <td>variables</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Placeholder variables within the template. </p></td>
            </tr>
          
            <tr>
              <td>template_type</td>
              <td><a href="#cdx.opencdx.grpc.communication.TemplateType">TemplateType</a></td>
              <td></td>
              <td><p>Type of the email template. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.EmailTemplateListRequest">EmailTemplateListRequest</h3>
    <p>Request Message to list EmailTemplates</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.EmailTemplateListResponse">EmailTemplateListResponse</h3>
    <p>Response containing the requested list of EmailTemplates</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages. </p></td>
            </tr>
          
            <tr>
              <td>templates</td>
              <td><a href="#cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</a></td>
              <td>repeated</td>
              <td><p>List of EmailTemplates </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.Notification">Notification</h3>
    <p>Representation of a notification that can be sent through the communication service.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>queue_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the notification within the queue. </p></td>
            </tr>
          
            <tr>
              <td>event_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Event or trigger associated with this notification. </p></td>
            </tr>
          
            <tr>
              <td>sms_status</td>
              <td><a href="#cdx.opencdx.grpc.communication.NotificationStatus">NotificationStatus</a></td>
              <td>optional</td>
              <td><p>Status of the SMS notification (e.g., pending, sent, failed). </p></td>
            </tr>
          
            <tr>
              <td>email_status</td>
              <td><a href="#cdx.opencdx.grpc.communication.NotificationStatus">NotificationStatus</a></td>
              <td>optional</td>
              <td><p>Status of the EMAIL notification (e.g., pending, sent, failed). </p></td>
            </tr>
          
            <tr>
              <td>timestamp</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td>optional</td>
              <td><p>Timestamp when the notification was added to the queue. </p></td>
            </tr>
          
            <tr>
              <td>custom_data</td>
              <td><a href="#cdx.opencdx.grpc.communication.Notification.CustomDataEntry">Notification.CustomDataEntry</a></td>
              <td>repeated</td>
              <td><p>Store additional custom data associated with the notification. </p></td>
            </tr>
          
            <tr>
              <td>to_email</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Email attributes

Recipients&#39; email addresses. </p></td>
            </tr>
          
            <tr>
              <td>cc_email</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Carbon copy (Cc) recipients&#39; email addresses. </p></td>
            </tr>
          
            <tr>
              <td>bcc_email</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Blind carbon copy (Bcc) recipients&#39; email addresses. </p></td>
            </tr>
          
            <tr>
              <td>email_attachments</td>
              <td><a href="#cdx.opencdx.grpc.communication.Attachment">Attachment</a></td>
              <td>repeated</td>
              <td><p>Email attachments. </p></td>
            </tr>
          
            <tr>
              <td>to_phone_number</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>SMS attributes

Recipients&#39; phone numbers. </p></td>
            </tr>
          
            <tr>
              <td>variables</td>
              <td><a href="#cdx.opencdx.grpc.communication.Notification.VariablesEntry">Notification.VariablesEntry</a></td>
              <td>repeated</td>
              <td><p>Variables for replacement.

Variables or placeholders. </p></td>
            </tr>
          
            <tr>
              <td>recipients_id</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>List of the UUID&#39;s for each email recipient and for recipients phone numbers. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.Notification.CustomDataEntry">Notification.CustomDataEntry</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>key</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
            <tr>
              <td>value</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.Notification.VariablesEntry">Notification.VariablesEntry</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>key</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
            <tr>
              <td>value</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</h3>
    <p>Defines the structure for notification events.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>event_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the notification event. </p></td>
            </tr>
          
            <tr>
              <td>event_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Human-readable name of the event. </p></td>
            </tr>
          
            <tr>
              <td>event_description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Brief description of the event. </p></td>
            </tr>
          
            <tr>
              <td>email_template_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>ID of the associated email template. </p></td>
            </tr>
          
            <tr>
              <td>sms_template_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>ID of the associated SMS template. </p></td>
            </tr>
          
            <tr>
              <td>event_parameters</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Event-specific parameters. </p></td>
            </tr>
          
            <tr>
              <td>priority</td>
              <td><a href="#cdx.opencdx.grpc.communication.NotificationPriority">NotificationPriority</a></td>
              <td></td>
              <td><p>Priority of this notification event. </p></td>
            </tr>
          
            <tr>
              <td>sensitivity</td>
              <td><a href="#cdx.opencdx.grpc.audit.SensitivityLevel">cdx.opencdx.grpc.audit.SensitivityLevel</a></td>
              <td></td>
              <td><p>Indicates the Sensitivity level of the notification when sent. </p></td>
            </tr>
          
            <tr>
              <td>email_retry</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Number of attempts to try and send email. Defaults to 0 for infinite. </p></td>
            </tr>
          
            <tr>
              <td>sms_retry</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Number of attempts to try and send SMS. Defaults to 0 for infinite. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.NotificationEventListRequest">NotificationEventListRequest</h3>
    <p>Request Message to list NotificationEvents</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.NotificationEventListResponse">NotificationEventListResponse</h3>
    <p>Response containing the requested list of NotificationEvents</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages. </p></td>
            </tr>
          
            <tr>
              <td>templates</td>
              <td><a href="#cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</a></td>
              <td>repeated</td>
              <td><p>List of NotificationEvents </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</h3>
    <p>Defines the structure for representing SMS templates.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>template_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the SMS template. </p></td>
            </tr>
          
            <tr>
              <td>message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Content of the SMS template. </p></td>
            </tr>
          
            <tr>
              <td>variables</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Placeholder variables within the SMS template. </p></td>
            </tr>
          
            <tr>
              <td>template_type</td>
              <td><a href="#cdx.opencdx.grpc.communication.TemplateType">TemplateType</a></td>
              <td></td>
              <td><p>Type of the SMS template. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.SMSTemplateListRequest">SMSTemplateListRequest</h3>
    <p>Request Message to list SMSTemplates</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.SMSTemplateListResponse">SMSTemplateListResponse</h3>
    <p>Response containing the requested list of SMSTemplates</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages. </p></td>
            </tr>
          
            <tr>
              <td>templates</td>
              <td><a href="#cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</a></td>
              <td>repeated</td>
              <td><p>List of SMSTemplates </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.SuccessResponse">SuccessResponse</h3>
    <p>Indicates if the operation was successful.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>success</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</h3>
    <p>Defines the template identification for the operation</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>template_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.communication.NotificationPriority">NotificationPriority</h3>
    <p>Indicates the priority of this Notification</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>NOTIFICATION_PRIORITY_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Priority is not specified.</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_PRIORITY_LOW</td>
            <td>1</td>
            <td><p>Priority is low</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_PRIORITY_MEDIUM</td>
            <td>2</td>
            <td><p>Priority is Medium</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_PRIORITY_HIGH</td>
            <td>3</td>
            <td><p>Priority is High</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_PRIORITY_IMMEDIATE</td>
            <td>4</td>
            <td><p>Priority is Immediate</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.communication.NotificationStatus">NotificationStatus</h3>
    <p>Indicates the status of the Notification</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>NOTIFICATION_STATUS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified Notification status.</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_STATUS_PENDING</td>
            <td>1</td>
            <td><p>Unspecified Notification status.</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_STATUS_SENT</td>
            <td>2</td>
            <td><p>Unspecified Notification status.</p></td>
          </tr>
        
          <tr>
            <td>NOTIFICATION_STATUS_FAILED</td>
            <td>3</td>
            <td><p>Unspecified Notification status.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.communication.TemplateType">TemplateType</h3>
    <p>Enum for template types</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>TEMPLATE_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Template is unspecified.</p></td>
          </tr>
        
          <tr>
            <td>TEMPLATE_TYPE_NOTIFICATION</td>
            <td>1</td>
            <td><p>Template is a Notification</p></td>
          </tr>
        
          <tr>
            <td>TEMPLATE_TYPE_WELCOME</td>
            <td>2</td>
            <td><p>Template is a welcome</p></td>
          </tr>
        
          <tr>
            <td>TEMPLATE_TYPE_NEWSLETTER</td>
            <td>3</td>
            <td><p>Template is a newsletter.</p></td>
          </tr>
        
          <tr>
            <td>TEMPLATE_TYPE_ALERT</td>
            <td>4</td>
            <td><p>Template is an alert.</p></td>
          </tr>
        
          <tr>
            <td>TEMPLATE_TYPE_REMINDER</td>
            <td>5</td>
            <td><p>Template is a reminder.</p></td>
          </tr>
        
          <tr>
            <td>TEMPLATE_TYPE_CONFIRMATION</td>
            <td>6</td>
            <td><p>Template is a confirmaiton.</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.communication.CommunicationService">CommunicationService</h3>
    <p>Service API</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>CreateEmailTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</a></td>
            <td><p>Email Template Management

Create a new email template.</p></td>
          </tr>
        
          <tr>
            <td>GetEmailTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</a></td>
            <td><p>Retrieve an email template by ID.</p></td>
          </tr>
        
          <tr>
            <td>UpdateEmailTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplate">EmailTemplate</a></td>
            <td><p>Update an email template.</p></td>
          </tr>
        
          <tr>
            <td>DeleteEmailTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SuccessResponse">SuccessResponse</a></td>
            <td><p>Delete an email template.</p></td>
          </tr>
        
          <tr>
            <td>CreateSMSTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</a></td>
            <td><p>SMS Template Management

Create a new SMS template.</p></td>
          </tr>
        
          <tr>
            <td>GetSMSTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</a></td>
            <td><p>Retrieve an SMS template by ID.</p></td>
          </tr>
        
          <tr>
            <td>UpdateSMSTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplate">SMSTemplate</a></td>
            <td><p>Update an SMS template.</p></td>
          </tr>
        
          <tr>
            <td>DeleteSMSTemplate</td>
            <td><a href="#cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SuccessResponse">SuccessResponse</a></td>
            <td><p>Delete an SMS template.</p></td>
          </tr>
        
          <tr>
            <td>CreateNotificationEvent</td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</a></td>
            <td><p>Notification Event Management

Create a new notification event.</p></td>
          </tr>
        
          <tr>
            <td>GetNotificationEvent</td>
            <td><a href="#cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</a></td>
            <td><p>Retrieve a notification event by ID.</p></td>
          </tr>
        
          <tr>
            <td>UpdateNotificationEvent</td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEvent">NotificationEvent</a></td>
            <td><p>Update a notification event.</p></td>
          </tr>
        
          <tr>
            <td>DeleteNotificationEvent</td>
            <td><a href="#cdx.opencdx.grpc.communication.TemplateRequest">TemplateRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SuccessResponse">SuccessResponse</a></td>
            <td><p>Delete a notification event.</p></td>
          </tr>
        
          <tr>
            <td>SendNotification</td>
            <td><a href="#cdx.opencdx.grpc.communication.Notification">Notification</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SuccessResponse">SuccessResponse</a></td>
            <td><p>Notification Queue Operations

Send a notification.</p></td>
          </tr>
        
          <tr>
            <td>listSMSTemplates</td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplateListRequest">SMSTemplateListRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.SMSTemplateListResponse">SMSTemplateListResponse</a></td>
            <td><p>Retrieve Lists

Retrieve a list of SMS templates.</p></td>
          </tr>
        
          <tr>
            <td>listEmailTemplates</td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplateListRequest">EmailTemplateListRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.EmailTemplateListResponse">EmailTemplateListResponse</a></td>
            <td><p>Retrieve a list of email templates.</p></td>
          </tr>
        
          <tr>
            <td>listNotificationEvents</td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEventListRequest">NotificationEventListRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.communication.NotificationEventListResponse">NotificationEventListResponse</a></td>
            <td><p>Retrieve a list of notification events.</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="connected_test.proto">connected_test.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.connected.BasicInfo">BasicInfo</h3>
    <p>Basic Information about the Test</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the test </p></td>
            </tr>
          
            <tr>
              <td>created</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Creation timestamp of the test record </p></td>
            </tr>
          
            <tr>
              <td>creator</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User or entity that created the test </p></td>
            </tr>
          
            <tr>
              <td>modified</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp when the test was last modified </p></td>
            </tr>
          
            <tr>
              <td>modifier</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User or entity that last modified the test </p></td>
            </tr>
          
            <tr>
              <td>vendor_lab_test_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID referencing the test within the vendor&#39;s system </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>className or type of the test </p></td>
            </tr>
          
            <tr>
              <td>user_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the user associated with the test </p></td>
            </tr>
          
            <tr>
              <td>national_health_id</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>National health ID of the user </p></td>
            </tr>
          
            <tr>
              <td>health_service_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the health service provider </p></td>
            </tr>
          
            <tr>
              <td>tenant_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the tenant (in multi-tenant systems) </p></td>
            </tr>
          
            <tr>
              <td>source</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Source or origin of the test data </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.ConnectedTest">ConnectedTest</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>basic_info</td>
              <td><a href="#cdx.opencdx.grpc.connected.BasicInfo">BasicInfo</a></td>
              <td></td>
              <td><p>Basic information block </p></td>
            </tr>
          
            <tr>
              <td>order_info</td>
              <td><a href="#cdx.opencdx.grpc.connected.OrderInfo">OrderInfo</a></td>
              <td></td>
              <td><p>Ordering related block </p></td>
            </tr>
          
            <tr>
              <td>test_notes</td>
              <td><a href="#cdx.opencdx.grpc.connected.TestNotes">TestNotes</a></td>
              <td></td>
              <td><p>Notes related block </p></td>
            </tr>
          
            <tr>
              <td>payment_details</td>
              <td><a href="#cdx.opencdx.grpc.connected.PaymentDetails">PaymentDetails</a></td>
              <td></td>
              <td><p>Payment details block </p></td>
            </tr>
          
            <tr>
              <td>provider_info</td>
              <td><a href="#cdx.opencdx.grpc.connected.ProviderInfo">ProviderInfo</a></td>
              <td></td>
              <td><p>Provider details block </p></td>
            </tr>
          
            <tr>
              <td>test_details</td>
              <td><a href="#cdx.opencdx.grpc.connected.TestDetails">TestDetails</a></td>
              <td></td>
              <td><p>Test and result details block </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.ConnectedTestListByNHIDRequest">ConnectedTestListByNHIDRequest</h3>
    <p>Request Message to list NotificationConnectedTests by National health id</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending </p></td>
            </tr>
          
            <tr>
              <td>national_health_id</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>national health id of user </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.ConnectedTestListByNHIDResponse">ConnectedTestListByNHIDResponse</h3>
    <p>Response containing the requested list of connected tests for a specific national health id</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages </p></td>
            </tr>
          
            <tr>
              <td>connected_tests</td>
              <td><a href="#cdx.opencdx.grpc.connected.ConnectedTest">ConnectedTest</a></td>
              <td>repeated</td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.ConnectedTestListRequest">ConnectedTestListRequest</h3>
    <p>Request Message to list NotificationConnectedTests</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending </p></td>
            </tr>
          
            <tr>
              <td>user_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>UUID of user </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.ConnectedTestListResponse">ConnectedTestListResponse</h3>
    <p>Response containing the requested list of connected tests for a specific user</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages </p></td>
            </tr>
          
            <tr>
              <td>connected_tests</td>
              <td><a href="#cdx.opencdx.grpc.connected.ConnectedTest">ConnectedTest</a></td>
              <td>repeated</td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.Metadata">Metadata</h3>
    <p>Metadata related to a specific test.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>qr_data</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>QR Code data associated with the test kit </p></td>
            </tr>
          
            <tr>
              <td>kit_upload_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID associated with the kit&#39;s data upload event </p></td>
            </tr>
          
            <tr>
              <td>response_message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Detailed message related to the response or result </p></td>
            </tr>
          
            <tr>
              <td>response_title</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Title or header of the response </p></td>
            </tr>
          
            <tr>
              <td>response_code</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Numeric code representing the response type </p></td>
            </tr>
          
            <tr>
              <td>image_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Type or format of any associated image (e.g., &#34;jpg&#34;, &#34;png&#34;) </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Type or category of the test </p></td>
            </tr>
          
            <tr>
              <td>manufacturer</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer or producer of the test kit </p></td>
            </tr>
          
            <tr>
              <td>cassete_lot_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Lot number associated with the cassette of the test kit </p></td>
            </tr>
          
            <tr>
              <td>outcome_igm</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Boolean outcome for IgM antibody </p></td>
            </tr>
          
            <tr>
              <td>outcome_igg</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Boolean outcome for IgG antibody </p></td>
            </tr>
          
            <tr>
              <td>outcome</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Overall outcome or result of the test </p></td>
            </tr>
          
            <tr>
              <td>self_assessment_outcome_igm</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Self-assessed outcome for IgM antibody </p></td>
            </tr>
          
            <tr>
              <td>self_assessment_outcome_igg</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Self-assessed outcome for IgG antibody </p></td>
            </tr>
          
            <tr>
              <td>self_assessment_outcome</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Overall self-assessed outcome or result </p></td>
            </tr>
          
            <tr>
              <td>cassete_expiration_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Expiration date of the test cassette </p></td>
            </tr>
          
            <tr>
              <td>lab_test_orderable_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID associated with the orderable lab test </p></td>
            </tr>
          
            <tr>
              <td>sku_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Stock Keeping Unit (SKU) ID for the test kit </p></td>
            </tr>
          
            <tr>
              <td>cassette_verification</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Verification status or details of the cassette </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.OrderInfo">OrderInfo</h3>
    <p>Information related to ordering</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>order_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the test order </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Current status of the test order </p></td>
            </tr>
          
            <tr>
              <td>status_message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Descriptive message about the order status </p></td>
            </tr>
          
            <tr>
              <td>status_message_actions</td>
              <td><a href="#cdx.opencdx.grpc.connected.StatusMessageAction">StatusMessageAction</a></td>
              <td>repeated</td>
              <td><p>Actions associated with the status </p></td>
            </tr>
          
            <tr>
              <td>encounter_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the clinical encounter associated with the test </p></td>
            </tr>
          
            <tr>
              <td>is_synced_with_ehr</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicator if the test has been synced with an EHR system </p></td>
            </tr>
          
            <tr>
              <td>result</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Test result data </p></td>
            </tr>
          
            <tr>
              <td>questionnaire_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the associated questionnaire, if any </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.OrderableTest">OrderableTest</h3>
    <p>Represents a specific test that can be ordered.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>orderable_test_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the orderable test </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.OrderableTestResult">OrderableTestResult</h3>
    <p>Detailed result information for an orderable test.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>orderable_test_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Refers to the specific test being described </p></td>
            </tr>
          
            <tr>
              <td>collection_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Date when the sample or specimen was collected </p></td>
            </tr>
          
            <tr>
              <td>test_result</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Actual result or value from the test </p></td>
            </tr>
          
            <tr>
              <td>outcome</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Interpretation or high-level outcome of the test </p></td>
            </tr>
          
            <tr>
              <td>response_message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Detailed message related to the test&#39;s response or result </p></td>
            </tr>
          
            <tr>
              <td>response_title</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Title or header of the response </p></td>
            </tr>
          
            <tr>
              <td>response_code</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Numeric code representing the response type </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.PaymentDetails">PaymentDetails</h3>
    <p>Payment details</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>payment_mode</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Mode of payment used </p></td>
            </tr>
          
            <tr>
              <td>insurance_info_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Reference ID for insurance information </p></td>
            </tr>
          
            <tr>
              <td>payment_transaction_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the payment transaction </p></td>
            </tr>
          
            <tr>
              <td>payment_details</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Additional details about the payment </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.ProviderInfo">ProviderInfo</h3>
    <p>Information about providers</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>ordering_provider_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the provider who ordered the test </p></td>
            </tr>
          
            <tr>
              <td>assigned_provider_id</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>ID of the provider assigned to oversee the test </p></td>
            </tr>
          
            <tr>
              <td>ordering_provider_npi</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>National Provider Identifier of the ordering provider </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.StatusMessageAction">StatusMessageAction</h3>
    <p>Describes actions or recommendations based on a status.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the action </p></td>
            </tr>
          
            <tr>
              <td>value</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Actual value or action to be taken </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Detailed description of the action or recommendation </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.TestDetails">TestDetails</h3>
    <p>Details about the test itself and results</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>metadata</td>
              <td><a href="#cdx.opencdx.grpc.connected.Metadata">Metadata</a></td>
              <td></td>
              <td><p>Metadata associated with the test </p></td>
            </tr>
          
            <tr>
              <td>requisition_base64</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Base64 encoded requisition data </p></td>
            </tr>
          
            <tr>
              <td>internal_test_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Internal reference ID for the test </p></td>
            </tr>
          
            <tr>
              <td>medications</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Information on medications related to the test </p></td>
            </tr>
          
            <tr>
              <td>referrals</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Referral details </p></td>
            </tr>
          
            <tr>
              <td>diagnostics</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Diagnostic details </p></td>
            </tr>
          
            <tr>
              <td>orderable_test_ids</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>List of IDs for orderable tests </p></td>
            </tr>
          
            <tr>
              <td>orderable_tests</td>
              <td><a href="#cdx.opencdx.grpc.connected.OrderableTest">OrderableTest</a></td>
              <td>repeated</td>
              <td><p>Orderable test details </p></td>
            </tr>
          
            <tr>
              <td>orderable_test_results</td>
              <td><a href="#cdx.opencdx.grpc.connected.OrderableTestResult">OrderableTestResult</a></td>
              <td>repeated</td>
              <td><p>Results for orderable tests </p></td>
            </tr>
          
            <tr>
              <td>test_classNameification</td>
              <td><a href="#cdx.opencdx.grpc.connected.TestclassNameification">TestclassNameification</a></td>
              <td></td>
              <td><p>classNameification or type of the test </p></td>
            </tr>
          
            <tr>
              <td>is_onsite_test</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicator if the test is conducted on-site </p></td>
            </tr>
          
            <tr>
              <td>specimen_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the specimen collected </p></td>
            </tr>
          
            <tr>
              <td>lab_vendor_confirmation_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Confirmation ID from the lab vendor </p></td>
            </tr>
          
            <tr>
              <td>device_identifier</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Identifier of the device used for the test </p></td>
            </tr>
          
            <tr>
              <td>device_serial_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Serial number of the device </p></td>
            </tr>
          
            <tr>
              <td>is_auto_generated</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicator if the test record was auto-generated </p></td>
            </tr>
          
            <tr>
              <td>reporting_flag</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Flag indicating reporting status </p></td>
            </tr>
          
            <tr>
              <td>notification_flag</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Flag indicating notification status </p></td>
            </tr>
          
            <tr>
              <td>order_status_flag</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Flag indicating order status </p></td>
            </tr>
          
            <tr>
              <td>order_receipt_path</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Path to the order receipt </p></td>
            </tr>
          
            <tr>
              <td>lab_test_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Type of the lab test </p></td>
            </tr>
          
            <tr>
              <td>speciman_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Type of specimen used for the test </p></td>
            </tr>
          
            <tr>
              <td>medical_code</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Medical code associated with the test </p></td>
            </tr>
          
            <tr>
              <td>test_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Human readable test name </p></td>
            </tr>
          
            <tr>
              <td>test_format</td>
              <td><a href="#cdx.opencdx.grpc.connected.TestFormat">TestFormat</a></td>
              <td></td>
              <td><p>Format or type of the test (e.g., &#34;PCR&#34;, &#34;Antigen&#34;, &#34;Serology&#34;, etc.) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.TestIdRequest">TestIdRequest</h3>
    <p>Request for a Connected Test</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>test_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of a connected Test </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.TestKitMetadata">TestKitMetadata</h3>
    <p>Metadata related to the physical test kit used.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>orderable_test_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID associated with the orderable test </p></td>
            </tr>
          
            <tr>
              <td>device_identifier</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Device identifier or code for the test kit </p></td>
            </tr>
          
            <tr>
              <td>batch_lot_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Lot or batch number of the test kit </p></td>
            </tr>
          
            <tr>
              <td>serial_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Serial number of the test kit </p></td>
            </tr>
          
            <tr>
              <td>manufacturing_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Date when the test kit was manufactured </p></td>
            </tr>
          
            <tr>
              <td>expiration_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Expiration date for the test kit </p></td>
            </tr>
          
            <tr>
              <td>barcode_data_format</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Data format of the kit&#39;s barcode </p></td>
            </tr>
          
            <tr>
              <td>scan_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Timestamp when the kit&#39;s barcode was scanned </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.TestNotes">TestNotes</h3>
    <p>Notes regarding the test</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>notes</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>General notes about the test </p></td>
            </tr>
          
            <tr>
              <td>medication_notes</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Notes about medication related to the test </p></td>
            </tr>
          
            <tr>
              <td>referral_notes</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Notes about referrals related to the test </p></td>
            </tr>
          
            <tr>
              <td>diagnostic_notes</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Diagnostic notes or observations </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.connected.TestSubmissionResponse">TestSubmissionResponse</h3>
    <p>Response for submitting a ConnectedTest</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>submission_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the Connected Test </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.connected.LabTestType">LabTestType</h3>
    <p>Define lab test types</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>LAB_TEST_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified Lab Test Type</p></td>
          </tr>
        
          <tr>
            <td>LAB_TEST_TYPE_BLOOD_TEST</td>
            <td>1</td>
            <td><p>Blood Lab Test</p></td>
          </tr>
        
          <tr>
            <td>LAB_TEST_TYPE_URINE_TEST</td>
            <td>2</td>
            <td><p>Urine Lab Test</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.connected.SpecimenType">SpecimenType</h3>
    <p>Define specimen types</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>SPECIMEN_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified Lab Test Type</p></td>
          </tr>
        
          <tr>
            <td>SPECIMEN_TYPE_BLOOD</td>
            <td>1</td>
            <td><p>Specimen is a blood sample.</p></td>
          </tr>
        
          <tr>
            <td>SPECIMEN_TYPE_URINE</td>
            <td>2</td>
            <td><p>Specimen is a urine sample.</p></td>
          </tr>
        
          <tr>
            <td>SPECIMEN_TYPE_SALIVA</td>
            <td>3</td>
            <td><p>Specimen is a saliva sample.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.connected.TestclassNameification">TestclassNameification</h3>
    <p>Enum to classNameify the type of test</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>TEST_classNameIFICATION_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified test classNameification</p></td>
          </tr>
        
          <tr>
            <td>TEST_classNameIFICATION_GENERAL</td>
            <td>1</td>
            <td><p>General test</p></td>
          </tr>
        
          <tr>
            <td>TEST_classNameIFICATION_DIAGNOSTIC</td>
            <td>2</td>
            <td><p>Diagnostic test</p></td>
          </tr>
        
          <tr>
            <td>TEST_classNameIFICATION_SCREENING</td>
            <td>3</td>
            <td><p>Screening test</p></td>
          </tr>
        
          <tr>
            <td>TEST_classNameIFICATION_MONITORING</td>
            <td>4</td>
            <td><p>Monitoring test</p></td>
          </tr>
        
          <tr>
            <td>TEST_classNameIFICATION_COVID_19</td>
            <td>5</td>
            <td><p>COVID-19 related test</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.connected.TestFormat">TestFormat</h3>
    <p>Define test format types</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>TEST_FORMAT_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified test format</p></td>
          </tr>
        
          <tr>
            <td>TEST_FORMAT_BLOOD</td>
            <td>1</td>
            <td><p>Blood test</p></td>
          </tr>
        
          <tr>
            <td>TEST_FORMAT_URINE</td>
            <td>2</td>
            <td><p>Urine test</p></td>
          </tr>
        
          <tr>
            <td>TEST_FORMAT_SALIVA</td>
            <td>3</td>
            <td><p>Saliva test</p></td>
          </tr>
        
          <tr>
            <td>TEST_FORMAT_COVID_19</td>
            <td>4</td>
            <td><p>COVID-19 test</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.connected.HealthcareService">HealthcareService</h3>
    <p></p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>SubmitTest</td>
            <td><a href="#cdx.opencdx.grpc.connected.ConnectedTest">ConnectedTest</a></td>
            <td><a href="#cdx.opencdx.grpc.connected.TestSubmissionResponse">TestSubmissionResponse</a></td>
            <td><p>RPC method to submit a new test</p></td>
          </tr>
        
          <tr>
            <td>GetTestDetailsById</td>
            <td><a href="#cdx.opencdx.grpc.connected.TestIdRequest">TestIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.connected.ConnectedTest">ConnectedTest</a></td>
            <td><p>RPC method to get test details by ID</p></td>
          </tr>
        
          <tr>
            <td>ListConnectedTests</td>
            <td><a href="#cdx.opencdx.grpc.connected.ConnectedTestListRequest">ConnectedTestListRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.connected.ConnectedTestListResponse">ConnectedTestListResponse</a></td>
            <td><p>Retrieve a list of connected tests by userid</p></td>
          </tr>
        
          <tr>
            <td>ListConnectedTestsByNHID</td>
            <td><a href="#cdx.opencdx.grpc.connected.ConnectedTestListByNHIDRequest">ConnectedTestListByNHIDRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.connected.ConnectedTestListByNHIDResponse">ConnectedTestListByNHIDResponse</a></td>
            <td><p>Retrieve a list of connected tests by national health id</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="helloworld.proto">helloworld.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.helloworld.HelloReply">HelloReply</h3>
    <p>The response message containing the greetings</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>This is the description for the message attribute of the HelloReply message. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.helloworld.HelloRequest">HelloRequest</h3>
    <p>The request message containing the user name.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>This is the description for the name attribute of the HelloRequest message. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  

  

  
    <h3 id="cdx.opencdx.grpc.helloworld.Greeter">Greeter</h3>
    <p>The greeting service definition.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>SayHello</td>
            <td><a href="#cdx.opencdx.grpc.helloworld.HelloRequest">HelloRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.helloworld.HelloReply">HelloReply</a></td>
            <td><p>This is the description for the grpc SayHello Greeter Service. This takes in a HelloRequest as message and returns
a HelloReply greeting message. Used to Send a greeting.</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="iam_organization.proto">iam_organization.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.organization.ContactInfo">ContactInfo</h3>
    <p>Contact information for an organization or workspace</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>contact_name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the contact person </p></td>
            </tr>
          
            <tr>
              <td>email</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Email address of the contact person </p></td>
            </tr>
          
            <tr>
              <td>phone</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Phone number of the contact person </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.CreateOrganizationRequest">CreateOrganizationRequest</h3>
    <p>Request for creating an organization</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization</td>
              <td><a href="#cdx.opencdx.grpc.organization.Organization">Organization</a></td>
              <td>optional</td>
              <td><p>Organization details for creation </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.CreateOrganizationResponse">CreateOrganizationResponse</h3>
    <p>Response for creating an organization</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization</td>
              <td><a href="#cdx.opencdx.grpc.organization.Organization">Organization</a></td>
              <td>optional</td>
              <td><p>Created organization details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.CreateWorkspaceRequest">CreateWorkspaceRequest</h3>
    <p>Request for creating a workspace within an organization</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspace</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>optional</td>
              <td><p>Workspace details for creation </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.CreateWorkspaceResponse">CreateWorkspaceResponse</h3>
    <p>Response for creating a workspace within an organization</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspace</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>optional</td>
              <td><p>Created workspace details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.Department">Department</h3>
    <p>Department within an organization or workspace</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the department </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the department </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Description of the department </p></td>
            </tr>
          
            <tr>
              <td>employees</td>
              <td><a href="#cdx.opencdx.grpc.organization.Employee">Employee</a></td>
              <td>repeated</td>
              <td><p>Employees within the department </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.Employee">Employee</h3>
    <p>Employee within a department</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>employee_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the employee </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the employee </p></td>
            </tr>
          
            <tr>
              <td>title</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Title or position of the employee </p></td>
            </tr>
          
            <tr>
              <td>email</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Email address of the employee </p></td>
            </tr>
          
            <tr>
              <td>phone</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Phone number of the employee </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.Empty">Empty</h3>
    <p>Empty message used in RPC methods</p>

    

    
  
    <h3 id="cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdRequest">GetOrganizationDetailsByIdRequest</h3>
    <p>Request for retrieving organization details by ID</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the organization </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdResponse">GetOrganizationDetailsByIdResponse</h3>
    <p>Response for retrieving organization details by ID</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization</td>
              <td><a href="#cdx.opencdx.grpc.organization.Organization">Organization</a></td>
              <td>optional</td>
              <td><p>Retrieved organization details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdRequest">GetWorkspaceDetailsByIdRequest</h3>
    <p>Request for retrieving workspace details by ID</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspace_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the workspace </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdResponse">GetWorkspaceDetailsByIdResponse</h3>
    <p>Response for retrieving workspace details by ID</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspace</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>optional</td>
              <td><p>Retrieved workspace details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.ListOrganizationsResponse">ListOrganizationsResponse</h3>
    <p>Response for listing all organizations</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organizations</td>
              <td><a href="#cdx.opencdx.grpc.organization.Organization">Organization</a></td>
              <td>repeated</td>
              <td><p>List of organizations </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.ListWorkspacesResponse">ListWorkspacesResponse</h3>
    <p>Response for listing all workspaces within an organization</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspaces</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>repeated</td>
              <td><p>List of workspaces </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.Organization">Organization</h3>
    <p>Definition for an organization</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the organization </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the organization </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Description of the organization </p></td>
            </tr>
          
            <tr>
              <td>founding_date</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Date when the organization was founded </p></td>
            </tr>
          
            <tr>
              <td>address</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Physical address of the organization </p></td>
            </tr>
          
            <tr>
              <td>website</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Organization&#39;s website URL </p></td>
            </tr>
          
            <tr>
              <td>industry</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Industry or sector of the organization </p></td>
            </tr>
          
            <tr>
              <td>revenue</td>
              <td><a href="#double">double</a></td>
              <td>optional</td>
              <td><p>Annual revenue of the organization </p></td>
            </tr>
          
            <tr>
              <td>logo_url</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>URL to the organization&#39;s logo </p></td>
            </tr>
          
            <tr>
              <td>social_media_links</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Links to social media profiles </p></td>
            </tr>
          
            <tr>
              <td>mission_statement</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Mission statement of the organization </p></td>
            </tr>
          
            <tr>
              <td>vision_statement</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Vision statement of the organization </p></td>
            </tr>
          
            <tr>
              <td>contacts</td>
              <td><a href="#cdx.opencdx.grpc.organization.ContactInfo">ContactInfo</a></td>
              <td>repeated</td>
              <td><p>Contact information for the organization </p></td>
            </tr>
          
            <tr>
              <td>workspace</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>repeated</td>
              <td><p>Departments (Workspaces) within the organization </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.UpdateOrganizationRequest">UpdateOrganizationRequest</h3>
    <p>Request for updating organization details</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization</td>
              <td><a href="#cdx.opencdx.grpc.organization.Organization">Organization</a></td>
              <td>optional</td>
              <td><p>Updated organization details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.UpdateOrganizationResponse">UpdateOrganizationResponse</h3>
    <p>Response for updating organization details</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization</td>
              <td><a href="#cdx.opencdx.grpc.organization.Organization">Organization</a></td>
              <td>optional</td>
              <td><p>Updated organization details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.UpdateWorkspaceRequest">UpdateWorkspaceRequest</h3>
    <p>Request for updating workspace details</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspace</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>optional</td>
              <td><p>Updated workspace details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.UpdateWorkspaceResponse">UpdateWorkspaceResponse</h3>
    <p>Response for updating workspace details</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>workspace</td>
              <td><a href="#cdx.opencdx.grpc.organization.Workspace">Workspace</a></td>
              <td>optional</td>
              <td><p>Updated workspace details </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.organization.Workspace">Workspace</h3>
    <p>Definition for a workspace</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for the workspace </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the workspace </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Description of the workspace </p></td>
            </tr>
          
            <tr>
              <td>created_date</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Date when the workspace was created </p></td>
            </tr>
          
            <tr>
              <td>location</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Physical location of the workspace </p></td>
            </tr>
          
            <tr>
              <td>manager</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the workspace manager </p></td>
            </tr>
          
            <tr>
              <td>capacity</td>
              <td><a href="#int32">int32</a></td>
              <td>optional</td>
              <td><p>Maximum capacity of the workspace </p></td>
            </tr>
          
            <tr>
              <td>facilities</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Facilities available within the workspace </p></td>
            </tr>
          
            <tr>
              <td>workspace_type</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Type of workspace (e.g., office, co-working space) </p></td>
            </tr>
          
            <tr>
              <td>workspace_image_urls</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>URLs to images of the workspace </p></td>
            </tr>
          
            <tr>
              <td>usage_policy</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Policies and rules for using the workspace </p></td>
            </tr>
          
            <tr>
              <td>availability_schedule</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Schedule of when the workspace is available </p></td>
            </tr>
          
            <tr>
              <td>departments</td>
              <td><a href="#cdx.opencdx.grpc.organization.Department">Department</a></td>
              <td>repeated</td>
              <td><p>Departments within the workspace </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  

  

  
    <h3 id="cdx.opencdx.grpc.organization.OrganizationService">OrganizationService</h3>
    <p></p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>CreateOrganization</td>
            <td><a href="#cdx.opencdx.grpc.organization.CreateOrganizationRequest">CreateOrganizationRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.CreateOrganizationResponse">CreateOrganizationResponse</a></td>
            <td><p>RPC method to create a new organization</p></td>
          </tr>
        
          <tr>
            <td>GetOrganizationDetailsById</td>
            <td><a href="#cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdRequest">GetOrganizationDetailsByIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdResponse">GetOrganizationDetailsByIdResponse</a></td>
            <td><p>RPC method to retrieve organization details by ID</p></td>
          </tr>
        
          <tr>
            <td>UpdateOrganization</td>
            <td><a href="#cdx.opencdx.grpc.organization.UpdateOrganizationRequest">UpdateOrganizationRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.UpdateOrganizationResponse">UpdateOrganizationResponse</a></td>
            <td><p>RPC method to update organization details</p></td>
          </tr>
        
          <tr>
            <td>ListOrganizations</td>
            <td><a href="#cdx.opencdx.grpc.organization.Empty">Empty</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.ListOrganizationsResponse">ListOrganizationsResponse</a></td>
            <td><p>RPC method to list all organizations</p></td>
          </tr>
        
      </tbody>
    </table>

    
    <h3 id="cdx.opencdx.grpc.organization.WorkspaceService">WorkspaceService</h3>
    <p></p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>CreateWorkspace</td>
            <td><a href="#cdx.opencdx.grpc.organization.CreateWorkspaceRequest">CreateWorkspaceRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.CreateWorkspaceResponse">CreateWorkspaceResponse</a></td>
            <td><p>RPC method to create a new workspace within an organization</p></td>
          </tr>
        
          <tr>
            <td>GetWorkspaceDetailsById</td>
            <td><a href="#cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdRequest">GetWorkspaceDetailsByIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdResponse">GetWorkspaceDetailsByIdResponse</a></td>
            <td><p>RPC method to retrieve workspace details by ID</p></td>
          </tr>
        
          <tr>
            <td>UpdateWorkspace</td>
            <td><a href="#cdx.opencdx.grpc.organization.UpdateWorkspaceRequest">UpdateWorkspaceRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.UpdateWorkspaceResponse">UpdateWorkspaceResponse</a></td>
            <td><p>RPC method to update workspace details</p></td>
          </tr>
        
          <tr>
            <td>ListWorkspaces</td>
            <td><a href="#cdx.opencdx.grpc.organization.Empty">Empty</a></td>
            <td><a href="#cdx.opencdx.grpc.organization.ListWorkspacesResponse">ListWorkspacesResponse</a></td>
            <td><p>RPC method to list all workspaces within an organization</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="iam_profile.proto">iam_profile.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.profile.Address">Address</h3>
    <p>Address details.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>street</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Street address </p></td>
            </tr>
          
            <tr>
              <td>city</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>City </p></td>
            </tr>
          
            <tr>
              <td>state</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>State or region </p></td>
            </tr>
          
            <tr>
              <td>postal_code</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Postal code </p></td>
            </tr>
          
            <tr>
              <td>country</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Country code </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.ContactInfo">ContactInfo</h3>
    <p>Contact information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>mobile_number</td>
              <td><a href="#cdx.opencdx.grpc.profile.PhoneNumber">PhoneNumber</a></td>
              <td></td>
              <td><p>User&#39;s mobile phone number </p></td>
            </tr>
          
            <tr>
              <td>home_number</td>
              <td><a href="#cdx.opencdx.grpc.profile.PhoneNumber">PhoneNumber</a></td>
              <td></td>
              <td><p>User&#39;s home phone number </p></td>
            </tr>
          
            <tr>
              <td>work_number</td>
              <td><a href="#cdx.opencdx.grpc.profile.PhoneNumber">PhoneNumber</a></td>
              <td></td>
              <td><p>User&#39;s work phone number </p></td>
            </tr>
          
            <tr>
              <td>fax_number</td>
              <td><a href="#cdx.opencdx.grpc.profile.PhoneNumber">PhoneNumber</a></td>
              <td></td>
              <td><p>User&#39;s fax number </p></td>
            </tr>
          
            <tr>
              <td>email</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User&#39;s email address </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.DateOfBirth">DateOfBirth</h3>
    <p>user date of birth information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Date of birth in a suitable format </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.DeleteUserProfileRequest">DeleteUserProfileRequest</h3>
    <p>Request message for deleting a user profile</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>user_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the user </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.DeleteUserProfileResponse">DeleteUserProfileResponse</h3>
    <p>Response message for deleting a user profile</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>success</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the delete was successful </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.Demographics">Demographics</h3>
    <p>user demographic information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>ethnicity</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Ethnicity of the user </p></td>
            </tr>
          
            <tr>
              <td>race</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Race of the user </p></td>
            </tr>
          
            <tr>
              <td>nationality</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Nationality of the user </p></td>
            </tr>
          
            <tr>
              <td>gender</td>
              <td><a href="#cdx.opencdx.grpc.profile.Gender">Gender</a></td>
              <td></td>
              <td><p>Gender of the user (e.g., Male, Female, Non-binary, etc.) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.Education">Education</h3>
    <p>user education information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>entries</td>
              <td><a href="#cdx.opencdx.grpc.profile.EducationEntry">EducationEntry</a></td>
              <td>repeated</td>
              <td><p>Education entries </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.EducationEntry">EducationEntry</h3>
    <p>Education entry details.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>degree</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Degree obtained </p></td>
            </tr>
          
            <tr>
              <td>institution</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Institution of education </p></td>
            </tr>
          
            <tr>
              <td>start_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Start date of education </p></td>
            </tr>
          
            <tr>
              <td>completion_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Completion date of education </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.EmergencyContact">EmergencyContact</h3>
    <p>Emergency contact information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>relationship</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Relationship to the user </p></td>
            </tr>
          
            <tr>
              <td>contact_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the emergency contact </p></td>
            </tr>
          
            <tr>
              <td>contact_info</td>
              <td><a href="#cdx.opencdx.grpc.profile.ContactInfo">ContactInfo</a></td>
              <td></td>
              <td><p>Contact information for the emergency contact </p></td>
            </tr>
          
            <tr>
              <td>residence_address</td>
              <td><a href="#cdx.opencdx.grpc.profile.Address">Address</a></td>
              <td></td>
              <td><p>Residence address of the emergency contact </p></td>
            </tr>
          
            <tr>
              <td>work_address</td>
              <td><a href="#cdx.opencdx.grpc.profile.Address">Address</a></td>
              <td></td>
              <td><p>Work address of the emergency contact </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.EmployeeIdentity">EmployeeIdentity</h3>
    <p>Employee identity information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>organization_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Organization ID </p></td>
            </tr>
          
            <tr>
              <td>workspace_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Workspace ID </p></td>
            </tr>
          
            <tr>
              <td>employee_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Employee ID </p></td>
            </tr>
          
            <tr>
              <td>identity_verified</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Whether employee identity is verified </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Employee status </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.FullName">FullName</h3>
    <p>Full name information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>title</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Title of the user (e.g., Mr., Mrs., Dr.) </p></td>
            </tr>
          
            <tr>
              <td>first_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>First name of the user </p></td>
            </tr>
          
            <tr>
              <td>middle_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Middle name of the user </p></td>
            </tr>
          
            <tr>
              <td>last_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Last name of the user </p></td>
            </tr>
          
            <tr>
              <td>suffix</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Suffix for the name (e.g., Jr., Sr., III) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.KnownAllergy">KnownAllergy</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>allergen</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The substance causing the allergy </p></td>
            </tr>
          
            <tr>
              <td>reaction</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description of the allergic reaction </p></td>
            </tr>
          
            <tr>
              <td>is_severe</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates if the allergy is severe </p></td>
            </tr>
          
            <tr>
              <td>onset_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Date of onset of the allergy </p></td>
            </tr>
          
            <tr>
              <td>last_occurrence</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Date of the last occurrence of the allergic reaction </p></td>
            </tr>
          
            <tr>
              <td>notes</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Additional notes or comments about the allergy </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.Medication">Medication</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the medication </p></td>
            </tr>
          
            <tr>
              <td>dosage</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Dosage information (e.g., 500mg, twice daily) </p></td>
            </tr>
          
            <tr>
              <td>instructions</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Medication usage instructions </p></td>
            </tr>
          
            <tr>
              <td>route_of_administration</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Route of administration (e.g., oral, intravenous) </p></td>
            </tr>
          
            <tr>
              <td>frequency</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Frequency of medication (e.g., daily, weekly) </p></td>
            </tr>
          
            <tr>
              <td>start_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Start date for the medication </p></td>
            </tr>
          
            <tr>
              <td>end_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>End date for the medication </p></td>
            </tr>
          
            <tr>
              <td>prescribing_doctor</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the prescribing doctor </p></td>
            </tr>
          
            <tr>
              <td>pharmacy</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the pharmacy where the medication was obtained </p></td>
            </tr>
          
            <tr>
              <td>is_prescription</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates if the medication requires a prescription </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.Pharmacy">Pharmacy</h3>
    <p>Pharmacy information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>pharmacy_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the pharmacy </p></td>
            </tr>
          
            <tr>
              <td>pharmacy_address</td>
              <td><a href="#cdx.opencdx.grpc.profile.Address">Address</a></td>
              <td></td>
              <td><p>Address of the pharmacy </p></td>
            </tr>
          
            <tr>
              <td>pharmacy_contact</td>
              <td><a href="#cdx.opencdx.grpc.profile.ContactInfo">ContactInfo</a></td>
              <td></td>
              <td><p>Contact information for the pharmacy </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.PhoneNumber">PhoneNumber</h3>
    <p>Phone number details.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Phone number </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.profile.PhoneType">PhoneType</a></td>
              <td></td>
              <td><p>Type of phone number (e.g., mobile, home, work) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.PlaceOfBirth">PlaceOfBirth</h3>
    <p>user place of birth information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>country</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Country of birth </p></td>
            </tr>
          
            <tr>
              <td>state</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>State or region of birth </p></td>
            </tr>
          
            <tr>
              <td>city</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>City of birth </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.Preferences">Preferences</h3>
    <p>user communication preferences.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>language</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Preferred language </p></td>
            </tr>
          
            <tr>
              <td>preferred</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Whether the user prefers this mode of communication </p></td>
            </tr>
          
            <tr>
              <td>time_zone</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User&#39;s preferred time zone </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.SystemSettings">SystemSettings</h3>
    <p>System settings information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>identity_verified</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the user&#39;s identity is verified </p></td>
            </tr>
          
            <tr>
              <td>email_verified</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the user&#39;s email is verified </p></td>
            </tr>
          
            <tr>
              <td>sms_notifications</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether SMS notifications are enabled </p></td>
            </tr>
          
            <tr>
              <td>email_notifications</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether email notifications are enabled </p></td>
            </tr>
          
            <tr>
              <td>biometrics</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether biometrics are enabled </p></td>
            </tr>
          
            <tr>
              <td>user_since</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates the user&#39;s registration date </p></td>
            </tr>
          
            <tr>
              <td>mfa</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether multi-factor authentication (MFA) is enabled </p></td>
            </tr>
          
            <tr>
              <td>last_successful_login</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Last successful login date and time </p></td>
            </tr>
          
            <tr>
              <td>last_failed_login</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Last failed login date and time </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.UpdateUserProfileRequest">UpdateUserProfileRequest</h3>
    <p>Request message for updating a user profile</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>user_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the user </p></td>
            </tr>
          
            <tr>
              <td>updated_profile</td>
              <td><a href="#cdx.opencdx.grpc.profile.UserProfile">UserProfile</a></td>
              <td></td>
              <td><p>Updated user profile information </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.UpdateUserProfileResponse">UpdateUserProfileResponse</h3>
    <p>Response message for updating a user profile</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>success</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the update was successful </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.UserProfile">UserProfile</h3>
    <p>User Profile message containing user-related information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>user_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the user </p></td>
            </tr>
          
            <tr>
              <td>national_health_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>National health identifier </p></td>
            </tr>
          
            <tr>
              <td>full_name</td>
              <td><a href="#cdx.opencdx.grpc.profile.FullName">FullName</a></td>
              <td></td>
              <td><p>Full name of the user </p></td>
            </tr>
          
            <tr>
              <td>contacts</td>
              <td><a href="#cdx.opencdx.grpc.profile.ContactInfo">ContactInfo</a></td>
              <td>repeated</td>
              <td><p>User&#39;s contact information </p></td>
            </tr>
          
            <tr>
              <td>gender</td>
              <td><a href="#cdx.opencdx.grpc.profile.Gender">Gender</a></td>
              <td></td>
              <td><p>Gender of the user </p></td>
            </tr>
          
            <tr>
              <td>date_of_birth</td>
              <td><a href="#cdx.opencdx.grpc.profile.DateOfBirth">DateOfBirth</a></td>
              <td></td>
              <td><p>User&#39;s date of birth </p></td>
            </tr>
          
            <tr>
              <td>place_of_birth</td>
              <td><a href="#cdx.opencdx.grpc.profile.PlaceOfBirth">PlaceOfBirth</a></td>
              <td></td>
              <td><p>User&#39;s place of birth </p></td>
            </tr>
          
            <tr>
              <td>primary_address</td>
              <td><a href="#cdx.opencdx.grpc.profile.Address">Address</a></td>
              <td></td>
              <td><p>User&#39;s primary address </p></td>
            </tr>
          
            <tr>
              <td>photo</td>
              <td><a href="#bytes">bytes</a></td>
              <td></td>
              <td><p>Photo represented as bytes (binary data) </p></td>
            </tr>
          
            <tr>
              <td>communication</td>
              <td><a href="#cdx.opencdx.grpc.profile.Preferences">Preferences</a></td>
              <td></td>
              <td><p>User&#39;s communication preferences </p></td>
            </tr>
          
            <tr>
              <td>demographics</td>
              <td><a href="#cdx.opencdx.grpc.profile.Demographics">Demographics</a></td>
              <td></td>
              <td><p>User&#39;s demographics information </p></td>
            </tr>
          
            <tr>
              <td>education</td>
              <td><a href="#cdx.opencdx.grpc.profile.Education">Education</a></td>
              <td></td>
              <td><p>User&#39;s education information </p></td>
            </tr>
          
            <tr>
              <td>employee_identity</td>
              <td><a href="#cdx.opencdx.grpc.profile.EmployeeIdentity">EmployeeIdentity</a></td>
              <td></td>
              <td><p>User&#39;s employee identity information </p></td>
            </tr>
          
            <tr>
              <td>primary_contact_info</td>
              <td><a href="#cdx.opencdx.grpc.profile.ContactInfo">ContactInfo</a></td>
              <td></td>
              <td><p>Additional contact information </p></td>
            </tr>
          
            <tr>
              <td>billing_address</td>
              <td><a href="#cdx.opencdx.grpc.profile.Address">Address</a></td>
              <td></td>
              <td><p>User&#39;s billing address </p></td>
            </tr>
          
            <tr>
              <td>shipping_address</td>
              <td><a href="#cdx.opencdx.grpc.profile.Address">Address</a></td>
              <td></td>
              <td><p>User&#39;s shipping address </p></td>
            </tr>
          
            <tr>
              <td>emergency_contact</td>
              <td><a href="#cdx.opencdx.grpc.profile.EmergencyContact">EmergencyContact</a></td>
              <td></td>
              <td><p>User&#39;s emergency contact information </p></td>
            </tr>
          
            <tr>
              <td>pharmacy_details</td>
              <td><a href="#cdx.opencdx.grpc.profile.Pharmacy">Pharmacy</a></td>
              <td></td>
              <td><p>User&#39;s pharmacy details </p></td>
            </tr>
          
            <tr>
              <td>vaccine_administered</td>
              <td><a href="#cdx.opencdx.grpc.profile.Vaccine">Vaccine</a></td>
              <td>repeated</td>
              <td><p>Vaccines administered to the user </p></td>
            </tr>
          
            <tr>
              <td>dependent_id</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Identifiers for user&#39;s dependents </p></td>
            </tr>
          
            <tr>
              <td>known_allergies</td>
              <td><a href="#cdx.opencdx.grpc.profile.KnownAllergy">KnownAllergy</a></td>
              <td>repeated</td>
              <td><p>A list of known allergies for the user </p></td>
            </tr>
          
            <tr>
              <td>current_medications</td>
              <td><a href="#cdx.opencdx.grpc.profile.Medication">Medication</a></td>
              <td>repeated</td>
              <td><p>A list of medications currently being taken by the user </p></td>
            </tr>
          
            <tr>
              <td>is_active</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the user is active </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.UserProfileRequest">UserProfileRequest</h3>
    <p>Request message for retrieving a user profile</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>user_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique identifier for the user </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.UserProfileResponse">UserProfileResponse</h3>
    <p>Response message for retrieving a user profile</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>user_profile</td>
              <td><a href="#cdx.opencdx.grpc.profile.UserProfile">UserProfile</a></td>
              <td></td>
              <td><p>User&#39;s profile information </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.profile.Vaccine">Vaccine</h3>
    <p>Vaccination information.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>administration_date</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Date when the vaccine dose is administered to a person (Date &amp; Time) </p></td>
            </tr>
          
            <tr>
              <td>fips</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>5-digit code (51XXX) for the locality (Plain Text) </p></td>
            </tr>
          
            <tr>
              <td>locality</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Independent city or county in Virginia where the person lives (Plain Text) </p></td>
            </tr>
          
            <tr>
              <td>health_district</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Health district name assigned by the Virginia Department of Health (Plain Text) </p></td>
            </tr>
          
            <tr>
              <td>facility_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Facility type of the provider that performed the vaccine administration (Plain Text) </p></td>
            </tr>
          
            <tr>
              <td>manufacturer</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the manufacturing company that produced the vaccine (Plain Text) </p></td>
            </tr>
          
            <tr>
              <td>dose_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Dose number for the person who is administered the vaccine (Number) </p></td>
            </tr>
          
            <tr>
              <td>vaccine_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Type of vaccine </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.profile.Gender">Gender</h3>
    <p>Enumeration for gender.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>GENDER_NOT_SPECIFIED</td>
            <td>0</td>
            <td><p>Not specified</p></td>
          </tr>
        
          <tr>
            <td>GENDER_MALE</td>
            <td>1</td>
            <td><p>Male</p></td>
          </tr>
        
          <tr>
            <td>GENDER_FEMALE</td>
            <td>2</td>
            <td><p>Female</p></td>
          </tr>
        
          <tr>
            <td>GENDER_NON_BINARY</td>
            <td>3</td>
            <td><p>Non-binary</p></td>
          </tr>
        
          <tr>
            <td>GENDER_OTHER</td>
            <td>4</td>
            <td><p>Other</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.profile.PhoneType">PhoneType</h3>
    <p>Enumeration for phone types.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>PHONE_TYPE_NOT_SPECIFIED</td>
            <td>0</td>
            <td><p>Not specified</p></td>
          </tr>
        
          <tr>
            <td>PHONE_TYPE_MOBILE</td>
            <td>1</td>
            <td><p>Mobile phone</p></td>
          </tr>
        
          <tr>
            <td>PHONE_TYPE_HOME</td>
            <td>2</td>
            <td><p>Home phone</p></td>
          </tr>
        
          <tr>
            <td>PHONE_TYPE_WORK</td>
            <td>3</td>
            <td><p>Work phone</p></td>
          </tr>
        
          <tr>
            <td>PHONE_TYPE_FAX</td>
            <td>4</td>
            <td><p>Fax</p></td>
          </tr>
        
          <tr>
            <td>PHONE_TYPE_OTHER</td>
            <td>5</td>
            <td><p>Other</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.profile.UserProfileService">UserProfileService</h3>
    <p>Define the service</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>GetUserProfile</td>
            <td><a href="#cdx.opencdx.grpc.profile.UserProfileRequest">UserProfileRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.profile.UserProfileResponse">UserProfileResponse</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>UpdateUserProfile</td>
            <td><a href="#cdx.opencdx.grpc.profile.UpdateUserProfileRequest">UpdateUserProfileRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.profile.UpdateUserProfileResponse">UpdateUserProfileResponse</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>DeleteUserProfile</td>
            <td><a href="#cdx.opencdx.grpc.profile.DeleteUserProfileRequest">DeleteUserProfileRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.profile.DeleteUserProfileResponse">DeleteUserProfileResponse</a></td>
            <td><p></p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="iam_user.proto">iam_user.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.iam.ChangePasswordRequest">ChangePasswordRequest</h3>
    <p>Request message for changing a user password.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User ID for which the password is being changed. </p></td>
            </tr>
          
            <tr>
              <td>old_password</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User&#39;s old password. </p></td>
            </tr>
          
            <tr>
              <td>new_password</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>New password for the user. </p></td>
            </tr>
          
            <tr>
              <td>new_password_confirmation</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Confirmation of the new password. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.ChangePasswordResponse">ChangePasswordResponse</h3>
    <p>Response message for changing a user password.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.CurrentUserRequest">CurrentUserRequest</h3>
    <p>Request message for current user</p>

    

    
  
    <h3 id="cdx.opencdx.grpc.iam.CurrentUserResponse">CurrentUserResponse</h3>
    <p>Response message for current user</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.DeleteIamUserRequest">DeleteIamUserRequest</h3>
    <p>Request message for deleting an IAM user by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique ID of the IAM user to be deleted. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.DeleteIamUserResponse">DeleteIamUserResponse</h3>
    <p>Response message for deleting an IAM user by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.GetIamUserRequest">GetIamUserRequest</h3>
    <p>Request message for getting an IAM user by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique ID of the IAM user. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.GetIamUserResponse">GetIamUserResponse</h3>
    <p>Response message for getting an IAM user by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.IamUser">IamUser</h3>
    <p>Message representing an IAM user.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique ID of the user. </p></td>
            </tr>
          
            <tr>
              <td>created_at</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td>optional</td>
              <td><p>Timestamp when the user was created. </p></td>
            </tr>
          
            <tr>
              <td>updated_at</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td>optional</td>
              <td><p>Timestamp when the user was last updated. </p></td>
            </tr>
          
            <tr>
              <td>system_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Name of the system </p></td>
            </tr>
          
            <tr>
              <td>username</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Email address of the user. </p></td>
            </tr>
          
            <tr>
              <td>email_verified</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the user&#39;s email is verified. </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUserStatus">IamUserStatus</a></td>
              <td></td>
              <td><p>Status of the user (e.g., Active, Inactive, Deleted). </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUserType">IamUserType</a></td>
              <td></td>
              <td><p>Type of IAM user (e.g., Regular, System, Trial). </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.ListIamUsersRequest">ListIamUsersRequest</h3>
    <p>Request message for listing IAM users with pagination and sorting.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page in the result. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Page number being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the results should be sorted in ascending order. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.ListIamUsersResponse">ListIamUsersResponse</h3>
    <p>Response message for listing IAM users with pagination and sorting.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page in the result. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Page number being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates whether the results should be sorted in ascending order. </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages. </p></td>
            </tr>
          
            <tr>
              <td>iam_users</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td>repeated</td>
              <td><p>List of IAM users. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.LoginRequest">LoginRequest</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>user_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User name for the user on login request. </p></td>
            </tr>
          
            <tr>
              <td>password</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Password for user on login. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.LoginResponse">LoginResponse</h3>
    <p></p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>token</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>JWT Token to use for authorization </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.SignUpRequest">SignUpRequest</h3>
    <p>Request message for user sign-up.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUserType">IamUserType</a></td>
              <td></td>
              <td><p>Type of IAM user (e.g., Regular, System, Trial). </p></td>
            </tr>
          
            <tr>
              <td>first_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>First name of the user. </p></td>
            </tr>
          
            <tr>
              <td>last_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Last name of the user. </p></td>
            </tr>
          
            <tr>
              <td>system_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>System name for the user. </p></td>
            </tr>
          
            <tr>
              <td>username</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Email address of the user. </p></td>
            </tr>
          
            <tr>
              <td>password</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Password for the user account. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.SignUpResponse">SignUpResponse</h3>
    <p>Response message for user sign-up.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.UpdateIamUserRequest">UpdateIamUserRequest</h3>
    <p>Request message for updating an IAM user.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>Updated IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.UpdateIamUserResponse">UpdateIamUserResponse</h3>
    <p>Response message for updating an IAM user.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>Updated IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.UserExistsRequest">UserExistsRequest</h3>
    <p>Request message to check if an IAM user with a given ID exists.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the IAM user to check for existence. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.iam.UserExistsResponse">UserExistsResponse</h3>
    <p>Response message for checking if an IAM user with a given ID exists.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>iam_user</td>
              <td><a href="#cdx.opencdx.grpc.iam.IamUser">IamUser</a></td>
              <td></td>
              <td><p>IAM user information. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.iam.IamUserStatus">IamUserStatus</h3>
    <p>Enum for IAM User Status</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>IAM_USER_STATUS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Default or unspecified IAM user status.</p></td>
          </tr>
        
          <tr>
            <td>IAM_USER_STATUS_ACTIVE</td>
            <td>1</td>
            <td><p>Active IAM user status.</p></td>
          </tr>
        
          <tr>
            <td>IAM_USER_STATUS_INACTIVE</td>
            <td>2</td>
            <td><p>Inactive IAM user status.</p></td>
          </tr>
        
          <tr>
            <td>IAM_USER_STATUS_DELETED</td>
            <td>3</td>
            <td><p>Deleted IAM user status.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.iam.IamUserType">IamUserType</h3>
    <p>Enum for IAM User Types</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>IAM_USER_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Default or unspecified IAM user type.</p></td>
          </tr>
        
          <tr>
            <td>IAM_USER_TYPE_REGULAR</td>
            <td>1</td>
            <td><p>Regular IAM user type.</p></td>
          </tr>
        
          <tr>
            <td>IAM_USER_TYPE_SYSTEM</td>
            <td>2</td>
            <td><p>System IAM user type.</p></td>
          </tr>
        
          <tr>
            <td>IAM_USER_TYPE_TRIAL</td>
            <td>3</td>
            <td><p>Trial IAM user type.</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.iam.IamUserService">IamUserService</h3>
    <p>Service Definition</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>SignUp</td>
            <td><a href="#cdx.opencdx.grpc.iam.SignUpRequest">SignUpRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.SignUpResponse">SignUpResponse</a></td>
            <td><p>Sign up a new user.</p></td>
          </tr>
        
          <tr>
            <td>ListIamUsers</td>
            <td><a href="#cdx.opencdx.grpc.iam.ListIamUsersRequest">ListIamUsersRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.ListIamUsersResponse">ListIamUsersResponse</a></td>
            <td><p>List IAM users with pagination and sorting options.</p></td>
          </tr>
        
          <tr>
            <td>GetIamUser</td>
            <td><a href="#cdx.opencdx.grpc.iam.GetIamUserRequest">GetIamUserRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.GetIamUserResponse">GetIamUserResponse</a></td>
            <td><p>Get an IAM user by their unique ID.</p></td>
          </tr>
        
          <tr>
            <td>UpdateIamUser</td>
            <td><a href="#cdx.opencdx.grpc.iam.UpdateIamUserRequest">UpdateIamUserRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.UpdateIamUserResponse">UpdateIamUserResponse</a></td>
            <td><p>Update an existing IAM user.</p></td>
          </tr>
        
          <tr>
            <td>ChangePassword</td>
            <td><a href="#cdx.opencdx.grpc.iam.ChangePasswordRequest">ChangePasswordRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.ChangePasswordResponse">ChangePasswordResponse</a></td>
            <td><p>Change the password of an IAM user.</p></td>
          </tr>
        
          <tr>
            <td>DeleteIamUser</td>
            <td><a href="#cdx.opencdx.grpc.iam.DeleteIamUserRequest">DeleteIamUserRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.DeleteIamUserResponse">DeleteIamUserResponse</a></td>
            <td><p>Delete an IAM user by their unique ID.</p></td>
          </tr>
        
          <tr>
            <td>UserExists</td>
            <td><a href="#cdx.opencdx.grpc.iam.UserExistsRequest">UserExistsRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.UserExistsResponse">UserExistsResponse</a></td>
            <td><p>Check if an IAM user with a given ID exists.</p></td>
          </tr>
        
          <tr>
            <td>Login</td>
            <td><a href="#cdx.opencdx.grpc.iam.LoginRequest">LoginRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.LoginResponse">LoginResponse</a></td>
            <td><p>Authenticate IAM user.</p></td>
          </tr>
        
          <tr>
            <td>CurrentUser</td>
            <td><a href="#cdx.opencdx.grpc.iam.CurrentUserRequest">CurrentUserRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.iam.CurrentUserResponse">CurrentUserResponse</a></td>
            <td><p>Get Current User</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="inventory.proto">inventory.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.inventory.Address">Address</h3>
    <p>Address message defines the address details.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>street</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Street address </p></td>
            </tr>
          
            <tr>
              <td>city</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>City or locality </p></td>
            </tr>
          
            <tr>
              <td>postal_code</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Postal code </p></td>
            </tr>
          
            <tr>
              <td>region</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Region or state </p></td>
            </tr>
          
            <tr>
              <td>country</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Country </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.Country">Country</h3>
    <p>Country message represents a country.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Country ID </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Country name </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.CountryIdRequest">CountryIdRequest</h3>
    <p>Request message to get a country by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>country_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Country ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.DeleteResponse">DeleteResponse</h3>
    <p>Response message for delete operations.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>success</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Indicates if the delete operation was successful </p></td>
            </tr>
          
            <tr>
              <td>message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Message providing additional information about the delete operation </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.Device">Device</h3>
    <p>Device message represents a device.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Device ID </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Device type </p></td>
            </tr>
          
            <tr>
              <td>model</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Device model </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the manufacturer </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_country_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the manufacturer&#39;s country </p></td>
            </tr>
          
            <tr>
              <td>vendor_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the vendor </p></td>
            </tr>
          
            <tr>
              <td>vendor_country_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the vendor&#39;s country </p></td>
            </tr>
          
            <tr>
              <td>manufacture_date</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Date of manufacture </p></td>
            </tr>
          
            <tr>
              <td>expiry_date</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Date of expiry </p></td>
            </tr>
          
            <tr>
              <td>batch_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Batch number </p></td>
            </tr>
          
            <tr>
              <td>serial_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Serial number </p></td>
            </tr>
          
            <tr>
              <td>test_type_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>ID of the test type </p></td>
            </tr>
          
            <tr>
              <td>test_sensitivity</td>
              <td><a href="#double">double</a></td>
              <td></td>
              <td><p>Test sensitivity </p></td>
            </tr>
          
            <tr>
              <td>test_specificity</td>
              <td><a href="#double">double</a></td>
              <td></td>
              <td><p>Test specificity </p></td>
            </tr>
          
            <tr>
              <td>storage_requirements</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Storage requirements </p></td>
            </tr>
          
            <tr>
              <td>test_validation_date</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Test validation date </p></td>
            </tr>
          
            <tr>
              <td>approval_status</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Approval status </p></td>
            </tr>
          
            <tr>
              <td>url</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>URL related to the device </p></td>
            </tr>
          
            <tr>
              <td>notes</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Additional notes </p></td>
            </tr>
          
            <tr>
              <td>safety</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Safety information </p></td>
            </tr>
          
            <tr>
              <td>user_instructions</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User instructions </p></td>
            </tr>
          
            <tr>
              <td>limitations</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Limitations of the device </p></td>
            </tr>
          
            <tr>
              <td>warranty_info</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Warranty information </p></td>
            </tr>
          
            <tr>
              <td>intended_use_age</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Intended use age </p></td>
            </tr>
          
            <tr>
              <td>is_fda_authorized</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>FDA authorization status </p></td>
            </tr>
          
            <tr>
              <td>device_status</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Device status </p></td>
            </tr>
          
            <tr>
              <td>associated_software_version</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Associated software version </p></td>
            </tr>
          
            <tr>
              <td>test_case_ids</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>IDs of the test cases this device is part of </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.DeviceIdRequest">DeviceIdRequest</h3>
    <p>Request message to get a device by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>device_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Device ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</h3>
    <p>Manufacturer message represents a manufacturer.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Manufacturer ID </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer name </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_address</td>
              <td><a href="#cdx.opencdx.grpc.inventory.Address">Address</a></td>
              <td></td>
              <td><p>Manufacturer&#39;s address </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_contact</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer&#39;s contact information </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_email</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer&#39;s email </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_phone</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer&#39;s phone number </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_website</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer&#39;s website </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer&#39;s description </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_certifications</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Manufacturer&#39;s certifications </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.ManufacturerIdRequest">ManufacturerIdRequest</h3>
    <p>Request message to get a manufacturer by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>manufacturer_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Manufacturer ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.TestCase">TestCase</h3>
    <p>TestCase message represents a case or package of tests.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique ID for the test case </p></td>
            </tr>
          
            <tr>
              <td>manufacturer_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>ID of the manufacturer </p></td>
            </tr>
          
            <tr>
              <td>vendor_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>ID of the vendor </p></td>
            </tr>
          
            <tr>
              <td>device_ids</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>IDs of the devices included in the test case </p></td>
            </tr>
          
            <tr>
              <td>number_of_tests</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Number of tests included in the test case </p></td>
            </tr>
          
            <tr>
              <td>packaging_date</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Date of packaging </p></td>
            </tr>
          
            <tr>
              <td>expiry_date</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Expiry date of the test case </p></td>
            </tr>
          
            <tr>
              <td>batch_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Batch number </p></td>
            </tr>
          
            <tr>
              <td>serial_number</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Serial number of the test case </p></td>
            </tr>
          
            <tr>
              <td>storage_requirements</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Storage requirements </p></td>
            </tr>
          
            <tr>
              <td>user_instructions</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>User instructions for the test case </p></td>
            </tr>
          
            <tr>
              <td>limitations</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Limitations of the test case </p></td>
            </tr>
          
            <tr>
              <td>safety</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Safety information </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.TestCaseIdRequest">TestCaseIdRequest</h3>
    <p>Request message to get a test case by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>test_case_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Test Case ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.Vendor">Vendor</h3>
    <p>Vendor message represents a vendor.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Vendor ID </p></td>
            </tr>
          
            <tr>
              <td>vendor_name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor name </p></td>
            </tr>
          
            <tr>
              <td>vendor_address</td>
              <td><a href="#cdx.opencdx.grpc.inventory.Address">Address</a></td>
              <td></td>
              <td><p>Vendor&#39;s address </p></td>
            </tr>
          
            <tr>
              <td>vendor_contact</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor&#39;s contact information </p></td>
            </tr>
          
            <tr>
              <td>vendor_email</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor&#39;s email </p></td>
            </tr>
          
            <tr>
              <td>vendor_phone</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor&#39;s phone number </p></td>
            </tr>
          
            <tr>
              <td>vendor_website</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor&#39;s website </p></td>
            </tr>
          
            <tr>
              <td>vendor_description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor&#39;s description </p></td>
            </tr>
          
            <tr>
              <td>vendor_certifications</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Vendor&#39;s certifications </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.inventory.VendorIdRequest">VendorIdRequest</h3>
    <p>Request message to get a vendor by ID.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>vendor_id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Vendor ID </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  

  

  
    <h3 id="cdx.opencdx.grpc.inventory.CountryService">CountryService</h3>
    <p>gRPC service for managing countries.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>get_country_by_id</td>
            <td><a href="#cdx.opencdx.grpc.inventory.CountryIdRequest">CountryIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Country">Country</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>add_country</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Country">Country</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Country">Country</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>update_country</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Country">Country</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Country">Country</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>delete_country</td>
            <td><a href="#cdx.opencdx.grpc.inventory.CountryIdRequest">CountryIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeleteResponse">DeleteResponse</a></td>
            <td><p></p></td>
          </tr>
        
      </tbody>
    </table>

    
    <h3 id="cdx.opencdx.grpc.inventory.DeviceService">DeviceService</h3>
    <p>gRPC service for managing devices.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>get_device_by_id</td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeviceIdRequest">DeviceIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Device">Device</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>add_device</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Device">Device</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Device">Device</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>update_device</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Device">Device</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Device">Device</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>delete_device</td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeviceIdRequest">DeviceIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeleteResponse">DeleteResponse</a></td>
            <td><p></p></td>
          </tr>
        
      </tbody>
    </table>

    
    <h3 id="cdx.opencdx.grpc.inventory.ManufacturerService">ManufacturerService</h3>
    <p>gRPC service for managing manufacturers.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>get_manufacturer_by_id</td>
            <td><a href="#cdx.opencdx.grpc.inventory.ManufacturerIdRequest">ManufacturerIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>add_manufacturer</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>update_manufacturer</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>delete_manufacturer</td>
            <td><a href="#cdx.opencdx.grpc.inventory.ManufacturerIdRequest">ManufacturerIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeleteResponse">DeleteResponse</a></td>
            <td><p></p></td>
          </tr>
        
      </tbody>
    </table>

    
    <h3 id="cdx.opencdx.grpc.inventory.TestCaseService">TestCaseService</h3>
    <p>gRPC service for managing test cases.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>get_test_case_by_id</td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCaseIdRequest">TestCaseIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCase">TestCase</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>add_test_case</td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCase">TestCase</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCase">TestCase</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>update_test_case</td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCase">TestCase</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCase">TestCase</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>delete_test_case</td>
            <td><a href="#cdx.opencdx.grpc.inventory.TestCaseIdRequest">TestCaseIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeleteResponse">DeleteResponse</a></td>
            <td><p></p></td>
          </tr>
        
      </tbody>
    </table>

    
    <h3 id="cdx.opencdx.grpc.inventory.VendorService">VendorService</h3>
    <p>gRPC service for managing vendors.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>get_vendor_by_id</td>
            <td><a href="#cdx.opencdx.grpc.inventory.VendorIdRequest">VendorIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Vendor">Vendor</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>add_vendor</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Vendor">Vendor</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Vendor">Vendor</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>update_vendor</td>
            <td><a href="#cdx.opencdx.grpc.inventory.Vendor">Vendor</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.Vendor">Vendor</a></td>
            <td><p></p></td>
          </tr>
        
          <tr>
            <td>delete_vendor</td>
            <td><a href="#cdx.opencdx.grpc.inventory.VendorIdRequest">VendorIdRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.inventory.DeleteResponse">DeleteResponse</a></td>
            <td><p></p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="media.proto">media.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.media.CreateMediaRequest">CreateMediaRequest</h3>
    <p>Request for CreateMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.media.MediaType">MediaType</a></td>
              <td></td>
              <td><p>Type </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>File name </p></td>
            </tr>
          
            <tr>
              <td>short_description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Short Description </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description </p></td>
            </tr>
          
            <tr>
              <td>labels</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Labels </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.CreateMediaResponse">CreateMediaResponse</h3>
    <p>Response for CreateMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>media</td>
              <td><a href="#cdx.opencdx.grpc.media.Media">Media</a></td>
              <td></td>
              <td><p>The create media </p></td>
            </tr>
          
            <tr>
              <td>upload_url</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>The pre-signed URL for the upload </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.DeleteMediaRequest">DeleteMediaRequest</h3>
    <p>Request for DeleteMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique id for this media </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.DeleteMediaResponse">DeleteMediaResponse</h3>
    <p>Response for DeleteMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>media</td>
              <td><a href="#cdx.opencdx.grpc.media.Media">Media</a></td>
              <td></td>
              <td><p>The deleted media. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.GetMediaRequest">GetMediaRequest</h3>
    <p>Request for GetMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique id for this media </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.GetMediaResponse">GetMediaResponse</h3>
    <p>Response for GetMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>media</td>
              <td><a href="#cdx.opencdx.grpc.media.Media">Media</a></td>
              <td></td>
              <td><p>The requested media. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.ListMediaRequest">ListMediaRequest</h3>
    <p>Request for ListMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.ListMediaResponse">ListMediaResponse</h3>
    <p>Response for ListMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>page_size</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Size of each page. </p></td>
            </tr>
          
            <tr>
              <td>page_number</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>The page being requested. </p></td>
            </tr>
          
            <tr>
              <td>sort_ascending</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>indicating if the results should ascending. </p></td>
            </tr>
          
            <tr>
              <td>page_count</td>
              <td><a href="#int32">int32</a></td>
              <td></td>
              <td><p>Total number of available pages. </p></td>
            </tr>
          
            <tr>
              <td>templates</td>
              <td><a href="#cdx.opencdx.grpc.media.Media">Media</a></td>
              <td>repeated</td>
              <td><p>List of Media </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.Media">Media</h3>
    <p>Media data</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Identifier for this media. </p></td>
            </tr>
          
            <tr>
              <td>created_at</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td>optional</td>
              <td><p>Timestamp indicating when this media was created. </p></td>
            </tr>
          
            <tr>
              <td>updated_at</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td>optional</td>
              <td><p>Timestamp indicating when this media was last updated. </p></td>
            </tr>
          
            <tr>
              <td>organization_slug</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Organization slug </p></td>
            </tr>
          
            <tr>
              <td>workspace_slug</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Workspace slug </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>File name </p></td>
            </tr>
          
            <tr>
              <td>short_description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Short Description </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.media.MediaType">MediaType</a></td>
              <td></td>
              <td><p>Media Type </p></td>
            </tr>
          
            <tr>
              <td>labels</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Labels for media </p></td>
            </tr>
          
            <tr>
              <td>mime_type</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Mime type </p></td>
            </tr>
          
            <tr>
              <td>size</td>
              <td><a href="#uint64">uint64</a></td>
              <td></td>
              <td><p>Size in bytes </p></td>
            </tr>
          
            <tr>
              <td>location</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Media storage location (URL in S3, or GCP). This location URL won&#39;t be used for retrieving </p></td>
            </tr>
          
            <tr>
              <td>endpoint</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Media endpoint URL used for display/download </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#cdx.opencdx.grpc.media.MediaStatus">MediaStatus</a></td>
              <td></td>
              <td><p>Status </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.MediaFilter">MediaFilter</h3>
    <p>Media Filter</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>filter</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Filter string </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.UpdateMediaRequest">UpdateMediaRequest</h3>
    <p>Request for UpdateMedia.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Unique id for this media </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>File name </p></td>
            </tr>
          
            <tr>
              <td>short_description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Short Description </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Description </p></td>
            </tr>
          
            <tr>
              <td>labels</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Labels </p></td>
            </tr>
          
            <tr>
              <td>type</td>
              <td><a href="#cdx.opencdx.grpc.media.MediaType">MediaType</a></td>
              <td></td>
              <td><p>Type </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.UpdateMediaResponse">UpdateMediaResponse</h3>
    <p>Response for UpdateMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>media</td>
              <td><a href="#cdx.opencdx.grpc.media.Media">Media</a></td>
              <td></td>
              <td><p>Unique id for this media </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.media.MediaStatus">MediaStatus</h3>
    <p>Media Status</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>MEDIA_STATUS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified or unknown status</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_STATUS_UPLOADING</td>
            <td>1</td>
            <td><p>Media is being uploaded</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_STATUS_ACTIVE</td>
            <td>2</td>
            <td><p>Media is active.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_STATUS_DELETED</td>
            <td>3</td>
            <td><p>Media is being deleted.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_STATUS_ERROR</td>
            <td>4</td>
            <td><p>Error with media.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.media.MediaType">MediaType</h3>
    <p>Media Type</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>MEDIA_TYPE_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified or unknown media type</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_TYPE_IMAGE</td>
            <td>1</td>
            <td><p>Media is image.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_TYPE_VIDEO</td>
            <td>2</td>
            <td><p>Media is a video.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_TYPE_ARCHIVE</td>
            <td>3</td>
            <td><p>Media is an archive.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_TYPE_INTEGRATION</td>
            <td>4</td>
            <td><p>Media is an integraiton</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_TYPE_BYTE_ARRAY</td>
            <td>5</td>
            <td><p>Media is a data stream</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.media.MediaService">MediaService</h3>
    <p>Media Service interface</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>CreateMedia</td>
            <td><a href="#cdx.opencdx.grpc.media.CreateMediaRequest">CreateMediaRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.media.CreateMediaResponse">CreateMediaResponse</a></td>
            <td><p>Method to create media</p></td>
          </tr>
        
          <tr>
            <td>ListMedia</td>
            <td><a href="#cdx.opencdx.grpc.media.ListMediaRequest">ListMediaRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.media.ListMediaResponse">ListMediaResponse</a></td>
            <td><p>Method to list media</p></td>
          </tr>
        
          <tr>
            <td>GetMedia</td>
            <td><a href="#cdx.opencdx.grpc.media.GetMediaRequest">GetMediaRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.media.GetMediaResponse">GetMediaResponse</a></td>
            <td><p>Method to get media</p></td>
          </tr>
        
          <tr>
            <td>UpdateMedia</td>
            <td><a href="#cdx.opencdx.grpc.media.UpdateMediaRequest">UpdateMediaRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.media.UpdateMediaResponse">UpdateMediaResponse</a></td>
            <td><p>Method to update media</p></td>
          </tr>
        
          <tr>
            <td>DeleteMedia</td>
            <td><a href="#cdx.opencdx.grpc.media.DeleteMediaRequest">DeleteMediaRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.media.DeleteMediaResponse">DeleteMediaResponse</a></td>
            <td><p>Method to delete media</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="media_preprocessor.proto">media_preprocessor.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.media.MediaPreprocessor">MediaPreprocessor</h3>
    <p>Media Preprocessor data</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>id</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
            <tr>
              <td>created_at</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>When this record was created </p></td>
            </tr>
          
            <tr>
              <td>creator</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Who created this record </p></td>
            </tr>
          
            <tr>
              <td>updated_at</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>When was the last time this record was modified </p></td>
            </tr>
          
            <tr>
              <td>modifier</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>Who was the last modifier of this record </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.PreprocessMediaRequest">PreprocessMediaRequest</h3>
    <p>Request to PreprocessMedia.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>check</td>
              <td><a href="#bool">bool</a></td>
              <td></td>
              <td><p>Boolean indicating to check media </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.media.PreprocessMediaResponse">PreprocessMediaResponse</h3>
    <p>Response to PreprocessMedia</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>media_preprocessor</td>
              <td><a href="#cdx.opencdx.grpc.media.MediaPreprocessor">MediaPreprocessor</a></td>
              <td></td>
              <td><p>The media preprocessor. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  
    <h3 id="cdx.opencdx.grpc.media.MediaPreprocessorCommands">MediaPreprocessorCommands</h3>
    <p>Media Preprocessor Commands</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_COMMANDS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified or unknown command.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_COMMANDS_PREPROCESS</td>
            <td>1</td>
            <td><p>Preprocess media.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.media.MediaPreprocessorEvents">MediaPreprocessorEvents</h3>
    <p>Media Preprocessor Events</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_EVENTS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified or unknown event.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_EVENTS_PREPROCESSED</td>
            <td>1</td>
            <td><p>Media has been preprocessed.</p></td>
          </tr>
        
      </tbody>
    </table>
  
    <h3 id="cdx.opencdx.grpc.media.MediaPreprocessorStatus">MediaPreprocessorStatus</h3>
    <p>Media Preprocessor Status</p>
    <table className="enum-table">
      <thead>
        <tr><td>Name</td><td>Number</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_STATUS_UNSPECIFIED</td>
            <td>0</td>
            <td><p>Unspecified or unknown status</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_STATUS_PREPROCESSING</td>
            <td>1</td>
            <td><p>Media is being preprocessing.</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_STATUS_ACTIVE</td>
            <td>2</td>
            <td><p>Preprocessor is active</p></td>
          </tr>
        
          <tr>
            <td>MEDIA_PREPROCESSOR_STATUS_ERROR</td>
            <td>3</td>
            <td><p>Error with Preprocessor</p></td>
          </tr>
        
      </tbody>
    </table>
  

  

  
    <h3 id="cdx.opencdx.grpc.media.MediaPreprocessorService">MediaPreprocessorService</h3>
    <p>Media Preprocessor Service interface</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>PreprocessMedia</td>
            <td><a href="#cdx.opencdx.grpc.media.PreprocessMediaRequest">PreprocessMediaRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.media.PreprocessMediaResponse">PreprocessMediaResponse</a></td>
            <td><p>Method to preprocess media</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="routine.proto">routine.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</h3>
    <p>Entities for the Clinical Protocol Execution</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>execution_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for each execution of a clinical protocol </p></td>
            </tr>
          
            <tr>
              <td>routine_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Reference to the routine associated with the clinical protocol execution </p></td>
            </tr>
          
            <tr>
              <td>protocol_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Reference to the specific clinical protocol being executed </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Status of the protocol execution (e.g., not started, in progress, completed) </p></td>
            </tr>
          
            <tr>
              <td>start_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp when the execution of the protocol began </p></td>
            </tr>
          
            <tr>
              <td>end_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp when the execution was completed </p></td>
            </tr>
          
            <tr>
              <td>results</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Details about the results or outcomes of the protocol execution </p></td>
            </tr>
          
            <tr>
              <td>assigned_medical_staff</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Information about the medical professionals responsible for the protocol execution </p></td>
            </tr>
          
            <tr>
              <td>steps</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Representation of the individual steps or actions within the protocol </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</h3>
    <p>Entities for the Delivery Tracking System</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>delivery_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for each tracked delivery </p></td>
            </tr>
          
            <tr>
              <td>order_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Identifier linking the delivery to an order or request </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Status of the delivery (e.g., in transit, delivered) </p></td>
            </tr>
          
            <tr>
              <td>start_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp when the delivery process started </p></td>
            </tr>
          
            <tr>
              <td>end_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp when the delivery was completed </p></td>
            </tr>
          
            <tr>
              <td>current_location</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Information about the current location of the delivery </p></td>
            </tr>
          
            <tr>
              <td>recipient</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Information about the recipient or receiver of the delivery </p></td>
            </tr>
          
            <tr>
              <td>delivery_items</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Details of the items being delivered </p></td>
            </tr>
          
            <tr>
              <td>assigned_courier</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Courier or delivery person responsible for the delivery </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</h3>
    <p>Diagnosis Data</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>diagnosis_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for diagnoses </p></td>
            </tr>
          
            <tr>
              <td>diagnosis_code</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Diagnosis code </p></td>
            </tr>
          
            <tr>
              <td>diagnosis_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp of the diagnosis </p></td>
            </tr>
          
            <tr>
              <td>matched_value_set</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Value set that triggered the eICR </p></td>
            </tr>
          
            <tr>
              <td>related_entities</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Related entities (e.g., Patients, Practitioners) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.LabOrder">LabOrder</h3>
    <p>Entities for Trigger Conditions</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>lab_order_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for lab orders </p></td>
            </tr>
          
            <tr>
              <td>test_name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the lab test </p></td>
            </tr>
          
            <tr>
              <td>order_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp of the lab order </p></td>
            </tr>
          
            <tr>
              <td>matched_value_set</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Value set that triggered the eICR </p></td>
            </tr>
          
            <tr>
              <td>related_entities</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Related entities (e.g., Patients, Practitioners) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.LabResult">LabResult</h3>
    <p>Lab Result data</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>result_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for lab results </p></td>
            </tr>
          
            <tr>
              <td>result_value</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Value of the lab result </p></td>
            </tr>
          
            <tr>
              <td>result_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp of the lab result </p></td>
            </tr>
          
            <tr>
              <td>matched_value_set</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Value set that triggered the eICR </p></td>
            </tr>
          
            <tr>
              <td>related_entities</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Related entities (e.g., Patients, Lab Facilities) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.Medication">Medication</h3>
    <p>Medication data</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>medication_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for medications </p></td>
            </tr>
          
            <tr>
              <td>medication_name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name of the medication </p></td>
            </tr>
          
            <tr>
              <td>administration_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp of medication administration </p></td>
            </tr>
          
            <tr>
              <td>matched_value_set</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Value set that triggered the eICR </p></td>
            </tr>
          
            <tr>
              <td>related_entities</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Related entities (e.g., Patients, Practitioners) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.Routine">Routine</h3>
    <p>Entities for the Routine System</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>routine_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for each routine process </p></td>
            </tr>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Name or title for the routine process </p></td>
            </tr>
          
            <tr>
              <td>description</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Additional details describing the nature of the routine </p></td>
            </tr>
          
            <tr>
              <td>status</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Status indicating the state of the routine (e.g., in progress, completed) </p></td>
            </tr>
          
            <tr>
              <td>creation_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp for when the routine was created </p></td>
            </tr>
          
            <tr>
              <td>last_updated_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp for the last update to the routine </p></td>
            </tr>
          
            <tr>
              <td>assigned_user</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>User or team responsible for the routine </p></td>
            </tr>
          
            <tr>
              <td>associated_protocols</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Reference to clinical protocols linked to the routine </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</h3>
    <p>Suspected Diagnosis data</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>suspected_diagnosis_id</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Unique identifier for suspected diagnoses </p></td>
            </tr>
          
            <tr>
              <td>diagnosis_code</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Diagnosis code for suspected diagnoses </p></td>
            </tr>
          
            <tr>
              <td>diagnosis_datetime</td>
              <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
              <td></td>
              <td><p>Timestamp of the suspected diagnosis </p></td>
            </tr>
          
            <tr>
              <td>matched_value_set</td>
              <td><a href="#string">string</a></td>
              <td>optional</td>
              <td><p>Value set that triggered the eICR </p></td>
            </tr>
          
            <tr>
              <td>related_entities</td>
              <td><a href="#string">string</a></td>
              <td>repeated</td>
              <td><p>Related entities (e.g., Patients, Practitioners) </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  

  

  
    <h3 id="cdx.opencdx.grpc.routine.RoutineSystemService">RoutineSystemService</h3>
    <p>gRPC services and operations</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>CreateRoutine</td>
            <td><a href="#cdx.opencdx.grpc.routine.Routine">Routine</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.Routine">Routine</a></td>
            <td><p>Create a new routine entity</p></td>
          </tr>
        
          <tr>
            <td>GetRoutineByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.Routine">Routine</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.Routine">Routine</a></td>
            <td><p>Retrieve information about a routine entity by ID</p></td>
          </tr>
        
          <tr>
            <td>CreateDeliveryTracking</td>
            <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</a></td>
            <td><p>Create a new delivery tracking entity</p></td>
          </tr>
        
          <tr>
            <td>GetDeliveryTrackingByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</a></td>
            <td><p>Retrieve information about a delivery tracking entity by ID</p></td>
          </tr>
        
          <tr>
            <td>CreateClinicalProtocolExecution</td>
            <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</a></td>
            <td><p>Create a new clinical protocol execution entity</p></td>
          </tr>
        
          <tr>
            <td>GetClinicalProtocolExecutionByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</a></td>
            <td><p>Retrieve information about a clinical protocol execution entity by ID</p></td>
          </tr>
        
          <tr>
            <td>TriggerLabOrder</td>
            <td><a href="#cdx.opencdx.grpc.routine.LabOrder">LabOrder</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.LabOrder">LabOrder</a></td>
            <td><p>Trigger a new lab order entity</p></td>
          </tr>
        
          <tr>
            <td>GetLabOrderByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.LabOrder">LabOrder</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.LabOrder">LabOrder</a></td>
            <td><p>Retrieve information about a lab order entity by ID</p></td>
          </tr>
        
          <tr>
            <td>TriggerDiagnosis</td>
            <td><a href="#cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</a></td>
            <td><p>Trigger a new diagnosis entity</p></td>
          </tr>
        
          <tr>
            <td>GetDiagnosisByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</a></td>
            <td><p>Retrieve information about a diagnosis entity by ID</p></td>
          </tr>
        
          <tr>
            <td>TriggerSuspectedDiagnosis</td>
            <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</a></td>
            <td><p>Trigger a new suspected diagnosis entity</p></td>
          </tr>
        
          <tr>
            <td>GetSuspectedDiagnosisByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</a></td>
            <td><p>Retrieve information about a suspected diagnosis entity by ID</p></td>
          </tr>
        
          <tr>
            <td>TriggerLabResult</td>
            <td><a href="#cdx.opencdx.grpc.routine.LabResult">LabResult</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.LabResult">LabResult</a></td>
            <td><p>Trigger a new lab result entity</p></td>
          </tr>
        
          <tr>
            <td>GetLabResultByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.LabResult">LabResult</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.LabResult">LabResult</a></td>
            <td><p>Retrieve information about a lab result entity by ID</p></td>
          </tr>
        
          <tr>
            <td>TriggerMedication</td>
            <td><a href="#cdx.opencdx.grpc.routine.Medication">Medication</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.Medication">Medication</a></td>
            <td><p>Trigger a new medication entity</p></td>
          </tr>
        
          <tr>
            <td>GetMedicationByID</td>
            <td><a href="#cdx.opencdx.grpc.routine.Medication">Medication</a></td>
            <td><a href="#cdx.opencdx.grpc.routine.Medication">Medication</a></td>
            <td><p>Retrieve information about a medication entity by ID</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="tinkar.proto">tinkar.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="cdx.opencdx.grpc.tinkar.TinkarReply">TinkarReply</h3>
    <p>The response message containing the greetings</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="cdx.opencdx.grpc.tinkar.TinkarRequest">TinkarRequest</h3>
    <p>The request message containing the user name.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>name</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p> </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  

  

  
    <h3 id="cdx.opencdx.grpc.tinkar.Tinkar">Tinkar</h3>
    <p>The greeting service definition.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>sayTinkar</td>
            <td><a href="#cdx.opencdx.grpc.tinkar.TinkarRequest">TinkarRequest</a></td>
            <td><a href="#cdx.opencdx.grpc.tinkar.TinkarReply">TinkarReply</a></td>
            <td><p>Sends a greeting</p></td>
          </tr>
        
      </tbody>
    </table>

    

  
  <div className="file-heading">
    <h2 id="unit_test.proto">unit_test.proto</h2><a href="#title">Top</a>
  </div>
  <p></p>

  
    <h3 id="health.safe.api.opencdx.grpc.unit.test.UnitTestRequest">UnitTestRequest</h3>
    <p>The request message containing the user name.</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>This is the description of message attribute of the UnitTestRequest message. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  
    <h3 id="health.safe.api.opencdx.grpc.unit.test.UnitTestResponse">UnitTestResponse</h3>
    <p>The response message containing the greetings</p>

    
      <table className="field-table">
        <thead>
          <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
        </thead>
        <tbody>
          
            <tr>
              <td>message</td>
              <td><a href="#string">string</a></td>
              <td></td>
              <td><p>This is the description of message attribute of the UnitTestResponse message. </p></td>
            </tr>
          
        </tbody>
      </table>

      

    
  

  

  

  
    <h3 id="health.safe.api.opencdx.grpc.unit.test.UnitTest">UnitTest</h3>
    <p>This proto is for use in unit testing gRPC Services and messages through out</p><p>the OpenCDx project.</p>
    <table className="enum-table">
      <thead>
        <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
      </thead>
      <tbody>
        
          <tr>
            <td>TestA</td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestRequest">UnitTestRequest</a></td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestResponse">UnitTestResponse</a></td>
            <td><p>Used to Sends a greeting. TestB rpc takes UnitTestRequest as a input and return UnitTestResponse message.</p></td>
          </tr>
        
          <tr>
            <td>TestB</td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestRequest">UnitTestRequest</a></td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestResponse">UnitTestResponse</a></td>
            <td><p>Used to Sends a greeting. TestB rpc takes UnitTestRequest as a input and return UnitTestResponse message.</p></td>
          </tr>
        
          <tr>
            <td>TestC</td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestRequest">UnitTestRequest</a></td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestResponse">UnitTestResponse</a></td>
            <td><p>Used to Sends a greeting. TestB rpc takes UnitTestRequest as a input and return UnitTestResponse message.</p></td>
          </tr>
        
          <tr>
            <td>TestD</td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestRequest">UnitTestRequest</a></td>
            <td><a href="#health.safe.api.opencdx.grpc.unit.test.UnitTestResponse">UnitTestResponse</a></td>
            <td><p>Used to Sends a greeting. TestB rpc takes UnitTestRequest as a input and return UnitTestResponse message.</p></td>
          </tr>
        
      </tbody>
    </table>

    


<h2 id="scalar-value-types">Scalar Value Types</h2>
<table className="scalar-value-types-table">
  <thead>
    <tr><td>.proto Type</td><td>Notes</td><td>C++</td><td>Java</td><td>Python</td><td>Go</td><td>C#</td><td>PHP</td><td>Ruby</td></tr>
  </thead>
  <tbody>
    
      <tr id="double">
        <td>double</td>
        <td></td>
        <td>double</td>
        <td>double</td>
        <td>float</td>
        <td>float64</td>
        <td>double</td>
        <td>float</td>
        <td>Float</td>
      </tr>
    
      <tr id="float">
        <td>float</td>
        <td></td>
        <td>float</td>
        <td>float</td>
        <td>float</td>
        <td>float32</td>
        <td>float</td>
        <td>float</td>
        <td>Float</td>
      </tr>
    
      <tr id="int32">
        <td>int32</td>
        <td>Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint32 instead.</td>
        <td>int32</td>
        <td>int</td>
        <td>int</td>
        <td>int32</td>
        <td>int</td>
        <td>integer</td>
        <td>Bignum or Fixnum (as required)</td>
      </tr>
    
      <tr id="int64">
        <td>int64</td>
        <td>Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint64 instead.</td>
        <td>int64</td>
        <td>long</td>
        <td>int/long</td>
        <td>int64</td>
        <td>long</td>
        <td>integer/string</td>
        <td>Bignum</td>
      </tr>
    
      <tr id="uint32">
        <td>uint32</td>
        <td>Uses variable-length encoding.</td>
        <td>uint32</td>
        <td>int</td>
        <td>int/long</td>
        <td>uint32</td>
        <td>uint</td>
        <td>integer</td>
        <td>Bignum or Fixnum (as required)</td>
      </tr>
    
      <tr id="uint64">
        <td>uint64</td>
        <td>Uses variable-length encoding.</td>
        <td>uint64</td>
        <td>long</td>
        <td>int/long</td>
        <td>uint64</td>
        <td>ulong</td>
        <td>integer/string</td>
        <td>Bignum or Fixnum (as required)</td>
      </tr>
    
      <tr id="sint32">
        <td>sint32</td>
        <td>Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int32s.</td>
        <td>int32</td>
        <td>int</td>
        <td>int</td>
        <td>int32</td>
        <td>int</td>
        <td>integer</td>
        <td>Bignum or Fixnum (as required)</td>
      </tr>
    
      <tr id="sint64">
        <td>sint64</td>
        <td>Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int64s.</td>
        <td>int64</td>
        <td>long</td>
        <td>int/long</td>
        <td>int64</td>
        <td>long</td>
        <td>integer/string</td>
        <td>Bignum</td>
      </tr>
    
      <tr id="fixed32">
        <td>fixed32</td>
        <td>Always four bytes. More efficient than uint32 if values are often greater than 2^28.</td>
        <td>uint32</td>
        <td>int</td>
        <td>int</td>
        <td>uint32</td>
        <td>uint</td>
        <td>integer</td>
        <td>Bignum or Fixnum (as required)</td>
      </tr>
    
      <tr id="fixed64">
        <td>fixed64</td>
        <td>Always eight bytes. More efficient than uint64 if values are often greater than 2^56.</td>
        <td>uint64</td>
        <td>long</td>
        <td>int/long</td>
        <td>uint64</td>
        <td>ulong</td>
        <td>integer/string</td>
        <td>Bignum</td>
      </tr>
    
      <tr id="sfixed32">
        <td>sfixed32</td>
        <td>Always four bytes.</td>
        <td>int32</td>
        <td>int</td>
        <td>int</td>
        <td>int32</td>
        <td>int</td>
        <td>integer</td>
        <td>Bignum or Fixnum (as required)</td>
      </tr>
    
      <tr id="sfixed64">
        <td>sfixed64</td>
        <td>Always eight bytes.</td>
        <td>int64</td>
        <td>long</td>
        <td>int/long</td>
        <td>int64</td>
        <td>long</td>
        <td>integer/string</td>
        <td>Bignum</td>
      </tr>
    
      <tr id="bool">
        <td>bool</td>
        <td></td>
        <td>bool</td>
        <td>boolean</td>
        <td>boolean</td>
        <td>bool</td>
        <td>bool</td>
        <td>boolean</td>
        <td>TrueclassName/FalseclassName</td>
      </tr>
    
      <tr id="string">
        <td>string</td>
        <td>A string must always contain UTF-8 encoded or 7-bit ASCII text.</td>
        <td>string</td>
        <td>String</td>
        <td>str/unicode</td>
        <td>string</td>
        <td>string</td>
        <td>string</td>
        <td>String (UTF-8)</td>
      </tr>
    
      <tr id="bytes">
        <td>bytes</td>
        <td>May contain any arbitrary sequence of bytes.</td>
        <td>string</td>
        <td>ByteString</td>
        <td>str</td>
        <td>[]byte</td>
        <td>ByteString</td>
        <td>string</td>
        <td>String (ASCII-8BIT)</td>
      </tr>
    
  </tbody>
</table>

          
        </div>
    );
};

export default Proto;
