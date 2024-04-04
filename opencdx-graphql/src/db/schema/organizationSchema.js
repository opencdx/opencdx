"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.organizationSchema = void 0;
const mongoose = require('mongoose');

exports.organizationSchema = new mongoose.Schema({
    name: {
        type: String
    }

}, { collection: 'organization'});
