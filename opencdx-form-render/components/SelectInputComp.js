import React from 'react';
import { View, StyleSheet } from 'react-native';
import {
  Select,
  SelectTrigger,
  SelectInput,
  SelectIcon,
  SelectPortal,
  SelectBackdrop,
  SelectContent,
  SelectDragIndicatorWrapper,
  SelectDragIndicator,
  SelectItem,
  Icon,
  ChevronDownIcon,
} from '@gluestack-ui/themed';
import { useController, useFormContext } from 'react-hook-form';


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


  return (
    <View style={styles.container}>
      <View>
        <Select style={styles.margin}

          onValueChange={field.onChange}
          onChange={field.onChange}
          value={field.value}

        >
          <SelectTrigger variant="outline" size="md">
            <SelectInput placeholder="Select option"



            />
            <SelectIcon mr="$3">
              <Icon as={ChevronDownIcon} />
            </SelectIcon>
          </SelectTrigger>
          <SelectPortal>
            <SelectBackdrop />
            <SelectContent



            >
              <SelectDragIndicatorWrapper>
                <SelectDragIndicator />
              </SelectDragIndicatorWrapper>
              {props.answerOption.map((option) => {
                return (
                  <SelectItem label={option.valueCoding.display} value={option.valueCoding.display} />
                )
              })}
            </SelectContent>
          </SelectPortal>
        </Select>
      </View>
    </View>
  );
}

export const SelectInputComp = (props) => {
  const {
    name,
    rules,
    label,
    defaultValue,
    setFormError,
    ...inputProps
  } = props;

  const formContext = useFormContext();

  // Placeholder until input name is initialized
  if (!formContext || !name) {
    const msg = !formContext ? "TextInput must be wrapped by the FormProvider" : "Name must be defined"
    console.error(msg)
    setFormError(true)
    return null
  }

  return <ControlledInput {...props} />;
};

const styles = StyleSheet.create({
  
});