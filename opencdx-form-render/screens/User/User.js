import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { View, StyleSheet, Platform } from 'react-native';
import { Text } from 'react-native-paper';
import { TouchableOpacity } from 'react-native-gesture-handler';

const User = ({navigation}) => {
    const [buttonTitles, setButtonTitles] = useState([{
        "resourceType": "Questionnaire",
        "title": "BP-POC",
        "status": "draft",
        "item": [
            {
                "type": "integer",
                "extension": [
                    {
                        "url": "http://hl7.org/fhir/StructureDefinition/questionnaire-unit",
                        "valueCoding": {
                            "code": "millimeter of mercury",
                            "display": "millimeter of mercury"
                        }
                    }
                ],
                "linkId": "3079919224534",
                "text": "Upper Range (SYSTOLIC)",
                "required": false,
                "repeats": false,
                "readOnly": false,
                "item": [
                    {
                        "text": "ANF Main Statement",
                        "type": "display",
                        "linkId": "3079919224534_helpText",
                        "extension": [
                            {
                                "url": "http://hl7.org/fhir/StructureDefinition/questionnaire-itemControl",
                                "valueCodeableConcept": {
                                    "text": "Help-Button",
                                    "coding": [
                                        {
                                            "code": "help",
                                            "display": "Help-Button",
                                            "system": "http://hl7.org/fhir/questionnaire-item-control"
                                        }
                                    ]
                                }
                            }
                        ]
                    }
                ]
            },
            {
                "type": "boolean",
                "linkId": "7048556169730",
                "text": "Measurement taken using cuff",
                "required": false,
                "repeats": false,
                "readOnly": false,
                "initial": [
                    {
                        "valueBoolean": true
                    }
                ]
            },
            {
                "type": "choice",
                "extension": [
                    {
                        "url": "http://hl7.org/fhir/StructureDefinition/questionnaire-itemControl",
                        "valueCodeableConcept": {
                            "coding": [
                                {
                                    "system": "http://hl7.org/fhir/questionnaire-item-control",
                                    "code": "drop-down",
                                    "display": "Drop down"
                                }
                            ]
                        }
                    }
                ],
                "linkId": "8501933950742",
                "text": "Cuff Size",
                "required": false,
                "repeats": false,
                "readOnly": false,
                "answerOption": [
                    {
                        "valueCoding": {
                            "display": "Adult"
                        },
                        "initialSelected": true
                    },
                    {
                        "valueCoding": {
                            "display": "Pediatric"
                        }
                    }
                ]
            },
            {
                "type": "choice",
                "extension": [
                    {
                        "url": "http://hl7.org/fhir/StructureDefinition/questionnaire-itemControl",
                        "valueCodeableConcept": {
                            "coding": [
                                {
                                    "system": "http://hl7.org/fhir/questionnaire-item-control",
                                    "code": "drop-down",
                                    "display": "Drop down"
                                }
                            ]
                        }
                    }
                ],
                "linkId": "1451784149570",
                "text": "Arm Used",
                "required": false,
                "repeats": false,
                "readOnly": false,
                "answerOption": [
                    {
                        "valueCoding": {
                            "display": "Right"
                        },
                        "initialSelected": true
                    },
                    {
                        "valueCoding": {
                            "display": "Left"
                        }
                    }
                ]
            },
            {
                "type": "boolean",
                "linkId": "4051866307723",
                "text": "Please confirm you have not urinated more than 30 minutes prior to taking the measurement.",
                "required": false,
                "repeats": false,
                "readOnly": false,
                "initial": [
                    {
                        "valueBoolean": true
                    }
                ]
            },
            {
                "type": "boolean",
                "linkId": "2602220218173",
                "text": "Please confirm you have been in sitting position before taking the measurement for at least 5 minutes.",
                "required": false,
                "repeats": false,
                "readOnly": false,
                "initial": [
                    {
                        "valueBoolean": true
                    }
                ]
            }
        ]
    }]);
    useEffect(() => {
        const fetchQuestionnaireList = async () => {
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');
                const response = await axios.post(
                    '/questionnaire/questionnaire/list',
                    {
                        pagination: {
                            pageSize: 30,
                            sortAscending: true,
                        },
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${jwtToken}`,
                        },
                    }
                );
                const { questionnaires } = response.data;
                setButtonTitles(questionnaires);
            } catch (error) {
                console.error(error);
            }
        };

        fetchQuestionnaireList();
    }, []);
    return (
        <View style={styles.container}>
            {buttonTitles.map((questionnaire, index) => (

                 <TouchableOpacity 
                    onPress={() => navigation.navigate('ListQuestion', { questionnaire: questionnaire })}>
                        <View style={styles.sectionWrapper}>
                        <Text variant='titleMedium' key={index}>{questionnaire.title}</Text>
                        </View>
                        </TouchableOpacity>
            ))}

        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        marginTop: 100,
        ...Platform.select({
            web: {
                width: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    popularTest: {
        backgroundColor: '#ebf0f5',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'left',
        paddingLeft: 10,
        paddingVertical: 15,
    },
    popularText: {

        fontWeight: 'bold',
        ...Platform.select({
            web: {
                fontSize: 15,
            },
            default: {
                fontSize: 24,
            }
        })
    },

    sectionWrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#f0f0f0',
        backgroundColor: 'paleturquoise',
        padding: 10,
        margin: 5,
    },
    leftSection: {
        padding: 10,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',


    },
    rightSection: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',



    },
    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,

    },
    searchInput: {
        flex: 1,
        marginLeft: 10,
        height: 40,
        borderColor: 'white',
        borderWidth: 1,
        borderRadius: 5,
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 10,
        paddingHorizontal: 10,
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                width: 400,
            },
            default: {
                width: '100%',
            }
        })
    },
});



export default User;
