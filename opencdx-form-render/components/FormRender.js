import React from 'react';
import { useState } from 'react';
import { StyleSheet } from 'react-native';
import { Button, ButtonText, Input, InputField, Radio, RadioGroup, RadioIndicator, RadioIcon, CircleIcon, HStack, RadioLabel, Select, SelectTrigger, SelectItem, SelectIcon, SelectContent, SelectPortal, SelectDragIndicatorWrapper, SelectBackdrop, SelectInput, Icon, ChevronDownIcon, SelectDragIndicator, FormControl, FormControlLabel, FormControlLabelText, Heading } from '@gluestack-ui/themed';
import formData from './alpha.json';

const FormRender = React.forwardRef((props, ref) => {
    const [cuff, setCuff] = useState();
    const [urinated, setUrinated] = useState();
    const [sitting, setSitting] = useState();

    return(
        <FormControl minWidth="$80">
            <Heading>{formData?.title}</Heading>
            { formData?.item?.map((question, index) => {
                return(
                    <>
                    <FormControlLabel>
                        <FormControlLabelText>{question.text}</FormControlLabelText>
                    </FormControlLabel>

                    { question.type === "integer" &&
                        <Input
                            style={styles.margin}
                            variant="outline"
                            size="md"
                            isDisabled={false}
                            isInvalid={false}
                            isReadOnly={false}
                        >
                            <InputField placeholder="Enter Text here" keyboardType="numeric"/>
                        </Input>
                    }

                    { question.type === "string" &&
                        <Input
                            style={styles.margin}
                            variant="outline"
                            size="md"
                            isDisabled={false}
                            isInvalid={false}
                            isReadOnly={false}
                        >
                            <InputField placeholder="Enter Text here" />
                        </Input>
                    }

                    { question.type === "text" &&
                        <Input
                            style={styles.margin}
                            variant="outline"
                            size="md"
                            isDisabled={false}
                            isInvalid={false}
                            isReadOnly={false}
                        >
                            <InputField placeholder="Enter Text here" />
                        </Input>
                    }

                    { question.type === "boolean" &&
                        <RadioGroup value={cuff} onChange={setCuff} style={styles.margin}>
                            <HStack space="2xl">
                                <Radio value="yes">
                                <RadioIndicator mr="$2">
                                    <RadioIcon as={CircleIcon} />
                                </RadioIndicator>
                                <RadioLabel>Yes</RadioLabel>
                                </Radio>
                                <Radio value="no">
                                <RadioIndicator mr="$2">
                                    <RadioIcon as={CircleIcon} />
                                </RadioIndicator>
                                <RadioLabel>No</RadioLabel>
                                </Radio>
                                <Radio value="notAnswered">
                                <RadioIndicator mr="$2">
                                    <RadioIcon as={CircleIcon} />
                                </RadioIndicator>
                                <RadioLabel>Not Answered</RadioLabel>
                                </Radio>
                            </HStack>
                        </RadioGroup>
                    }

                    { question.type === "choice" &&
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
                                {question.answerOption.map((option) => {
                                    return(
                                        <SelectItem label={option.valueCoding.display} value={option.valueCoding.display} />
                                    )
                                })}
                                </SelectContent>
                            </SelectPortal>
                        </Select>
                    }
                    </>
                )
            }) }

            <Button style={styles.margin}>
            <ButtonText>Submit</ButtonText>
            </Button>
        </FormControl>
    );
});

const styles = StyleSheet.create({
    margin: {
        margin: 5,
        marginLeft: 0,
    },
});

export default FormRender;