"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.deviceSchema = void 0;
const mongoose = require('mongoose');
exports.deviceSchema = new mongoose.Schema({
    type: {
        type: String
    },
    model: {
        type: String
    },
    manufacturerId: {
        type: mongoose.Schema.ObjectId
    },
    vendorId: {
        type: mongoose.Schema.ObjectId
    },
    vendorCountryId: {
        type: mongoose.Schema.ObjectId
    },
    batchNumber: {
        type: String
    },
    serialNumber: {
        type: String
    },
    testTypeId: {
        type: String
    },
    testSensitivity: {
        type: Number
    },
    testSpecificity: {
        type: Number
    },
    storageRequirements: {
        type: String
    },
    approvalStatus: {
        type: String
    },
    url: {
        type: String
    },
    notes: {
        type: String
    },
    safety: {
        type: String
    },
    userInstructions: {
        type: String
    },
    limitations: {
        type: String
    },
    warrantyInfo: {
        type: String
    },
    intendedUseAge: {
        type: Number
    },
    fdaAuthorized: {
        type: Boolean
    },
    deviceStatus: {
        type: String
    },
    associatedSoftwareVersion: {
        type: String
    }
}, { collection: 'devices'});