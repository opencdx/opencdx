import { gql } from "graphql-tag";

export const typeDefs = gql`
  type Manufacturer {
    id: ID
    name: String
    website: String
    description: String
  }
  type Demographics {
    ethnicity_: String
    race_: String
    nationality_: String
  }
  type Address {
    state_: String
  }
  type Profile {
    id: ID
    gender: String
    demographics: Demographics
    addresses: [Address]
  }
  type Users {
    id: ID
    usersSchema: String
  }
  type Organization {
    id: ID
    name: String
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
    getDevicesCount: Int

    getProfiles: [Profile]
    findAProfile(id: String): Profile
    getGenderCount(gender: String): Int
    getRaceCount(race: String): Int

    getUsersCountByStatus(status: String): Int

    getOrganizationCount: Int

    getAudit: [Audit]
    
    getQuestionnaires: [Questionnaire]
    findAQuestionnaire(id: String): Questionnaire
    getQuestionnaireCount: Int
    getQuestionnaireCountByTitle(title: String): Int
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