"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.questionnaireSchema = void 0;
const mongoose = require('mongoose');

const Item = new mongoose.Schema({
    type_: {
        type: String
    },
    required_: {
        type: Boolean
    },
    linkId_: {
        type: String
    }
});

exports.questionnaireSchema = new mongoose.Schema({
    resourceType: {
        type: String
    },
    title: {
        type: String
    },
    status: {
        type: String
    },
    description: {
        type: String
    },
    items: {
        type: [Item]
    }
}, { collection: 'questionnaire'});