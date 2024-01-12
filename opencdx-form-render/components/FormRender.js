import React, { useState } from 'react';
import { TextInput } from './TextInput'
import { Text, View, StyleSheet, Button } from 'react-native';
import { Radio, RadioGroup, HStack, RadioIcon, CircleIcon, RadioLabel, RadioIndicator, Select, SelectTrigger, SelectInput, SelectIcon, SelectPortal, SelectBackdrop, SelectContent, SelectDragIndicatorWrapper, SelectDragIndicator, SelectItem, Icon, ChevronDownIcon, FormControlLabel, FormControlLabelText
    
 } from '@gluestack-ui/themed';
import { useForm, FormProvider } from 'react-hook-form';
import Constants from 'expo-constants';
import formData from './alpha.json';


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
export default function App() {
    const { ...methods } = useForm({ mode: 'onChange' });

    const onSubmit = (data) => console.log({ data });

    const [formError, setError] = useState(false)

    return (
        <View style={styles.container}>
            {formError ? <View><Text style={{ color: 'red' }}>There was a problem with loading the form. Please try again later.</Text></View> :
                <>
                    <FormProvider {...methods}>
                        <Text>{formData?.title}</Text>
                        {formData?.item?.map((field, index) => {
                        
                            return (
                                <View>
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
                                                <HStack style={styles.margin}>
                                                    <RadioGroup
                                                        key={index}
                                                        name={field.linkId}
                                                        flexDirection="row"
            
                                                        sx={{
                                                            flexDirection: 'row',
                                                        }}
                                                        value={field.value}
                                                        onValueChange={(value) => {
                                                            // Handle radio group value change here
                                                            // You can use the 'value' to update the state or perform any other logic
                                                            console.log(value);
                                                        }}
                                                    >
                                                        {answerOption?.map((option) => (
                                                            <Radio key={option.id} value={option.value}>
                                                                <RadioIndicator _checked={{ bg: 'primary.500' }} margin={2}>
                                                                    <RadioIcon as={CircleIcon} />
                                                                </RadioIndicator>
                                                                <RadioLabel>{option.label}</RadioLabel>
                                                            </Radio>
                                                        ))}
                                                    </RadioGroup>
                                                </HStack>
                                            ) ||
                                            field.type === "choice" && (
                                                <Select style={styles.margin}>
                                                    <SelectTrigger variant="outline" size="md">
                                                        <SelectInput placeholder="Select option" />
                                                        <SelectIcon mr="$3">
                                                            <Icon as={ChevronDownIcon} />
                                                        </SelectIcon>
                                                    </SelectTrigger>
                                                    <SelectPortal>
                                                        <SelectBackdrop />
                                                        <SelectContent>
                                                            <SelectDragIndicatorWrapper>
                                                                <SelectDragIndicator />
                                                            </SelectDragIndicatorWrapper>
                                                            {field.answerOption.map((option) => {
                                                                return (
                                                                    <SelectItem label={option.valueCoding.display} value={option.valueCoding.display} />
                                                                )
                                                            })}
                                                        </SelectContent>
                                                    </SelectPortal>
                                                </Select>
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
                    title="Login"
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