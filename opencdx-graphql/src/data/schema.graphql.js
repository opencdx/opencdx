import { gql } from "apollo-server-express";

export const typeDefs = gql`
  type Manufacturer {
    id: ID
    name: String
    website: String
    description: String
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

  type Query {
    getManufacturers: [Manufacturer]
    findAManufacturer(id: String): Manufacturer

    getDevices: [Device]
    findADevice(id: String): Device
  }
`;