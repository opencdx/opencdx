import React from 'react';

import { View, TextInput as RNTextInput, TextInputProps as RNTextInputProps, Text, StyleSheet } from 'react-native';
import { useController, useFormContext } from 'react-hook-form';
const ControlledInput = (props) => {
    const formContext = useFormContext();
    const { formState } = formContext;
    const { name, label, defaultValue, ...inputProps } = props;
    const { field } = useController({ name, defaultValue });
    const hasError = Boolean(formState?.errors[name]);
    return (
        <View style={styles.container}>
            <View>
                <RNTextInput
                    autoCapitalize="none"
                    textAlign="left"
                    style={styles.input}
                    onChangeText={field.onChange}
                    onBlur={field.onBlur}
                    value={field.value}
                    {...inputProps}
                />
                <View style={styles.errorContainer}>
                    {hasError && (<Text style={styles.error}>{formState.errors[name].message}</Text>)}
                </View>
            </View>
        </View>
    );
}
export const TextInput = (props) => {
    const { name, rules, label, defaultValue, setFormError, ...inputProps } = props;
    const formContext = useFormContext();
    if (!formContext || !name) {
        const msg = !formContext ? "TextInput must be wrapped by the FormProvider" : "Name must be defined"
        setFormError(true)
        return null
    }
    return <ControlledInput {...props} />;
};
const styles = StyleSheet.create({
    label: {
        margin: 20,
        marginLeft: 0,
    },
    container: {
        flex: -1,
        justifyContent: 'center',
        padding: 8,
        borderColor: 'white',
        borderWidth: 1
    },
    input: {
        borderColor: 'gray',
        borderWidth: 1,
        padding: 10,
        borderRadius: 5,
    },
    errorContainer: {
        flex: -1,
        height: 25
    },
    error: {
        color: 'red'
    }
});