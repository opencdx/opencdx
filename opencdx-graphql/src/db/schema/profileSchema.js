"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.profileSchema = void 0;
const mongoose = require('mongoose');

const Demographics = new mongoose.Schema({
    ethnicity_: {
        type: String
    },
    race_: {
        type: String
    },
    nationality_: {
        type: String
    }
});

const Address = new mongoose.Schema({
    state_: {
        type: String
    }
});

exports.profileSchema = new mongoose.Schema({
    gender: {
        type: String
    },
    demographics: {
        type: Demographics
    },
    addresses: {
        type: [Address]
    }

}, { collection: 'profiles'});
