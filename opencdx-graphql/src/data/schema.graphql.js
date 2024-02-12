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
  
  type Audit {
    purposeOfUse: String
  }

  type Query {
    getManufacturers: [Manufacturer]
    findAManufacturer(id: String): Manufacturer

    getDevices: [Device]
    findADevice(id: String): Device

    getPatients: [Patient]
    findAPatient(id: String): Patient

    getAudit: [Audit]
  }
`;