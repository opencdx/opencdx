"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.patientSchema = void 0;
const mongoose = require('mongoose');
exports.patientSchema = new mongoose.Schema({
    gender: {
        type: String
    },
    firstName: {
        type: String
    },
    lastName: {
        type: String
    },
    birthDate: {
        type: String
    },
    language: {
        type: String
    },
    race : {
        type: String
    },
    ethnicity: {
        type: String
    },
    zipCode: {
        type: String
    },
    state: {
        type: String
    },
    city: {
        type: String
    },
    county: {
        type: String
    }

}, { collection: 'patient'});
