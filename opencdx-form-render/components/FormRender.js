import React, { useState, useEffect } from 'react';
import { TextInput } from './TextInput'
import { RadioInput } from './RadioInput'
import { SelectInputComp } from './SelectInputComp'
import { Text, View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, ButtonText, FormControlLabel, FormControlLabelText, Heading } from '@gluestack-ui/themed';
import { useForm, FormProvider } from 'react-hook-form';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from '../utils/axios';


export default function App({ questionnaire, navigation}) {
    const { ...methods } = useForm({ mode: 'onChange' });

    const onSubmit = (data) => {
        questionnaire.item.forEach((field, index) => {
                field.item.forEach((connector) => {
                    if (
                        connector &&
                        connector.anfStatementConnector[0] &&
                        connector.anfStatementConnector[0].anfStatement &&
                        connector.anfStatementConnector[0].anfStatement.circumstanceChoice
                    ) {
                        connector.anfStatementConnector[0].anfStatement.circumstanceChoice.result.resolution = data[field.linkId];

                    }
                });
            
            switch (field.type) {
                case 'integer':
                    field.answerInteger = data[field.linkId];
                    break;
                case 'string':
                    field.answerString = data[field.linkId];
                    break;
                case 'text':
                    field.answerText = data[field.linkId];
                    break;
                case 'boolean':
                    field.answerBoolean = data[field.linkId] === 'yes';
                    break;
                case 'choice':
                    field.answerString = data[field.linkId];
                    break;
                default:
                    break;
            }
        });
      
        const userQuestionnaireData = {
            "userQuestionnaireData": {
                "patientId": "65e9d6589f21925367ccef18",
                "questionnaireData": [
                    questionnaire

                ]
            }
        };
        
        const handleSave = async () => {
            console.log(userQuestionnaireData)
            const data=JSON.stringify(userQuestionnaireData);
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');

                const response = await axios.post('/questionnaire/user/questionnaire', data, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${jwtToken}`,
                    },
                });
                console.log(response)
                navigation.navigate('Success');
            } catch (error) {
                console.log(error);
                alert( error);
            }
        };
        handleSave();
    };


    const [formError, setError] = useState(false)

    return (
        <View style={styles.container}>
            {formError ? (
                <View>
                    <Text style={{ color: 'red' }}>There was a problem with loading the form. Please try again later.</Text>
                </View>
            ) : (
                <>
                    <FormProvider {...methods}>
                        <Text style={styles.heading} >{questionnaire?.title}</Text>
                        {questionnaire?.item?.map((field, index) => {
                            let inputComponent;

                            switch (field.type) {
                                case "integer":
                                    inputComponent = (
                                        <>
                                            <TextInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="number"
                                            />
                                            <View style={styles.divider} />
                                        </>
                                    );
                                    break;
                                case "string":
                                    inputComponent = (
                                        <>
                                            <TextInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="text"
                                            />
                                            <View style={styles.divider} />
                                        </>
                                    );
                                    break;
                                case "text":
                                    inputComponent = (
                                        <>
                                            <TextInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="text"
                                            />
                                            <View style={styles.divider} />
                                        </>
                                    );
                                    break;
                                case "boolean":
                                    inputComponent = (
                                        <>
                                            <RadioInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="radio"
                                            />
                                            <View style={styles.divider} />
                                        </>
                                    );
                                    break;
                                case "choice":
                                    inputComponent = (
                                        <>
                                            <SelectInputComp
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="select"
                                                answerOption={field.answerOption}
                                            />
                                            <View style={styles.divider} />
                                        </>
                                    );
                                    break;
                                default:
                                    inputComponent = null;
                            }

                            return (
                                <View key={index}>
                                    <FormControlLabel>
                                        <FormControlLabelText>{field.text}</FormControlLabelText>
                                    </FormControlLabel>
                                    {inputComponent}
                                </View>
                            );
                        })}

                    </FormProvider>
                </>
            )}
            <View style={styles.footer}>
                <Button
                    title="Submit"
                    onPress={methods.handleSubmit(onSubmit)}
                    style={styles.button}
                >
                    <ButtonText>Submit</ButtonText>
                </Button>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    heading: {
        fontSize: 36,
        fontWeight: 'bold',
        marginBottom: 24,
    },
    radio: {

    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
    button: {
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,
        backgroundColor: '#0066FF',
        ...Platform.select({
            web: {
                width: '90%',
            },
            default: {
                width: '90%',
            }
        })
    },
    divider: {
        borderBottomColor: '#DBDBDB',
        borderBottomWidth: 1,
        marginTop: 16,
        marginBottom: 16,

    },
    container: {
        flex: 1,
        justifyContent: 'center',

        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },

});