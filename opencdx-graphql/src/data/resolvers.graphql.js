import { Manufacturers, Devices } from "../db/dbConnector.js";

/**
 * GraphQL Resolvers
 **/

export const resolvers = {
  Query: {
    getManufacturers: (root) => {
      return new Promise((resolve, reject) => {
        Manufacturers.find((err, manufacturers) => {
          if (err) reject(err);
          else resolve(manufacturers);
        });
      });
    },
    findAManufacturer: (root, { id }) => {
      return new Promise((resolve, reject) => {
        Manufacturers.findOne({ _id: id }, (err, manufacturer) => {
          if (err) reject(err);
          else resolve(manufacturer);
        });
      });
    },
    getDevices: (root) => {
      return new Promise((resolve, reject) => {
        Devices.find((err, devices) => {
          if (err) reject(err);
          else resolve(devices);
        });
      });
    },
    findADevice: (root, { id }) => {
      return new Promise((resolve, reject) => {
        Devices.findOne({ _id: id }, (err, device) => {
          if (err) reject(err);
          else resolve(device);
        });
      });
    },
  },
};