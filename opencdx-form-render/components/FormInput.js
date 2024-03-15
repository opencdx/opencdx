import React from 'react';

import { View, TextInput as RNTextInput, TextInputProps as RNTextInputProps, Text, StyleSheet } from 'react-native';
import { useController, useFormContext } from 'react-hook-form';
import { TextInput } from 'react-native-paper';
import { DefaultTheme } from 'react-native-paper';

const ControlledInput = (props) => {
    const formContext = useFormContext();
    const { formState } = formContext;
    const { name, label, defaultValue, ...inputProps } = props;
    const { field } = useController({ name, defaultValue });
    const hasError = Boolean(formState?.errors[name]);
    return (
        <View style={styles.container}>
            <View>
                <TextInput
                    style={styles.input}
                    autoCapitalize="none"
                    textAlign="left"
                    onChangeText={field.onChange}
                    value={field.value}
                    mode="outlined"
                    label={label}
                    outlineColor="gray"
                    activeOutlineColor="gray"
                    theme={theme}
                    {...inputProps}
                />
                { hasError &&
                <View style={styles.errorContainer}>
                    <Text style={styles.error}>{formState.errors[name].message}</Text>
                </View>
                }
            </View>
        </View>
    );
}
export const FormInput = (props) => {
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
        fontSize: 16,
    },
    container: {
        flex: -1,
        justifyContent: 'center',
    },
    input: {
        fontSize: 16,
        height: 42,
    },
    errorContainer: {
        flex: -1,
        height: 25
    },
    error: {
        color: 'red'
    }
});

const theme = {
    ...DefaultTheme,
    colors: {
        ...DefaultTheme.colors,
        background: 'white',
    },
};