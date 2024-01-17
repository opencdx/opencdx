// ==============================|| Proto Doc PAGE ||============================== //

import React from 'react';

const ProtoDoc = () => {
    const protodocHTML = `
  <!DOCTYPE html>

  <html>
    <head>
      <title>Protocol Documentation</title>
      <meta charset="UTF-8">
      <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Ubuntu:400,700,400italic"/>

  
      
      <link rel="stylesheet" type="text/css" href="stylesheet.css"/>
    </head>
  
    <body>
  
      <h1 id="title">Protocol Documentation</h1>
  
      <h2>Table of Contents</h2>
  
      <div id="toc-container">
        <ul id="toc">
          
            
            <li>
              <a href="#anf_statement.proto">anf_statement.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.ANFStatement"><span class="badge">M</span>ANFStatement</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.AssociatedStatement"><span class="badge">M</span>AssociatedStatement</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Circumstance"><span class="badge">M</span>Circumstance</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Identifier"><span class="badge">M</span>Identifier</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.LogicalExpression"><span class="badge">M</span>LogicalExpression</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Measure"><span class="badge">M</span>Measure</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.NarrativeCircumstance"><span class="badge">M</span>NarrativeCircumstance</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Participant"><span class="badge">M</span>Participant</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.PerformanceCircumstance"><span class="badge">M</span>PerformanceCircumstance</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Practitioner"><span class="badge">M</span>Practitioner</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Reference"><span class="badge">M</span>Reference</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Repetition"><span class="badge">M</span>Repetition</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.RequestCircumstance"><span class="badge">M</span>RequestCircumstance</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Timing"><span class="badge">M</span>Timing</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.HealthRisk"><span class="badge">E</span>HealthRisk</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.NormalRange"><span class="badge">E</span>NormalRange</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.ResultStateStatus"><span class="badge">E</span>ResultStateStatus</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.Status"><span class="badge">E</span>Status</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.anf.ANFService"><span class="badge">S</span>ANFService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#audit.proto">audit.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.Actor"><span class="badge">M</span>Actor</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AuditEntity"><span class="badge">M</span>AuditEntity</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AuditEvent"><span class="badge">M</span>AuditEvent</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AuditSource"><span class="badge">M</span>AuditSource</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AuditStatus"><span class="badge">M</span>AuditStatus</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.DataObject"><span class="badge">M</span>DataObject</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AgentType"><span class="badge">E</span>AgentType</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AuditEventType"><span class="badge">E</span>AuditEventType</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.SensitivityLevel"><span class="badge">E</span>SensitivityLevel</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.audit.AuditService"><span class="badge">S</span>AuditService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#classification.proto">classification.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.ClassificationRequest"><span class="badge">M</span>ClassificationRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.ClassificationResponse"><span class="badge">M</span>ClassificationResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.Interpretation"><span class="badge">M</span>Interpretation</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.Interpretation.NegativeEntry"><span class="badge">M</span>Interpretation.NegativeEntry</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.Interpretation.PositiveEntry"><span class="badge">M</span>Interpretation.PositiveEntry</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.Location"><span class="badge">M</span>Location</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.Symptom"><span class="badge">M</span>Symptom</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.TestKit"><span class="badge">M</span>TestKit</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.UserAnswer"><span class="badge">M</span>UserAnswer</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.SeverityLevel"><span class="badge">E</span>SeverityLevel</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.SpecimenType"><span class="badge">E</span>SpecimenType</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.classification.ClassificationService"><span class="badge">S</span>ClassificationService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#codeable.proto">codeable.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.codeable.CodeableConcept"><span class="badge">M</span>CodeableConcept</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.codeable.Coding"><span class="badge">M</span>Coding</a>
                  </li>
                
                
                
                
              </ul>
            </li>
          
            
            <li>
              <a href="#common.proto">common.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.Address"><span class="badge">M</span>Address</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.ContactInfo"><span class="badge">M</span>ContactInfo</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.Country"><span class="badge">M</span>Country</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.EmailAddress"><span class="badge">M</span>EmailAddress</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.FullName"><span class="badge">M</span>FullName</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.PhoneNumber"><span class="badge">M</span>PhoneNumber</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.AddressPurpose"><span class="badge">E</span>AddressPurpose</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.EmailType"><span class="badge">E</span>EmailType</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.PhoneType"><span class="badge">E</span>PhoneType</a>
                  </li>
                
                
                
              </ul>
            </li>
          
            
            <li>
              <a href="#communications.proto">communications.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.Attachment"><span class="badge">M</span>Attachment</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.CommunicationAuditRecord"><span class="badge">M</span>CommunicationAuditRecord</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.EmailTemplate"><span class="badge">M</span>EmailTemplate</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.EmailTemplateListRequest"><span class="badge">M</span>EmailTemplateListRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.EmailTemplateListResponse"><span class="badge">M</span>EmailTemplateListResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.Notification"><span class="badge">M</span>Notification</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.Notification.CustomDataEntry"><span class="badge">M</span>Notification.CustomDataEntry</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.Notification.VariablesEntry"><span class="badge">M</span>Notification.VariablesEntry</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.NotificationEvent"><span class="badge">M</span>NotificationEvent</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.NotificationEventListRequest"><span class="badge">M</span>NotificationEventListRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.NotificationEventListResponse"><span class="badge">M</span>NotificationEventListResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.SMSTemplate"><span class="badge">M</span>SMSTemplate</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.SMSTemplateListRequest"><span class="badge">M</span>SMSTemplateListRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.SMSTemplateListResponse"><span class="badge">M</span>SMSTemplateListResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.SuccessResponse"><span class="badge">M</span>SuccessResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.TemplateRequest"><span class="badge">M</span>TemplateRequest</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.NotificationPriority"><span class="badge">E</span>NotificationPriority</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.NotificationStatus"><span class="badge">E</span>NotificationStatus</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.TemplateType"><span class="badge">E</span>TemplateType</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.communication.CommunicationService"><span class="badge">S</span>CommunicationService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#connected_test.proto">connected_test.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.BasicInfo"><span class="badge">M</span>BasicInfo</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.ConnectedTest"><span class="badge">M</span>ConnectedTest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.ConnectedTestListByNHIDRequest"><span class="badge">M</span>ConnectedTestListByNHIDRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.ConnectedTestListByNHIDResponse"><span class="badge">M</span>ConnectedTestListByNHIDResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.ConnectedTestListRequest"><span class="badge">M</span>ConnectedTestListRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.ConnectedTestListResponse"><span class="badge">M</span>ConnectedTestListResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.Metadata"><span class="badge">M</span>Metadata</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.OrderInfo"><span class="badge">M</span>OrderInfo</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.OrderableTest"><span class="badge">M</span>OrderableTest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.OrderableTestResult"><span class="badge">M</span>OrderableTestResult</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.PaymentDetails"><span class="badge">M</span>PaymentDetails</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.ProviderInfo"><span class="badge">M</span>ProviderInfo</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.StatusMessageAction"><span class="badge">M</span>StatusMessageAction</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestDetails"><span class="badge">M</span>TestDetails</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestIdRequest"><span class="badge">M</span>TestIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestKitMetadata"><span class="badge">M</span>TestKitMetadata</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestNotes"><span class="badge">M</span>TestNotes</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestSubmissionResponse"><span class="badge">M</span>TestSubmissionResponse</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.LabTestType"><span class="badge">E</span>LabTestType</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.SpecimenType"><span class="badge">E</span>SpecimenType</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestClassification"><span class="badge">E</span>TestClassification</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.TestFormat"><span class="badge">E</span>TestFormat</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.connected.HealthcareService"><span class="badge">S</span>HealthcareService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#helloworld.proto">helloworld.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.helloworld.HelloRequest"><span class="badge">M</span>HelloRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.helloworld.HelloResponse"><span class="badge">M</span>HelloResponse</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.helloworld.Greeter"><span class="badge">S</span>Greeter</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#iam_organization.proto">iam_organization.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.CreateOrganizationRequest"><span class="badge">M</span>CreateOrganizationRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.CreateOrganizationResponse"><span class="badge">M</span>CreateOrganizationResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.CreateWorkspaceRequest"><span class="badge">M</span>CreateWorkspaceRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.CreateWorkspaceResponse"><span class="badge">M</span>CreateWorkspaceResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.Department"><span class="badge">M</span>Department</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.Employee"><span class="badge">M</span>Employee</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.Empty"><span class="badge">M</span>Empty</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdRequest"><span class="badge">M</span>GetOrganizationDetailsByIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdResponse"><span class="badge">M</span>GetOrganizationDetailsByIdResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdRequest"><span class="badge">M</span>GetWorkspaceDetailsByIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.GetWorkspaceDetailsByIdResponse"><span class="badge">M</span>GetWorkspaceDetailsByIdResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.ListOrganizationsResponse"><span class="badge">M</span>ListOrganizationsResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.ListWorkspacesResponse"><span class="badge">M</span>ListWorkspacesResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.Organization"><span class="badge">M</span>Organization</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.UpdateOrganizationRequest"><span class="badge">M</span>UpdateOrganizationRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.UpdateOrganizationResponse"><span class="badge">M</span>UpdateOrganizationResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.UpdateWorkspaceRequest"><span class="badge">M</span>UpdateWorkspaceRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.UpdateWorkspaceResponse"><span class="badge">M</span>UpdateWorkspaceResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.Workspace"><span class="badge">M</span>Workspace</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.OrganizationService"><span class="badge">S</span>OrganizationService</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.organization.WorkspaceService"><span class="badge">S</span>WorkspaceService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#iam_profile.proto">iam_profile.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.DateOfBirth"><span class="badge">M</span>DateOfBirth</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.DeleteUserProfileRequest"><span class="badge">M</span>DeleteUserProfileRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.DeleteUserProfileResponse"><span class="badge">M</span>DeleteUserProfileResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Demographics"><span class="badge">M</span>Demographics</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Education"><span class="badge">M</span>Education</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.EducationEntry"><span class="badge">M</span>EducationEntry</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.EmergencyContact"><span class="badge">M</span>EmergencyContact</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.EmployeeIdentity"><span class="badge">M</span>EmployeeIdentity</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.KnownAllergy"><span class="badge">M</span>KnownAllergy</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Medication"><span class="badge">M</span>Medication</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Pharmacy"><span class="badge">M</span>Pharmacy</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.PlaceOfBirth"><span class="badge">M</span>PlaceOfBirth</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Preferences"><span class="badge">M</span>Preferences</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.SystemSettings"><span class="badge">M</span>SystemSettings</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.UpdateUserProfileRequest"><span class="badge">M</span>UpdateUserProfileRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.UpdateUserProfileResponse"><span class="badge">M</span>UpdateUserProfileResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.UserProfile"><span class="badge">M</span>UserProfile</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.UserProfileRequest"><span class="badge">M</span>UserProfileRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.UserProfileResponse"><span class="badge">M</span>UserProfileResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Vaccine"><span class="badge">M</span>Vaccine</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.Gender"><span class="badge">E</span>Gender</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.profile.UserProfileService"><span class="badge">S</span>UserProfileService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#iam_user.proto">iam_user.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.ChangePasswordRequest"><span class="badge">M</span>ChangePasswordRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.ChangePasswordResponse"><span class="badge">M</span>ChangePasswordResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.CurrentUserRequest"><span class="badge">M</span>CurrentUserRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.CurrentUserResponse"><span class="badge">M</span>CurrentUserResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.DeleteIamUserRequest"><span class="badge">M</span>DeleteIamUserRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.DeleteIamUserResponse"><span class="badge">M</span>DeleteIamUserResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.GetIamUserRequest"><span class="badge">M</span>GetIamUserRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.GetIamUserResponse"><span class="badge">M</span>GetIamUserResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.IamUser"><span class="badge">M</span>IamUser</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.ListIamUsersRequest"><span class="badge">M</span>ListIamUsersRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.ListIamUsersResponse"><span class="badge">M</span>ListIamUsersResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.LoginRequest"><span class="badge">M</span>LoginRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.LoginResponse"><span class="badge">M</span>LoginResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.SignUpRequest"><span class="badge">M</span>SignUpRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.SignUpResponse"><span class="badge">M</span>SignUpResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.UpdateIamUserRequest"><span class="badge">M</span>UpdateIamUserRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.UpdateIamUserResponse"><span class="badge">M</span>UpdateIamUserResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.UserExistsRequest"><span class="badge">M</span>UserExistsRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.UserExistsResponse"><span class="badge">M</span>UserExistsResponse</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.IamUserStatus"><span class="badge">E</span>IamUserStatus</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.IamUserType"><span class="badge">E</span>IamUserType</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.iam.IamUserService"><span class="badge">S</span>IamUserService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#inventory.proto">inventory.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.CountryIdRequest"><span class="badge">M</span>CountryIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.DeleteResponse"><span class="badge">M</span>DeleteResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.Device"><span class="badge">M</span>Device</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.DeviceIdRequest"><span class="badge">M</span>DeviceIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.Manufacturer"><span class="badge">M</span>Manufacturer</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.ManufacturerIdRequest"><span class="badge">M</span>ManufacturerIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.TestCase"><span class="badge">M</span>TestCase</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.TestCaseIdRequest"><span class="badge">M</span>TestCaseIdRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.Vendor"><span class="badge">M</span>Vendor</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.VendorIdRequest"><span class="badge">M</span>VendorIdRequest</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.CountryService"><span class="badge">S</span>CountryService</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.DeviceService"><span class="badge">S</span>DeviceService</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.ManufacturerService"><span class="badge">S</span>ManufacturerService</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.TestCaseService"><span class="badge">S</span>TestCaseService</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.inventory.VendorService"><span class="badge">S</span>VendorService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#media.proto">media.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.CreateMediaRequest"><span class="badge">M</span>CreateMediaRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.CreateMediaResponse"><span class="badge">M</span>CreateMediaResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.DeleteMediaRequest"><span class="badge">M</span>DeleteMediaRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.DeleteMediaResponse"><span class="badge">M</span>DeleteMediaResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.GetMediaRequest"><span class="badge">M</span>GetMediaRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.GetMediaResponse"><span class="badge">M</span>GetMediaResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.ListMediaRequest"><span class="badge">M</span>ListMediaRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.ListMediaResponse"><span class="badge">M</span>ListMediaResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.Media"><span class="badge">M</span>Media</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaFilter"><span class="badge">M</span>MediaFilter</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.UpdateMediaRequest"><span class="badge">M</span>UpdateMediaRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.UpdateMediaResponse"><span class="badge">M</span>UpdateMediaResponse</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaStatus"><span class="badge">E</span>MediaStatus</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaType"><span class="badge">E</span>MediaType</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaService"><span class="badge">S</span>MediaService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#media_preprocessor.proto">media_preprocessor.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaPreprocessor"><span class="badge">M</span>MediaPreprocessor</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.PreprocessMediaRequest"><span class="badge">M</span>PreprocessMediaRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.PreprocessMediaResponse"><span class="badge">M</span>PreprocessMediaResponse</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaPreprocessorCommands"><span class="badge">E</span>MediaPreprocessorCommands</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaPreprocessorEvents"><span class="badge">E</span>MediaPreprocessorEvents</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaPreprocessorStatus"><span class="badge">E</span>MediaPreprocessorStatus</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.media.MediaPreprocessorService"><span class="badge">S</span>MediaPreprocessorService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#predictor.proto">predictor.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.predictor.PredictorInput"><span class="badge">M</span>PredictorInput</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.predictor.PredictorOutput"><span class="badge">M</span>PredictorOutput</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.predictor.PredictorRequest"><span class="badge">M</span>PredictorRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.predictor.PredictorResponse"><span class="badge">M</span>PredictorResponse</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.predictor.NeuralPredictorService"><span class="badge">S</span>NeuralPredictorService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#protector.proto">protector.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.AnomalyDetectionData"><span class="badge">M</span>AnomalyDetectionData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.AnomalyDetectionDataRequest"><span class="badge">M</span>AnomalyDetectionDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.AuthorizationControlData"><span class="badge">M</span>AuthorizationControlData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.AuthorizationControlDataRequest"><span class="badge">M</span>AuthorizationControlDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.PrivacyProtectionData"><span class="badge">M</span>PrivacyProtectionData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.PrivacyProtectionDataRequest"><span class="badge">M</span>PrivacyProtectionDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.RealTimeMonitoringData"><span class="badge">M</span>RealTimeMonitoringData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.RealTimeMonitoringDataRequest"><span class="badge">M</span>RealTimeMonitoringDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.SecurityResponse"><span class="badge">M</span>SecurityResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisData"><span class="badge">M</span>UserBehaviorAnalysisData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.UserBehaviorAnalysisDataRequest"><span class="badge">M</span>UserBehaviorAnalysisDataRequest</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.neural.protector.NeuralProtectorService"><span class="badge">S</span>NeuralProtectorService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#provider.proto">provider.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.BasicInfo"><span class="badge">M</span>BasicInfo</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.DeleteProviderRequest"><span class="badge">M</span>DeleteProviderRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.DeleteProviderResponse"><span class="badge">M</span>DeleteProviderResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.EditProviderRequest"><span class="badge">M</span>EditProviderRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.EditProviderResponse"><span class="badge">M</span>EditProviderResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.GetProviderRequest"><span class="badge">M</span>GetProviderRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.GetProviderResponse"><span class="badge">M</span>GetProviderResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.Identifier"><span class="badge">M</span>Identifier</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.ListProvidersRequest"><span class="badge">M</span>ListProvidersRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.ListProvidersResponse"><span class="badge">M</span>ListProvidersResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.LoadProviderRequest"><span class="badge">M</span>LoadProviderRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.LoadProviderResponse"><span class="badge">M</span>LoadProviderResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.Provider"><span class="badge">M</span>Provider</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.Taxonomy"><span class="badge">M</span>Taxonomy</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.ProviderStatus"><span class="badge">E</span>ProviderStatus</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.provider.ProviderService"><span class="badge">S</span>ProviderService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#questionnaire.proto">questionnaire.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.ClientQuestionnaireData"><span class="badge">M</span>ClientQuestionnaireData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.ClientQuestionnaireDataRequest"><span class="badge">M</span>ClientQuestionnaireDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.Coding"><span class="badge">M</span>Coding</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.DeleteQuestionnaireRequest"><span class="badge">M</span>DeleteQuestionnaireRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.GetQuestionnaireRequest"><span class="badge">M</span>GetQuestionnaireRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.Questionnaire"><span class="badge">M</span>Questionnaire</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireData"><span class="badge">M</span>QuestionnaireData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireDataRequest"><span class="badge">M</span>QuestionnaireDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireItem"><span class="badge">M</span>QuestionnaireItem</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireItemAnswerOption"><span class="badge">M</span>QuestionnaireItemAnswerOption</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireItemExtension"><span class="badge">M</span>QuestionnaireItemExtension</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireItemInitial"><span class="badge">M</span>QuestionnaireItemInitial</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireRequest"><span class="badge">M</span>QuestionnaireRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.Questionnaires"><span class="badge">M</span>Questionnaires</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.SubmissionResponse"><span class="badge">M</span>SubmissionResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.SystemQuestionnaireData"><span class="badge">M</span>SystemQuestionnaireData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.UserQuestionnaireData"><span class="badge">M</span>UserQuestionnaireData</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.UserQuestionnaireDataRequest"><span class="badge">M</span>UserQuestionnaireDataRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.ValueBoolean"><span class="badge">M</span>ValueBoolean</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.ValueCodeableConcept"><span class="badge">M</span>ValueCodeableConcept</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.ValueCoding"><span class="badge">M</span>ValueCoding</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.questionnaire.QuestionnaireService"><span class="badge">S</span>QuestionnaireService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#routine.proto">routine.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution"><span class="badge">M</span>ClinicalProtocolExecution</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest"><span class="badge">M</span>ClinicalProtocolExecutionRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecutionResponse"><span class="badge">M</span>ClinicalProtocolExecutionResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.DeliveryTracking"><span class="badge">M</span>DeliveryTracking</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.DeliveryTrackingRequest"><span class="badge">M</span>DeliveryTrackingRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.DeliveryTrackingResponse"><span class="badge">M</span>DeliveryTrackingResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.Diagnosis"><span class="badge">M</span>Diagnosis</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.DiagnosisRequest"><span class="badge">M</span>DiagnosisRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.DiagnosisResponse"><span class="badge">M</span>DiagnosisResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.LabOrder"><span class="badge">M</span>LabOrder</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.LabOrderRequest"><span class="badge">M</span>LabOrderRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.LabOrderResponse"><span class="badge">M</span>LabOrderResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.LabResult"><span class="badge">M</span>LabResult</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.LabResultRequest"><span class="badge">M</span>LabResultRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.LabResultResponse"><span class="badge">M</span>LabResultResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.Medication"><span class="badge">M</span>Medication</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.MedicationRequest"><span class="badge">M</span>MedicationRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.MedicationResponse"><span class="badge">M</span>MedicationResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.Routine"><span class="badge">M</span>Routine</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.RoutineRequest"><span class="badge">M</span>RoutineRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.RoutineResponse"><span class="badge">M</span>RoutineResponse</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis"><span class="badge">M</span>SuspectedDiagnosis</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest"><span class="badge">M</span>SuspectedDiagnosisRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosisResponse"><span class="badge">M</span>SuspectedDiagnosisResponse</a>
                  </li>
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution.Status"><span class="badge">E</span>ClinicalProtocolExecution.Status</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.DeliveryTracking.Status"><span class="badge">E</span>DeliveryTracking.Status</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.Routine.Status"><span class="badge">E</span>Routine.Status</a>
                  </li>
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.routine.RoutineSystemService"><span class="badge">S</span>RoutineSystemService</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#tinkar.proto">tinkar.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.tinkar.TinkarRequest"><span class="badge">M</span>TinkarRequest</a>
                  </li>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.tinkar.TinkarResponse"><span class="badge">M</span>TinkarResponse</a>
                  </li>
                
                
                
                
                  <li>
                    <a href="#cdx.opencdx.grpc.tinkar.Tinkar"><span class="badge">S</span>Tinkar</a>
                  </li>
                
              </ul>
            </li>
          
            
            <li>
              <a href="#types.proto">types.proto</a>
              <ul>
                
                  <li>
                    <a href="#cdx.opencdx.grpc.common.types.CategoryType"><span class="badge">M</span>CategoryType</a>
                  </li>
                
                
                
                
              </ul>
            </li>
          
          <li><a href="#scalar-value-types">Scalar Value Types</a></li>
        </ul>
      </div>
  
      
        
        <div class="file-heading">
          <h2 id="anf_statement.proto">anf_statement.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.anf.ANFStatement">ANFStatement</h3>
          <p>Main representation of an ANFStatement.</p>
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>status</td>
                    <td><a href="#cdx.opencdx.grpc.anf.Status">Status</a></td>
                    <td></td>
                    <td><p>Status of the ANFStatement </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.anf.AssociatedStatement">AssociatedStatement</h3>
          <p>Representation of associated statements like preconditions or interpretations.</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.anf.Circumstance">Circumstance</h3>
          <p>Representation of the circumstances associated with a clinical statement</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>timing</td>
                    <td><a href="#cdx.opencdx.grpc.anf.Measure">Measure</a></td>
                    <td></td>
                    <td><p>Timing information, represents a past or current time </p></td>
                  </tr>
                
                  <tr>
                    <td>purpose</td>
                    <td><a href="#cdx.opencdx.grpc.anf.LogicalExpression">LogicalExpression</a></td>
                    <td>repeated</td>
                    <td><p>Purposes information </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.anf.Identifier">Identifier</h3>
          <p>Unique identifier for an ANFStatement.</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>circumstance</td>
                    <td><a href="#cdx.opencdx.grpc.anf.Circumstance">Circumstance</a></td>
                    <td></td>
                    <td><p>Circumstances associated with a clinical statement </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.anf.Participant">Participant</h3>
          <p>Representation of a participant, typically a patient.</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                    <td>circumstance</td>
                    <td><a href="#cdx.opencdx.grpc.anf.Circumstance">Circumstance</a></td>
                    <td></td>
                    <td><p>Circumstances associated with a clinical statement </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.anf.Practitioner">Practitioner</h3>
          <p>Representation of a practitioner or authoring entity.</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                    <td>circumstance</td>
                    <td><a href="#cdx.opencdx.grpc.anf.Circumstance">Circumstance</a></td>
                    <td></td>
                    <td><p>Circumstances associated with a clinical statement </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.anf.Timing">Timing</h3>
          <p>Representation of timing information.</p>
  
          
            <table class="field-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
        
          <h3 id="cdx.opencdx.grpc.anf.Status">Status</h3>
          <p>Media Status</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>STATUS_UNSPECIFIED</td>
                  <td>0</td>
                  <td><p>Unspecified or unknown status</p></td>
                </tr>
              
                <tr>
                  <td>STATUS_ACTIVE</td>
                  <td>1</td>
                  <td><p>Media is active.</p></td>
                </tr>
              
                <tr>
                  <td>STATUS_DELETED</td>
                  <td>2</td>
                  <td><p>Media is being deleted.</p></td>
                </tr>
              
            </tbody>
          </table>
        
  
        
  
        
          <h3 id="cdx.opencdx.grpc.anf.ANFService">ANFService</h3>
          <p>Service definition for ANF operations.</p><p>Provides CRUD operations for ANFStatement and circumstances.</p>
          <table class="enum-table">
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
              
            </tbody>
          </table>
  
          
      
        
        <div class="file-heading">
          <h2 id="audit.proto">audit.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.audit.Actor">Actor</h3>
          <p>Identifies the identity that initiated this audit.</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
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
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.audit.AuditSource">AuditSource</h3>
          <p>Source of the Audit event.</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
  
          
      
        
        <div class="file-heading">
          <h2 id="classification.proto">classification.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.neural.classification.ClassificationRequest">ClassificationRequest</h3>
          <p>UserClassificationRequest represents input based on the user-provided information.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>user_answer</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.UserAnswer">UserAnswer</a></td>
                    <td></td>
                    <td><p>user entered and generated information </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.ClassificationResponse">ClassificationResponse</h3>
          <p>ClassificationResponse message represents the result of test kit classification.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>test_kit_name</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Recommended test kit name </p></td>
                  </tr>
                
                  <tr>
                    <td>confidence</td>
                    <td><a href="#float">float</a></td>
                    <td>optional</td>
                    <td><p>Confidence score of the classification </p></td>
                  </tr>
                
                  <tr>
                    <td>positive_probability</td>
                    <td><a href="#float">float</a></td>
                    <td>optional</td>
                    <td><p>Probability of a positive test result </p></td>
                  </tr>
                
                  <tr>
                    <td>message</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Explanatory message regarding the classification </p></td>
                  </tr>
                
                  <tr>
                    <td>availability</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Availability status of the recommended test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>cost</td>
                    <td><a href="#float">float</a></td>
                    <td>optional</td>
                    <td><p>Cost associated with the recommended test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>further_actions</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Recommendations for further actions based on the classification </p></td>
                  </tr>
                
                  <tr>
                    <td>alternative_options</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.TestKit">TestKit</a></td>
                    <td>repeated</td>
                    <td><p>List of alternative test kit options </p></td>
                  </tr>
                
                  <tr>
                    <td>feedback_url</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>URL for user feedback on the classification result </p></td>
                  </tr>
                
                  <tr>
                    <td>timestamp</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td></td>
                    <td><p>Timestamp of the classification result </p></td>
                  </tr>
                
                  <tr>
                    <td>user_id</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>User identifier associated with the classification </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.Interpretation">Interpretation</h3>
          <p>Interpretation message provides positive and negative interpretation guides.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>positive</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.Interpretation.PositiveEntry">Interpretation.PositiveEntry</a></td>
                    <td>repeated</td>
                    <td><p>Positive interpretation guide (e.g., {&#34;result&#34;: &#34;Two lines&#34;}) </p></td>
                  </tr>
                
                  <tr>
                    <td>negative</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.Interpretation.NegativeEntry">Interpretation.NegativeEntry</a></td>
                    <td>repeated</td>
                    <td><p>Negative interpretation guide (e.g., {&#34;result&#34;: &#34;One line&#34;}) </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.Interpretation.NegativeEntry">Interpretation.NegativeEntry</h3>
          <p></p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.Interpretation.PositiveEntry">Interpretation.PositiveEntry</h3>
          <p></p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.Location">Location</h3>
          <p>Location message represents geographical coordinates.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>latitude</td>
                    <td><a href="#double">double</a></td>
                    <td></td>
                    <td><p>Latitude of the location </p></td>
                  </tr>
                
                  <tr>
                    <td>longitude</td>
                    <td><a href="#double">double</a></td>
                    <td></td>
                    <td><p>Longitude of the location </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.Symptom">Symptom</h3>
          <p>Symptom message represents information about a symptom.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>name</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Name of the symptom </p></td>
                  </tr>
                
                  <tr>
                    <td>severity</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.SeverityLevel">SeverityLevel</a></td>
                    <td></td>
                    <td><p>Severity level of the symptom (LOW, MEDIUM, HIGH) </p></td>
                  </tr>
                
                  <tr>
                    <td>onset_date</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Date when the symptom first appeared (in UTC format) </p></td>
                  </tr>
                
                  <tr>
                    <td>duration</td>
                    <td><a href="#int32">int32</a></td>
                    <td>optional</td>
                    <td><p>Duration of the symptom in days or weeks </p></td>
                  </tr>
                
                  <tr>
                    <td>additional_details</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Additional details about the symptom </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.TestKit">TestKit</h3>
          <p>TestKit message represents information about a test kit.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>name</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Name of the test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>target_disease</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Target disease for the test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>specimen_type</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.SpecimenType">SpecimenType</a></td>
                    <td></td>
                    <td><p>Type of specimen required for the test (e.g., BLOOD, SWAB, URINE) </p></td>
                  </tr>
                
                  <tr>
                    <td>procedure</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Procedure for conducting the test </p></td>
                  </tr>
                
                  <tr>
                    <td>interpretation_guide</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.Interpretation">Interpretation</a></td>
                    <td></td>
                    <td><p>Interpretation guide for test results </p></td>
                  </tr>
                
                  <tr>
                    <td>brand</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Brand of the test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>approval_status</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Approval status of the test kit (e.g., FDA-approved, CE-marked) </p></td>
                  </tr>
                
                  <tr>
                    <td>accuracy</td>
                    <td><a href="#float">float</a></td>
                    <td>optional</td>
                    <td><p>Accuracy percentage of the test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>test_duration</td>
                    <td><a href="#int32">int32</a></td>
                    <td>optional</td>
                    <td><p>Duration of the test in minutes </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.neural.classification.UserAnswer">UserAnswer</h3>
          <p>UserAnswer message represents user-provided information for test kit classification.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>test_kit_id</td>
                    <td><a href="#int32">int32</a></td>
                    <td>optional</td>
                    <td><p>ID of the chosen test kit </p></td>
                  </tr>
                
                  <tr>
                    <td>symptoms</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.Symptom">Symptom</a></td>
                    <td>repeated</td>
                    <td><p>List of symptoms reported by the user </p></td>
                  </tr>
                
                  <tr>
                    <td>image_data</td>
                    <td><a href="#bytes">bytes</a></td>
                    <td></td>
                    <td><p>Image data representing the test result </p></td>
                  </tr>
                
                  <tr>
                    <td>text_result</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Textual description of the test result </p></td>
                  </tr>
                
                  <tr>
                    <td>age</td>
                    <td><a href="#int32">int32</a></td>
                    <td>optional</td>
                    <td><p>Age of the user </p></td>
                  </tr>
                
                  <tr>
                    <td>gender</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Gender of the user </p></td>
                  </tr>
                
                  <tr>
                    <td>medical_conditions</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Medical conditions of the user (comma-separated list) </p></td>
                  </tr>
                
                  <tr>
                    <td>pregnancy_status</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Pregnancy status of the user (e.g., pregnant, not pregnant) </p></td>
                  </tr>
                
                  <tr>
                    <td>language_preference</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Language preference of the user </p></td>
                  </tr>
                
                  <tr>
                    <td>user_location</td>
                    <td><a href="#cdx.opencdx.grpc.neural.classification.Location">Location</a></td>
                    <td></td>
                    <td><p>Location of the user </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
  
        
          <h3 id="cdx.opencdx.grpc.neural.classification.SeverityLevel">SeverityLevel</h3>
          <p>Enum to represent severity levels</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>LOW</td>
                  <td>0</td>
                  <td><p>Low severity level</p></td>
                </tr>
              
                <tr>
                  <td>MEDIUM</td>
                  <td>1</td>
                  <td><p>Medium severity level</p></td>
                </tr>
              
                <tr>
                  <td>HIGH</td>
                  <td>2</td>
                  <td><p>High severity level</p></td>
                </tr>
              
            </tbody>
          </table>
        
          <h3 id="cdx.opencdx.grpc.neural.classification.SpecimenType">SpecimenType</h3>
          <p>Enum to represent different specimen types</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>BLOOD</td>
                  <td>0</td>
                  <td><p>Specimen type: Blood</p></td>
                </tr>
              
                <tr>
                  <td>SWAB</td>
                  <td>1</td>
                  <td><p>Specimen type: Swab</p></td>
                </tr>
              
                <tr>
                  <td>URINE</td>
                  <td>2</td>
                  <td><p>Specimen type: Urine</p></td>
                </tr>
              
            </tbody>
          </table>
        
  
        
  
        
          <h3 id="cdx.opencdx.grpc.neural.classification.ClassificationService">ClassificationService</h3>
          <p>TestKitClasClassifierServicesifier service provides the Classify RPC for test kit classification.</p>
          <table class="enum-table">
            <thead>
              <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>Classify</td>
                  <td><a href="#cdx.opencdx.grpc.neural.classification.ClassificationRequest">ClassificationRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.neural.classification.ClassificationResponse">ClassificationResponse</a></td>
                  <td><p>Classify RPC takes a UserAnswer input and returns a ClassificationResult</p></td>
                </tr>
              
            </tbody>
          </table>
  
          
      
        
        <div class="file-heading">
          <h2 id="codeable.proto">codeable.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.codeable.CodeableConcept">CodeableConcept</h3>
          <p>Concept - reference to a terminology or just  text.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Unique id for inter-element referencing </p></td>
                  </tr>
                
                  <tr>
                    <td>coding</td>
                    <td><a href="#cdx.opencdx.grpc.codeable.Coding">Coding</a></td>
                    <td>repeated</td>
                    <td><p>Code defined by a terminology system </p></td>
                  </tr>
                
                  <tr>
                    <td>text</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Plain text representation of the concept </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.codeable.Coding">Coding</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Unique id for inter-element referencing </p></td>
                  </tr>
                
                  <tr>
                    <td>system</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Identity of the terminology system </p></td>
                  </tr>
                
                  <tr>
                    <td>version</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Version of the system - if relevant </p></td>
                  </tr>
                
                  <tr>
                    <td>code</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Symbol in syntax defined by the system </p></td>
                  </tr>
                
                  <tr>
                    <td>display</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Representation defined by the system </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
  
        
  
        
  
        
      
        
        <div class="file-heading">
          <h2 id="common.proto">common.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.common.Address">Address</h3>
          <p>Message to represent an address.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>country_id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Country code. </p></td>
                  </tr>
                
                  <tr>
                    <td>address_purpose</td>
                    <td><a href="#cdx.opencdx.grpc.common.AddressPurpose">AddressPurpose</a></td>
                    <td></td>
                    <td><p>Purpose of the address (e.g., LOCATION, MAILING). </p></td>
                  </tr>
                
                  <tr>
                    <td>address_1</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>First line of the address. </p></td>
                  </tr>
                
                  <tr>
                    <td>address_2</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>First line of the address. </p></td>
                  </tr>
                
                  <tr>
                    <td>address_3</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>First line of the address. </p></td>
                  </tr>
                
                  <tr>
                    <td>city</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>City. </p></td>
                  </tr>
                
                  <tr>
                    <td>state</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>State. </p></td>
                  </tr>
                
                  <tr>
                    <td>postal_code</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Postal code. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.common.ContactInfo">ContactInfo</h3>
          <p>Contact information.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>name</td>
                    <td><a href="#cdx.opencdx.grpc.common.FullName">FullName</a></td>
                    <td></td>
                    <td><p>Full name of the user </p></td>
                  </tr>
                
                  <tr>
                    <td>user_id</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Unique identifier for the user </p></td>
                  </tr>
                
                  <tr>
                    <td>addresses</td>
                    <td><a href="#cdx.opencdx.grpc.common.Address">Address</a></td>
                    <td>repeated</td>
                    <td><p>User&#39;s addresses </p></td>
                  </tr>
                
                  <tr>
                    <td>phone_numbers</td>
                    <td><a href="#cdx.opencdx.grpc.common.PhoneNumber">PhoneNumber</a></td>
                    <td>repeated</td>
                    <td><p>User&#39;s phone numbers </p></td>
                  </tr>
                
                  <tr>
                    <td>email</td>
                    <td><a href="#cdx.opencdx.grpc.common.EmailAddress">EmailAddress</a></td>
                    <td>repeated</td>
                    <td><p>User&#39;s email address </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.common.Country">Country</h3>
          <p>Country message represents a country.</p>
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.common.EmailAddress">EmailAddress</h3>
          <p>Email address details.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>email</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Email address </p></td>
                  </tr>
                
                  <tr>
                    <td>type</td>
                    <td><a href="#cdx.opencdx.grpc.common.EmailType">EmailType</a></td>
                    <td></td>
                    <td><p>Type of email address (e.g., personal, work) </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.common.FullName">FullName</h3>
          <p>Full name information.</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.common.PhoneNumber">PhoneNumber</h3>
          <p>Phone number details.</p>
  
          
            <table class="field-table">
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
                    <td><a href="#cdx.opencdx.grpc.common.PhoneType">PhoneType</a></td>
                    <td></td>
                    <td><p>Type of phone number (e.g., mobile, home, work) </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
  
        
          <h3 id="cdx.opencdx.grpc.common.AddressPurpose">AddressPurpose</h3>
          <p>Enum to represent the purpose of an address.</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>LOCATION</td>
                  <td>0</td>
                  <td><p>Address used for location purposes.</p></td>
                </tr>
              
                <tr>
                  <td>MAILING</td>
                  <td>1</td>
                  <td><p>Address used for mailing purposes.</p></td>
                </tr>
              
                <tr>
                  <td>PRIMARY</td>
                  <td>2</td>
                  <td><p>Primary address.</p></td>
                </tr>
              
                <tr>
                  <td>SECONDARY</td>
                  <td>3</td>
                  <td><p>Secondary address.</p></td>
                </tr>
              
                <tr>
                  <td>BILLING</td>
                  <td>4</td>
                  <td><p>Billing address.</p></td>
                </tr>
              
                <tr>
                  <td>SHIPPING</td>
                  <td>5</td>
                  <td><p>Shipping address.</p></td>
                </tr>
              
                <tr>
                  <td>WORK</td>
                  <td>6</td>
                  <td><p>Work address.</p></td>
                </tr>
              
            </tbody>
          </table>
        
          <h3 id="cdx.opencdx.grpc.common.EmailType">EmailType</h3>
          <p>Enumeration for email type.</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>EMAIL_TYPE_NOT_SPECIFIED</td>
                  <td>0</td>
                  <td><p>Not specified</p></td>
                </tr>
              
                <tr>
                  <td>EMAIL_TYPE_PERSONAL</td>
                  <td>1</td>
                  <td><p>Personal email</p></td>
                </tr>
              
                <tr>
                  <td>EMAIL_TYPE_WORK</td>
                  <td>2</td>
                  <td><p>Work email</p></td>
                </tr>
              
                <tr>
                  <td>EMAIL_TYPE_OTHER</td>
                  <td>3</td>
                  <td><p>Other</p></td>
                </tr>
              
            </tbody>
          </table>
        
          <h3 id="cdx.opencdx.grpc.common.PhoneType">PhoneType</h3>
          <p>Enumeration for phone types.</p>
          <table class="enum-table">
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
        
  
        
  
        
      
        
        <div class="file-heading">
          <h2 id="communications.proto">communications.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.communication.Attachment">Attachment</h3>
          <p>Defines the structure for attachments.</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.communication.EmailTemplateListRequest">EmailTemplateListRequest</h3>
          <p>Request Message to list EmailTemplates</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.communication.Notification.CustomDataEntry">Notification.CustomDataEntry</h3>
          <p></p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.communication.NotificationEventListRequest">NotificationEventListRequest</h3>
          <p>Request Message to list NotificationEvents</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.communication.SMSTemplateListRequest">SMSTemplateListRequest</h3>
          <p>Request Message to list SMSTemplates</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
                  <td><p>Template is a confirmation.</p></td>
                </tr>
              
            </tbody>
          </table>
        
  
        
  
        
          <h3 id="cdx.opencdx.grpc.communication.CommunicationService">CommunicationService</h3>
          <p>Service API</p>
          <table class="enum-table">
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
  
          
      
        
        <div class="file-heading">
          <h2 id="connected_test.proto">connected_test.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.connected.BasicInfo">BasicInfo</h3>
          <p>Basic Information about the Test</p>
  
          
            <table class="field-table">
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
                    <td><p>Class or type of the test </p></td>
                  </tr>
                
                  <tr>
                    <td>user_id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>ID of the user associated with the test </p></td>
                  </tr>
                
                  <tr>
                    <td>national_health_id</td>
                    <td><a href="#string">string</a></td>
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
                    <td>organization_id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>ID of the organization </p></td>
                  </tr>
                
                  <tr>
                    <td>workspace_id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>ID of the workspace </p></td>
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
  
          
            <table class="field-table">
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
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                
                  <tr>
                    <td>creator</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the creator of this event. </p></td>
                  </tr>
                
                  <tr>
                    <td>modifier</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>ID of the modifier of this event. </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.connected.ConnectedTestListByNHIDRequest">ConnectedTestListByNHIDRequest</h3>
          <p>Request Message to list NotificationConnectedTests by National health id</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                    <td>cassette_lot_number</td>
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
                    <td>cassette_expiration_date</td>
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                    <td>test_classification</td>
                    <td><a href="#cdx.opencdx.grpc.connected.TestClassification">TestClassification</a></td>
                    <td></td>
                    <td><p>Classification or type of the test </p></td>
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
                    <td><a href="#string">string</a></td>
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
                    <td>specimen_type</td>
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
                
                  <tr>
                    <td>loinc_code</td>
                    <td><a href="#cdx.opencdx.grpc.codeable.Coding">cdx.opencdx.grpc.codeable.Coding</a></td>
                    <td></td>
                    <td><p>LOINC Code </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.connected.TestIdRequest">TestIdRequest</h3>
          <p>Request for a Connected Test</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
          <table class="enum-table">
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
          <table class="enum-table">
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
        
          <h3 id="cdx.opencdx.grpc.connected.TestClassification">TestClassification</h3>
          <p>Enum to classify the type of test</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>TEST_CLASSIFICATION_UNSPECIFIED</td>
                  <td>0</td>
                  <td><p>Unspecified test classification</p></td>
                </tr>
              
                <tr>
                  <td>TEST_CLASSIFICATION_GENERAL</td>
                  <td>1</td>
                  <td><p>General test</p></td>
                </tr>
              
                <tr>
                  <td>TEST_CLASSIFICATION_DIAGNOSTIC</td>
                  <td>2</td>
                  <td><p>Diagnostic test</p></td>
                </tr>
              
                <tr>
                  <td>TEST_CLASSIFICATION_SCREENING</td>
                  <td>3</td>
                  <td><p>Screening test</p></td>
                </tr>
              
                <tr>
                  <td>TEST_CLASSIFICATION_MONITORING</td>
                  <td>4</td>
                  <td><p>Monitoring test</p></td>
                </tr>
              
                <tr>
                  <td>TEST_CLASSIFICATION_COVID_19</td>
                  <td>5</td>
                  <td><p>COVID-19 related test</p></td>
                </tr>
              
            </tbody>
          </table>
        
          <h3 id="cdx.opencdx.grpc.connected.TestFormat">TestFormat</h3>
          <p>Define test format types</p>
          <table class="enum-table">
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
          <table class="enum-table">
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
  
          
      
        
        <div class="file-heading">
          <h2 id="helloworld.proto">helloworld.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.helloworld.HelloRequest">HelloRequest</h3>
          <p>The request message containing the user's name.</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.helloworld.HelloResponse">HelloResponse</h3>
          <p>The response message containing the greetings</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
  
        
  
        
  
        
          <h3 id="cdx.opencdx.grpc.helloworld.Greeter">Greeter</h3>
          <p>The greeting service definition.</p>
          <table class="enum-table">
            <thead>
              <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>SayHello</td>
                  <td><a href="#cdx.opencdx.grpc.helloworld.HelloRequest">HelloRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.helloworld.HelloResponse">HelloResponse</a></td>
                  <td><p>This is the description for the grpc SayHello Greeter Service. This takes in a HelloRequest as message and returns
  a HelloReply greeting message. Used to Send a greeting.</p></td>
                </tr>
              
            </tbody>
          </table>
  
          
      
        
        <div class="file-heading">
          <h2 id="iam_organization.proto">iam_organization.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.organization.CreateOrganizationRequest">CreateOrganizationRequest</h3>
          <p>Request for creating an organization</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
                    <td><a href="#cdx.opencdx.grpc.common.FullName">cdx.opencdx.grpc.common.FullName</a></td>
                    <td>optional</td>
                    <td><p>Full name of the employee </p></td>
                  </tr>
                
                  <tr>
                    <td>title</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Title or position of the employee </p></td>
                  </tr>
                
                  <tr>
                    <td>phone_numbers</td>
                    <td><a href="#cdx.opencdx.grpc.common.PhoneNumber">cdx.opencdx.grpc.common.PhoneNumber</a></td>
                    <td>repeated</td>
                    <td><p>User&#39;s phone numbers </p></td>
                  </tr>
                
                  <tr>
                    <td>email</td>
                    <td><a href="#cdx.opencdx.grpc.common.EmailAddress">cdx.opencdx.grpc.common.EmailAddress</a></td>
                    <td>repeated</td>
                    <td><p>User&#39;s email address </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.organization.Empty">Empty</h3>
          <p>Empty message used in RPC methods</p>
  
          
  
          
        
          <h3 id="cdx.opencdx.grpc.organization.GetOrganizationDetailsByIdRequest">GetOrganizationDetailsByIdRequest</h3>
          <p>Request for retrieving organization details by ID</p>
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
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
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>id</td>
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
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Date when the organization was founded </p></td>
                  </tr>
                
                  <tr>
                    <td>address</td>
                    <td><a href="#cdx.opencdx.grpc.common.Address">cdx.opencdx.grpc.common.Address</a></td>
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
                    <td><a href="#cdx.opencdx.grpc.common.ContactInfo">cdx.opencdx.grpc.common.ContactInfo</a></td>
                    <td>repeated</td>
                    <td><p>Contact information for the organization </p></td>
                  </tr>
                
                  <tr>
                    <td>workspace_ids</td>
                    <td><a href="#string">string</a></td>
                    <td>repeated</td>
                    <td><p>Departments (Workspaces) within the organization </p></td>
                  </tr>
                
                  <tr>
                    <td>created</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was created. </p></td>
                  </tr>
                
                  <tr>
                    <td>modified</td>
                    <td><a href="#google.protobuf.Timestamp">google.protobuf.Timestamp</a></td>
                    <td>optional</td>
                    <td><p>Timestamp when this event was modified. </p></td>
                  </tr>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            <h3 id="cdx.opencdx.grpc.inventory.Manufacturer">Manufacturer</h3>
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                Xn�������������������Xn                                    Rn�Ƕ���Rn                                    Mn�Ǫ���Mn                                    cn�������������������cn                                                                                                                  ���������������������                                �Ͽ�������������������������������������                                �ϲ�������������������������������������                                ���������������������                                                                                                              �����������������������                            �Ͽ�����������������������������������������                            �ϲ�����������������������������������������                            �����������������������                                                                                                            Wm����������������������Wm                            Rm��������������������������������������������Rm                            Lm��������������������������������������������Lm                            bm����������������������bm                                                                                                            �������������������������                            �ǿ���������������������������������������������                            �ǲ���������������������������������������������                            �������������������������                                                                                                            ��������������������������                            ���                            ���                            ��������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ������������������������                            ������������������������������������������������                            ������������������������������������������������                            ������������������������                                                                                                            ��������������������������                            ���                            ���                            ��������������������������                                                                                                            �������������������������                            �ǿ���������������������������������������������                            �ǲ���������������������������������������������                            �������������������������                                                                                                            Xn����������������������Xn                            Rn��������������������������������������������Rn                            Mn��������������������������������������������Mn                            cn����������������������cn                                                                                                            �����������������������                            �п�����������������������������������������                            �в�����������������������������������������                            �����������������������                                                                                                              ���������������������                                �Ͽ�������������������������������������                                �ϲ�������������������������������������                                ���������������������                                                                                                                  Yo�������������������Yo                                    So�˷���So                                    Mo�˪���Mo                                    do�������������������do                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          8ne�|�|�e�8n                                                                                                                                                                                                                                                                                                          jς�������������������������������������������������������������������������������j�                                                                                                                                                                                                                                                                                                      jς�����������������������������������������������������������������������������������j�                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     <td>protocol_id</td>
                    <td><a href="#string">string</a></td>
                    <td>optional</td>
                    <td><p>Reference to the specific clinical protocol being executed </p></td>
                  </tr>
                
                  <tr>
                    <td>status</td>
                    <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution.Status">ClinicalProtocolExecution.Status</a></td>
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest">ClinicalProtocolExecutionRequest</h3>
          <p>Request and Response messages for CreateClinicalProtocolExecution operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>clinical_protocol_execution</td>
                    <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.ClinicalProtocolExecutionResponse">ClinicalProtocolExecutionResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>clinical_protocol_execution</td>
                    <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecution">ClinicalProtocolExecution</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</h3>
          <p>Entities for the Delivery Tracking System</p>
  
          
            <table class="field-table">
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
                    <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking.Status">DeliveryTracking.Status</a></td>
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.DeliveryTrackingRequest">DeliveryTrackingRequest</h3>
          <p>Request and Response messages for CreateDeliveryTracking operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>delivery_tracking</td>
                    <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.DeliveryTrackingResponse">DeliveryTrackingResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>delivery_tracking</td>
                    <td><a href="#cdx.opencdx.grpc.routine.DeliveryTracking">DeliveryTracking</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</h3>
          <p>Diagnosis Data</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.DiagnosisRequest">DiagnosisRequest</h3>
          <p>Request and Response messages for CreateDiagnosis operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>diagnosis</td>
                    <td><a href="#cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.DiagnosisResponse">DiagnosisResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>diagnosis</td>
                    <td><a href="#cdx.opencdx.grpc.routine.Diagnosis">Diagnosis</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.LabOrder">LabOrder</h3>
          <p>Entities for Create Conditions</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.LabOrderRequest">LabOrderRequest</h3>
          <p>Request and Response messages for CreateLabOrder operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>lab_order</td>
                    <td><a href="#cdx.opencdx.grpc.routine.LabOrder">LabOrder</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.LabOrderResponse">LabOrderResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>lab_order</td>
                    <td><a href="#cdx.opencdx.grpc.routine.LabOrder">LabOrder</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.LabResult">LabResult</h3>
          <p>Lab Result data</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.LabResultRequest">LabResultRequest</h3>
          <p>Request and Response messages for CreateLabResult operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>lab_result</td>
                    <td><a href="#cdx.opencdx.grpc.routine.LabResult">LabResult</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.LabResultResponse">LabResultResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>lab_result</td>
                    <td><a href="#cdx.opencdx.grpc.routine.LabResult">LabResult</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.Medication">Medication</h3>
          <p>Medication data</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.MedicationRequest">MedicationRequest</h3>
          <p>Request and Response messages for CreateMedication operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>medication</td>
                    <td><a href="#cdx.opencdx.grpc.routine.Medication">Medication</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.MedicationResponse">MedicationResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>medication</td>
                    <td><a href="#cdx.opencdx.grpc.routine.Medication">Medication</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.Routine">Routine</h3>
          <p>Entities for the Routine System</p>
  
          
            <table class="field-table">
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
                    <td><a href="#cdx.opencdx.grpc.routine.Routine.Status">Routine.Status</a></td>
                    <td></td>
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.RoutineRequest">RoutineRequest</h3>
          <p>Request and Response messages for CreateRoutine operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>routine</td>
                    <td><a href="#cdx.opencdx.grpc.routine.Routine">Routine</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.RoutineResponse">RoutineResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>routine</td>
                    <td><a href="#cdx.opencdx.grpc.routine.Routine">Routine</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</h3>
          <p>Suspected Diagnosis data</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest">SuspectedDiagnosisRequest</h3>
          <p>Request and Response messages for CreateSuspectedDiagnosis operation</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>suspected_diagnosis</td>
                    <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.routine.SuspectedDiagnosisResponse">SuspectedDiagnosisResponse</h3>
          <p></p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>suspected_diagnosis</td>
                    <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosis">SuspectedDiagnosis</a></td>
                    <td></td>
                    <td><p> </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
  
        
          <h3 id="cdx.opencdx.grpc.routine.ClinicalProtocolExecution.Status">ClinicalProtocolExecution.Status</h3>
          <p>Entities for protocol Execution status enumeration</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>NOT_STARTED</td>
                  <td>0</td>
                  <td><p>The protocol execution has not started yet</p></td>
                </tr>
              
                <tr>
                  <td>IN_PROGRESS</td>
                  <td>1</td>
                  <td><p>The protocol execution is currently in progress</p></td>
                </tr>
              
                <tr>
                  <td>COMPLETED</td>
                  <td>2</td>
                  <td><p>The protocol execution has been successfully completed</p></td>
                </tr>
              
                <tr>
                  <td>FAILED</td>
                  <td>3</td>
                  <td><p>The protocol execution was not successful</p></td>
                </tr>
              
                <tr>
                  <td>CANCELED</td>
                  <td>4</td>
                  <td><p>The protocol execution was scheduled but got canceled</p></td>
                </tr>
              
                <tr>
                  <td>DELAYED</td>
                  <td>5</td>
                  <td><p>The protocol execution is behind schedule or delayed</p></td>
                </tr>
              
                <tr>
                  <td>UNDER_REVIEW</td>
                  <td>6</td>
                  <td><p>The protocol execution or its results are under review</p></td>
                </tr>
              
            </tbody>
          </table>
        
          <h3 id="cdx.opencdx.grpc.routine.DeliveryTracking.Status">DeliveryTracking.Status</h3>
          <p>Entities for Delivery status enumeration</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>IN_TRANSIT</td>
                  <td>0</td>
                  <td><p>The delivery is currently in transit</p></td>
                </tr>
              
                <tr>
                  <td>DELIVERED</td>
                  <td>1</td>
                  <td><p>The delivery has been successfully completed</p></td>
                </tr>
              
                <tr>
                  <td>CANCELED</td>
                  <td>2</td>
                  <td><p>The delivery was scheduled but got canceled</p></td>
                </tr>
              
                <tr>
                  <td>PENDING_PICKUP</td>
                  <td>3</td>
                  <td><p>The delivery is awaiting pickup</p></td>
                </tr>
              
                <tr>
                  <td>OUT_FOR_DELIVERY</td>
                  <td>4</td>
                  <td><p>The delivery is out for delivery</p></td>
                </tr>
              
                <tr>
                  <td>FAILED_DELIVERY</td>
                  <td>5</td>
                  <td><p>The delivery attempt was not successful</p></td>
                </tr>
              
                <tr>
                  <td>RETURNED</td>
                  <td>6</td>
                  <td><p>The delivery has been returned</p></td>
                </tr>
              
                <tr>
                  <td>DELAYED</td>
                  <td>7</td>
                  <td><p>The delivery is behind schedule or delayed</p></td>
                </tr>
              
                <tr>
                  <td>ON_HOLD</td>
                  <td>8</td>
                  <td><p>The delivery is temporarily on hold</p></td>
                </tr>
              
                <tr>
                  <td>EXCEPTION</td>
                  <td>9</td>
                  <td><p>There is an exception or issue with the delivery</p></td>
                </tr>
              
                <tr>
                  <td>LOST</td>
                  <td>10</td>
                  <td><p>The delivery is lost or cannot be located</p></td>
                </tr>
              
            </tbody>
          </table>
        
          <h3 id="cdx.opencdx.grpc.routine.Routine.Status">Routine.Status</h3>
          <p>Entities for Routine status enumeration</p>
          <table class="enum-table">
            <thead>
              <tr><td>Name</td><td>Number</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>SCHEDULED</td>
                  <td>0</td>
                  <td><p>The routine has been planned and scheduled</p></td>
                </tr>
              
                <tr>
                  <td>IN_PROGRESS</td>
                  <td>1</td>
                  <td><p>The routine is currently in progress</p></td>
                </tr>
              
                <tr>
                  <td>COMPLETED</td>
                  <td>2</td>
                  <td><p>The routine has been successfully completed</p></td>
                </tr>
              
                <tr>
                  <td>CANCELED</td>
                  <td>3</td>
                  <td><p>The routine was scheduled but got canceled</p></td>
                </tr>
              
                <tr>
                  <td>DELAYED</td>
                  <td>4</td>
                  <td><p>The routine is behind schedule or delayed</p></td>
                </tr>
              
                <tr>
                  <td>PENDING_APPROVAL</td>
                  <td>5</td>
                  <td><p>The routine is awaiting approval</p></td>
                </tr>
              
                <tr>
                  <td>FAILED</td>
                  <td>6</td>
                  <td><p>The routine execution was not successful</p></td>
                </tr>
              
                <tr>
                  <td>ON_HOLD</td>
                  <td>7</td>
                  <td><p>The routine is temporarily on hold</p></td>
                </tr>
              
                <tr>
                  <td>NOT_STARTED</td>
                  <td>8</td>
                  <td><p>The routine has not started yet</p></td>
                </tr>
              
                <tr>
                  <td>UNDER_REVIEW</td>
                  <td>9</td>
                  <td><p>The routine or its results are under review</p></td>
                </tr>
              
                <tr>
                  <td>DISCHARGED</td>
                  <td>10</td>
                  <td><p>Applicable in healthcare settings, indicating completion</p></td>
                </tr>
              
                <tr>
                  <td>RECALLED</td>
                  <td>11</td>
                  <td><p>The routine has been recalled or needs to be re-executed</p></td>
                </tr>
              
            </tbody>
          </table>
        
  
        
  
        
          <h3 id="cdx.opencdx.grpc.routine.RoutineSystemService">RoutineSystemService</h3>
          <p>gRPC services and operations</p>
          <table class="enum-table">
            <thead>
              <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>CreateRoutine</td>
                  <td><a href="#cdx.opencdx.grpc.routine.RoutineRequest">RoutineRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.RoutineResponse">RoutineResponse</a></td>
                  <td><p>Create a new routine entity</p></td>
                </tr>
              
                <tr>
                  <td>GetRoutine</td>
                  <td><a href="#cdx.opencdx.grpc.routine.RoutineRequest">RoutineRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.RoutineResponse">RoutineResponse</a></td>
                  <td><p>Retrieve information about a routine entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateDeliveryTracking</td>
                  <td><a href="#cdx.opencdx.grpc.routine.DeliveryTrackingRequest">DeliveryTrackingRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.DeliveryTrackingResponse">DeliveryTrackingResponse</a></td>
                  <td><p>Create a new delivery tracking entity</p></td>
                </tr>
              
                <tr>
                  <td>GetDeliveryTracking</td>
                  <td><a href="#cdx.opencdx.grpc.routine.DeliveryTrackingRequest">DeliveryTrackingRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.DeliveryTrackingResponse">DeliveryTrackingResponse</a></td>
                  <td><p>Retrieve information about a delivery tracking entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateClinicalProtocolExecution</td>
                  <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest">ClinicalProtocolExecutionRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecutionResponse">ClinicalProtocolExecutionResponse</a></td>
                  <td><p>Create a new clinical protocol execution entity</p></td>
                </tr>
              
                <tr>
                  <td>GetClinicalProtocolExecution</td>
                  <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecutionRequest">ClinicalProtocolExecutionRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.ClinicalProtocolExecutionResponse">ClinicalProtocolExecutionResponse</a></td>
                  <td><p>Retrieve information about a clinical protocol execution entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateLabOrder</td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabOrderRequest">LabOrderRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabOrderResponse">LabOrderResponse</a></td>
                  <td><p>Create a new lab order entity</p></td>
                </tr>
              
                <tr>
                  <td>GetLabOrder</td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabOrderRequest">LabOrderRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabOrderResponse">LabOrderResponse</a></td>
                  <td><p>Retrieve information about a lab order entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateDiagnosis</td>
                  <td><a href="#cdx.opencdx.grpc.routine.DiagnosisRequest">DiagnosisRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.DiagnosisResponse">DiagnosisResponse</a></td>
                  <td><p>Create a new diagnosis entity</p></td>
                </tr>
              
                <tr>
                  <td>GetDiagnosis</td>
                  <td><a href="#cdx.opencdx.grpc.routine.DiagnosisRequest">DiagnosisRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.DiagnosisResponse">DiagnosisResponse</a></td>
                  <td><p>Retrieve information about a diagnosis entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateSuspectedDiagnosis</td>
                  <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest">SuspectedDiagnosisRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosisResponse">SuspectedDiagnosisResponse</a></td>
                  <td><p>Create a new suspected diagnosis entity</p></td>
                </tr>
              
                <tr>
                  <td>GetSuspectedDiagnosis</td>
                  <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosisRequest">SuspectedDiagnosisRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.SuspectedDiagnosisResponse">SuspectedDiagnosisResponse</a></td>
                  <td><p>Retrieve information about a suspected diagnosis entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateLabResult</td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabResultRequest">LabResultRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabResultResponse">LabResultResponse</a></td>
                  <td><p>Create a new lab result entity</p></td>
                </tr>
              
                <tr>
                  <td>GetLabResult</td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabResultRequest">LabResultRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.LabResultResponse">LabResultResponse</a></td>
                  <td><p>Retrieve information about a lab result entity</p></td>
                </tr>
              
                <tr>
                  <td>CreateMedication</td>
                  <td><a href="#cdx.opencdx.grpc.routine.MedicationRequest">MedicationRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.MedicationResponse">MedicationResponse</a></td>
                  <td><p>Create a new medication entity</p></td>
                </tr>
              
                <tr>
                  <td>GetMedication</td>
                  <td><a href="#cdx.opencdx.grpc.routine.MedicationRequest">MedicationRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.routine.MedicationResponse">MedicationResponse</a></td>
                  <td><p>Retrieve information about a medication entity</p></td>
                </tr>
              
            </tbody>
          </table>
  
          
      
        
        <div class="file-heading">
          <h2 id="tinkar.proto">tinkar.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.tinkar.TinkarRequest">TinkarRequest</h3>
          <p>The request message containing the user's name.</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
          <h3 id="cdx.opencdx.grpc.tinkar.TinkarResponse">TinkarResponse</h3>
          <p>The response message containing the greetings</p>
  
          
            <table class="field-table">
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
  
            
  
          
        
  
        
  
        
  
        
          <h3 id="cdx.opencdx.grpc.tinkar.Tinkar">Tinkar</h3>
          <p>The greeting service definition.</p>
          <table class="enum-table">
            <thead>
              <tr><td>Method Name</td><td>Request Type</td><td>Response Type</td><td>Description</td></tr>
            </thead>
            <tbody>
              
                <tr>
                  <td>sayTinkar</td>
                  <td><a href="#cdx.opencdx.grpc.tinkar.TinkarRequest">TinkarRequest</a></td>
                  <td><a href="#cdx.opencdx.grpc.tinkar.TinkarResponse">TinkarResponse</a></td>
                  <td><p>Sends a greeting</p></td>
                </tr>
              
            </tbody>
          </table>
  
          
      
        
        <div class="file-heading">
          <h2 id="types.proto">types.proto</h2><a href="#title">Top</a>
        </div>
        <p></p>
  
        
          <h3 id="cdx.opencdx.grpc.common.types.CategoryType">CategoryType</h3>
          <p>Used to specify the type of category, vendor, manfacturer, test type etc.</p>
  
          
            <table class="field-table">
              <thead>
                <tr><td>Field</td><td>Type</td><td>Label</td><td>Description</td></tr>
              </thead>
              <tbody>
                
                  <tr>
                    <td>id</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Unique id </p></td>
                  </tr>
                
                  <tr>
                    <td>type</td>
                    <td><a href="#string">string</a></td>
                    <td></td>
                    <td><p>Category description or type </p></td>
                  </tr>
                
              </tbody>
            </table>
  
            
  
          
        
  
        
  
        
  
        
      
  
      <h2 id="scalar-value-types">Scalar Value Types</h2>
      <table class="scalar-value-types-table">
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
              <td>TrueClass/FalseClass</td>
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
    </body>
  </html>
  
  
  `;

    return <div dangerouslySetInnerHTML={{ __html: protodocHTML }} />;
};

export default ProtoDoc;
