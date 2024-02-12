"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.auditSchema = void 0;
const mongoose = require('mongoose');
exports.auditSchema = new mongoose.Schema({
    purposeOfUse: {
        type: String
    },
}, { collection: 'audit'});
