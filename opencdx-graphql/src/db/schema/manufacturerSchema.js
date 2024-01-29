"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.manufacturerSchema = void 0;
const mongoose = require('mongoose');
exports.manufacturerSchema = new mongoose.Schema({
    name: {
        type: String
    },
    website: {
        type: String
    },
    description: {
        type: String
    }
}, { collection: 'manufacturer'});