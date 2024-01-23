import React, { useState } from 'react';
import { TextInput } from './TextInput'
import { RadioInput } from './RadioInput'
import { SelectInputComp } from './SelectInputComp'
import { Text, View, StyleSheet, Button } from 'react-native';
import {
    FormControlLabel, FormControlLabelText, Heading
} from '@gluestack-ui/themed';
import { useForm, FormProvider } from 'react-hook-form';
import Constants from 'expo-constants';
import formData from './alpha.json';


export default function App() {
    const { ...methods } = useForm({ mode: 'onChange' });

    const onSubmit = (data) => console.log({ data });

    const [formError, setError] = useState(false)

    return (
        <View style={styles.container}>
            {formError ? <View><Text style={{ color: 'red' }}>There was a problem with loading the form. Please try again later.</Text></View> :
                <>
                    <FormProvider {...methods}>
                        <Heading>{formData?.title}</Heading>
                        {formData?.item?.map((field, index) => {

                            return (
                                <View style={styles.margin}>
                                    <FormControlLabel>
                                        <FormControlLabelText>{field.text}</FormControlLabelText>
                                    </FormControlLabel>
                                    {
                                        field.type === "integer" && (

                                            <TextInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="number"
                                            />
                                        )
                                        ||
                                        field.type === "string" && (
                                            <TextInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="text"
                                            />
                                        ) ||
                                        field.type === "text" && (
                                            <TextInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="text"
                                            />
                                        ) ||
                                        field.type === "boolean" && (
                                            <RadioInput
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="radio"
                                            />
                                        ) ||
                                        field.type === "choice" && (
                                            <SelectInputComp
                                                key={index}
                                                name={field.linkId}
                                                label={field.text}
                                                rules={{ required: 'This field is required!' }}
                                                setFormError={setError}
                                                type="select"
                                                answerOption={field.answerOption}
                                            />
                                        )
                                    }

                                </View>

                            );
                        }
                        )}

                    </FormProvider>
                </>
            }
            <View style={styles.button}>
                <Button
                    title="Submit"
                    onPress={methods.handleSubmit(onSubmit)}
                />
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