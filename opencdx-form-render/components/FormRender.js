import React, { useState, useEffect } from 'react';
import { TextInput } from './TextInput'
import { RadioInput } from './RadioInput'
import { SelectInputComp } from './SelectInputComp'
import { Text, View, StyleSheet } from 'react-native';
import { Button, ButtonText, FormControlLabel, FormControlLabelText, Heading } from '@gluestack-ui/themed';
import { useForm, FormProvider } from 'react-hook-form';
import Constants from 'expo-constants';

export default function App({ questionnaire }) {
    const { ...methods } = useForm({ mode: 'onChange' });

    const onSubmit = (data) => console.log({ data });

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
                        <Heading>{questionnaire?.title}</Heading>
                        {questionnaire?.item?.map((field, index) => {
                            let inputComponent;

                            switch (field.type) {
                                case "integer":
                                    inputComponent = (
                                        <TextInput
                                            key={index}
                                            name={field.linkId}
                                            label={field.text}
                                            rules={{ required: 'This field is required!' }}
                                            setFormError={setError}
                                            type="number"
                                        />
                                    );
                                    break;
                                case "string":
                                    inputComponent = (
                                        <TextInput
                                            key={index}
                                            name={field.linkId}
                                            label={field.text}
                                            rules={{ required: 'This field is required!' }}
                                            setFormError={setError}
                                            type="text"
                                        />
                                    );
                                    break;
                                case "text":
                                    inputComponent = (
                                        <TextInput
                                            key={index}
                                            name={field.linkId}
                                            label={field.text}
                                            rules={{ required: 'This field is required!' }}
                                            setFormError={setError}
                                            type="text"
                                        />
                                    );
                                    break;
                                case "boolean":
                                    inputComponent = (
                                        <RadioInput
                                            key={index}
                                            name={field.linkId}
                                            label={field.text}
                                            rules={{ required: 'This field is required!' }}
                                            setFormError={setError}
                                            type="radio"
                                        />
                                    );
                                    break;
                                case "choice":
                                    inputComponent = (
                                        <SelectInputComp
                                            key={index}
                                            name={field.linkId}
                                            label={field.text}
                                            rules={{ required: 'This field is required!' }}
                                            setFormError={setError}
                                            type="select"
                                            answerOption={field.answerOption}
                                        />
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
            <View style={styles.button}>
                <Button
                    title="Submit"
                    onPress={methods.handleSubmit(onSubmit)}
                >
                    <ButtonText>Sumit</ButtonText>
                </Button>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    button: {
        marginTop: 40,
        color: 'white',
        borderRadius: 4,
    },
    container: {
        flex: 1,
        justifyContent: 'center',
        paddingTop: Constants.statusBarHeight,
    },
    margin: {
        margin: 5,
        marginLeft: 0,
    },
});