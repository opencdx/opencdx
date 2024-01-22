const mongoose = require('mongoose');
const { environment } = require('../config/config');
const { manufacturerSchema } = require('./schema/manufacturerSchema.js');
const { deviceSchema } = require('./schema/deviceSchema.js');
const { patientSchema } = require('./schema/patientSchema.js');

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
const Patients = mongoose.model('Patients', patientSchema);

export { Manufacturers, Devices, Patients };