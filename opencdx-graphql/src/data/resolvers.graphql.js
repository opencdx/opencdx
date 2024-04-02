import { Manufacturers, Devices, Profile, Audit, Questionnaires, Users, Organization } from "../db/dbConnector.js";

/**
 * GraphQL Resolvers
 **/

export const resolvers = {
  Query: {
    getManufacturers: async (root) => {
      try {
        const manufacturers = await Manufacturers.find();
        return manufacturers;
      } catch (err) {
        console.error(err)
      }
    },
    findAManufacturer: async (root, { id }) => {
      try {
        const manufacturer = await Manufacturers.findOne({ _id: id });
        return manufacturer;
      } catch (err) {
        console.error(err)
      }
    },
    getDevices: async (root) => {
      try {
        const devices = await Devices.find();
        return devices;
      } catch (err) {
        console.error(err)
      }
    },
    findADevice: async (root, { id }) => {
      try {
        const device = await Devices.findOne({ _id: id });
        return device;
      } catch (err) {
        console.error(err)
      }
    },
    getDevicesCount: async (root) => {
      try {
        const count = await Devices.countDocuments();
        return count;
      } catch (err) {
        console.error(err)
      }
    },
    getProfiles: async (root) => {
      try {
        const profiles = await Profile.find();
        return profiles;
      } catch (err) {
        console.error(err)
      }
    },
    findAProfile: async (root, { id }) => {
      try {
        const profile = await Profile.findOne({ _id: id });
        return profile;
      } catch (err) {
        console.error(err)
      }
    },
    getGenderCount: async (root, { gender }) => {
      try {
        const count = await Profile.countDocuments({ gender: gender});
        return count;
      } catch (err) {
        console.error(err)
      }
    },
    getRaceCount: async (root, { race }) => {
      try {
        const count = await Profile.countDocuments({ 'demographics.race_': race});
        return count;
      } catch (err) {
        console.error(err)
      }
    },
    getUsersCountByStatus: async (root, { status }) => {
      try {
        const users = await Users.countDocuments({status: status});
        return users;
      } catch (err) {
        console.error(err)
      }
    },
    getOrganizationCount: async (root) => {
      try {
        const organizations = await Organization.countDocuments();
        return organizations;
      } catch (err) {
        console.error(err)
      }
    },
    getAudit: async (root) => {
      try {
        const audit = await Audit.find();
        return audit;
      } catch (err) {
        console.error(err)
      }
    },
    getQuestionnaires: async (root) => {
      try {
        const questionnaires = await Questionnaires.find();
        return questionnaires;
      } catch (err) {
        console.error(err)
      }
    },
    findAQuestionnaire: async (root, { id }) => {
      try {
        const questionnaire = await Questionnaires.findOne({ _id: id });
        return questionnaire;
      } catch (err) {
        console.error(err)
      }
    },
    getQuestionnaireCount: async (root) => {
      try {
        const count = await Questionnaires.countDocuments();
        return count;
      } catch (err) {
        console.error(err)
      }
    },
    getQuestionnaireCountByTitle: async (root, { title }) => {
      try {
        const count = await Questionnaires.countDocuments({ title: title});
        return count;
      } catch (err) {
        console.error(err)
      }
    },
  },
};