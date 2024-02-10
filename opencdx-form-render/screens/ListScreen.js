import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Platform } from 'react-native';
import axios from '../utils/axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, Heading, ButtonText } from '@gluestack-ui/themed';

const ListScreen = ({ navigation }) => {
    const [buttonTitles, setButtonTitles] = useState([]);

    useEffect(() => {
        const fetchQuestionnaireList = async () => {
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');

                // const response = await axios.post(
                //     '/questionnaire/questionnaire/list',
                //     {
                //         pagination: {
                //             pageSize: 30,
                //             sortAscending: true,
                //         },
                //     },
                //     {
                //         headers: {
                //             'Content-Type': 'application/json',
                //             Authorization: `Bearer ${jwtToken}`,
                //         },
                //     }
                // );
                // const { questionnaires } = response.data;
                // setButtonTitles(questionnaires);
                setButtonTitles([{
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
            } catch (error) {
                console.error(error);
            }
        };

        fetchQuestionnaireList();
    }, []);

    return (
        <View style={styles.container}>
            <Heading>Select a questionnaire:</Heading>
            {buttonTitles.map((questionnaire, index) => (
                <Button
                    style={styles.input}
                    key={index}
                    title={questionnaire.title}
                    onPress={() => navigation.navigate('Home', { questionnaire })}
                >
                    <ButtonText>{questionnaire.title}</ButtonText>
                </Button>
            ))}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 8,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                margin:20,
                shadowColor: "#000",   
            },
        })),
    },
    input: {
        margin: 5,
        textAlign: 'right'
    },
});

export default ListScreen;
