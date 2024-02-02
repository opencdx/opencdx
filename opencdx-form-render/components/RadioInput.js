import React from 'react';
import { View, TextInput as RNTextInput, StyleSheet } from 'react-native';
import { RadioGroup, Radio, RadioIndicator, RadioLabel, RadioIcon, CircleIcon, HStack } from '@gluestack-ui/themed';
import { useController, useFormContext } from 'react-hook-form';

const answerOption = [
  {
    id: 1,
    label: 'Yes',
    value: 'yes',
  },
  {
    id: 2,
    label: 'No',
    value: 'no',
  },
  {
    id: 3,
    label: 'Not Answered',
    value: 'not answered',
  }
];

const ControlledInput = (props) => {
  const formContext = useFormContext();
  const { formState } = formContext;

  const {
    name,
    label,
    defaultValue,
    ...inputProps
  } = props;

  const { field } = useController({ name, defaultValue });

  const hasError = Boolean(formState?.errors[name]);

  return (
    <View style={styles.container}>
      <View>
        <HStack style={styles.margin}>
          <RadioGroup
            style={styles.input}
            onChange={field.onChange}
            value={field.value}
            {...inputProps}
            sx={{
              flexDirection: 'row',
            }}
          >
            {answerOption?.map((option) => (
              <Radio key={option.id} value={option.value}>
                <RadioIndicator _checked={{ bg: 'primary.500' }} margin={2}>
                  <RadioIcon as={CircleIcon} />
                </RadioIndicator>
                <RadioLabel style={styles.label}>{option.label}</RadioLabel>
              </Radio>
            ))}
          </RadioGroup>
        </HStack>
      </View>
    </View>
  );
}

export const RadioInput = (props) => {
  const {
    name,
    rules,
    label,
    defaultValue,
    setFormError,
    ...inputProps
  } = props;

  const formContext = useFormContext();

  if (!formContext || !name) {
    const msg = !formContext ? "TextInput must be wrapped by the FormProvider" : "Name must be defined"
    setFormError(true)
    return null
  }

  return <ControlledInput {...props} />;
};

const styles = StyleSheet.create({
  label: {},
  container: {
    flex: -1,
    justifyContent: 'center',
  },
  input: {
    margin: 10,
    borderRadius: 5,
  },
  errorContainer: {
    flex: -1,
    height: 25
  },
  error: {
    color: 'red'
  },
  label: {
    marginRight: 5,
  },
});