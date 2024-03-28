import { gql } from "apollo-server-express";

export const typeDefs = gql`
  type Manufacturer {
    id: ID
    name: String
    website: String
    description: String
  }
  type Patient {
    id: ID
    gender: String
    firstName: String
    lastName: String
    birthDate: String
    language: String
    race: String
    ethnicity: String
    zipCode: String
    state: String
    city: String
    county: String
  }
  type Device {
    id: ID
    type: String
    model: String
    manufacturerId: ID
    vendorId: ID
    vendorCountryId: ID
    batchNumber: String
    serialNumber: String
    testTypeId: String
    testSensitivity: Float
    testSpecificity: Float
    storageRequirements: String
    approvalStatus: String
    url: String
    notes: String
    safety: String
    userInstructions: String
    limitations: String
    warrantyInfo: String
    intendedUseAge: Int
    fdaAuthorized: Boolean
    deviceStatus: String
    associatedSoftwareVersion: String
  }
  type Actor {
    identity_: String
    role_: String
    networkAddress_: String
    agentType_: Int
    memoizedIsInitialized: Int
    memoizedSize: Int
    memoizedHashCode: Int
    _class: String
  }
  type DataObject {
    resource_: String
    data_: String
    sensitivity_: Int
    memoizedIsInitialized: Int
    memoizedSize: Int
    memoizedHashCode: Int
    _class: String
  }
  type AuditSource {
    systemInfo_: String
    configuration_: String
    memoizedIsInitialized: Int
    memoizedSize: Int
    memoizedHashCode: Int
  }
  type AuditEntity {
    patientIdentifier_: String
    userIdentifier_: String
    memoizedIsInitialized: Int
    memoizedSize: Int
    memoizedHashCode: Int
  }

  type Audit {
    purposeOfUse: String,
    created: String,
    eventType: String,
    actor: Actor,
    dataObject: DataObject,
    auditSource: AuditSource,
    auditEntity: AuditEntity,
    _class: String,
    creator: String,
    modifier: String,
    modified: String    
  }

  type Query {
    getManufacturers: [Manufacturer]
    findAManufacturer(id: String): Manufacturer

    getDevices: [Device]
    findADevice(id: String): Device

    getPatients: [Patient]
    findAPatient(id: String): Patient

    getAudit: [Audit]
    
    getQuestionnaires: [Questionnaire]
    findAQuestionnaire(id: String): Questionnaire
  }

  type Item {
    type_: String,
    required_: Boolean,
    linkId_: String
  }

  type Questionnaire {
    id: ID,
    resourceType: String,
    title: String,
    status: String,
    description: String,
    items: [Item]
  }
`;