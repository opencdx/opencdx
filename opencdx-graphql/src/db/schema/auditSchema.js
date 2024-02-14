"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.auditSchema = void 0;
const mongoose = require('mongoose');

const Actor = new mongoose.Schema({
    identity_: {
        type: String
    },
    role_: {
        type: String
    },
    networkAddress_: {
        type: String
    },
    agentType_: {
        type: Number
    },
    memoizedIsInitialized: {
        type: Number
    },
    
    memoizedSize: {
        type: Number
    },
    memoizedHashCode: {
        type: Number
    },
    _class: {
        type: String
    }
});
const DataObject = new mongoose.Schema({
    resource_: {
        type: String
    },
    data_: {
        type: String
    },
    sensitivity_: {
        type: Number
    },
    memoizedIsInitialized: {
        type: Number
    },
   
    memoizedSize: {
        type: Number
    },
    memoizedHashCode: {
        type: Number
    },
    _class: {
        type: String
    }
});
const AuditSource = new mongoose.Schema({
    systemInfo_: {
        type: String
    },
    configuration_: {
        type: String
    },
    memoizedIsInitialized: {
        type: Number
    },
   
    memoizedSize: {
        type: Number
    },
    memoizedHashCode: {
        type: Number
    },
    _class: {
        type: String
    }
});
const AuditEntity = new mongoose.Schema({
    patientIdentifier_: {
        type: String
    },
    userIdentifier_: {
        type: String
    },
    memoizedIsInitialized: {
        type: Number
    },
    unknownFields: {
        type: Object
    },
    memoizedSize: {
        type: Number
    },
    memoizedHashCode: {
        type: Number
    },
    _class: {
        type: String
    }
});

exports.auditSchema = new mongoose.Schema({
    purposeOfUse: {
        type: String
    },
    created: {
        type: String
    },
    eventType: {
        type: String
    },
    actor: {
        type: Actor
    },
    dataObject: {
        type: DataObject
    },
    auditSource: {
        type: AuditSource
    },
    auditEntity: {
        type: AuditEntity
    },
    creator: {
        type: mongoose.Schema.ObjectId
    },
    modifier: {
        type: mongoose.Schema.ObjectId
    },
    modified: {
        type: String
    },
    _class: {
        type: String
    },

}, { collection: 'audit'});
