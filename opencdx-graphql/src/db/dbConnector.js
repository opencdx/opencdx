import { questionnaireSchema } from './schema/questionnaireSchema.js';

const mongoose = require('mongoose');
const { environment } = require('../config/config');
const { manufacturerSchema } = require('./schema/manufacturerSchema.js');
const { deviceSchema } = require('./schema/deviceSchema.js');
const { profileSchema } = require('./schema/profileSchema.js');
const { usersSchema } = require('./schema/usersSchema.js');
const { organizationSchema } = require('./schema/organizationSchema.js');
const { auditSchema } = require('./schema/auditSchema.js');

const env = process.env.NODE_ENV || "development";

/**
 * Mongoose Connection
**/

mongoose.connect(environment[env].dbString, {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

let db = mongoose.connection;
db.on('error', () => {
    console.error("Error while connecting to DB");
});

const Manufacturers = mongoose.model('Manufacturers', manufacturerSchema);
const Devices = mongoose.model('Devices', deviceSchema);
const Profile = mongoose.model('Patients', profileSchema);
const Users = mongoose.model('Users', usersSchema)
const Organization = mongoose.model('Organization', organizationSchema)
const Audit = mongoose.model('Audit',auditSchema)
const Questionnaires = mongoose.model('Questionnaire', questionnaireSchema)

export { Manufacturers, Devices, Profile, Users, Organization, Audit, Questionnaires };