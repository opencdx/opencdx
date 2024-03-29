import { Manufacturers, Devices, Patients, Audit, Questionnaires } from "../db/dbConnector.js";

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
    getPatients: async (root) => {
      try {
        const patients = await Patients.find();
        return patients;
      } catch (err) {
        console.error(err)
      }
    },
    findAPatient: async (root, { id }) => {
      try {
        const patient = await Patients.findOne({ _id: id });
        return patient;
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