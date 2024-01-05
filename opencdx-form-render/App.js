import { StyleSheet, View } from 'react-native';
import Constants from 'expo-constants';
import { useState } from 'react';
import { GluestackUIProvider, Button, ButtonText, Text, Input, InputField, Radio, RadioGroup, RadioIndicator, RadioIcon, CircleIcon, HStack, RadioLabel, Select, SelectTrigger, SelectItem, SelectIcon, SelectContent, SelectPortal, SelectDragIndicatorWrapper, SelectBackdrop, SelectInput, Icon, ChevronDownIcon, SelectDragIndicator, FormControl, FormControlLabel, FormControlLabelText } from '@gluestack-ui/themed';
import { config } from '@gluestack-ui/config';

export default function App() {
  const [cuff, setCuff] = useState();
  const [urinated, setUrinated] = useState();
  const [sitting, setSitting] = useState();

  return (
    <View style={styles.container}>
      <GluestackUIProvider config={config}>
        <FormControl minWidth="$80">
        <FormControlLabel>
          <FormControlLabelText>Upper Range (SYSTOLIC)</FormControlLabelText>
        </FormControlLabel>
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

        <FormControlLabel>
          <FormControlLabelText>Measurement taken using cuff</FormControlLabelText>
        </FormControlLabel>
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

        <FormControlLabel>
          <FormControlLabelText>Cuff Size</FormControlLabelText>
        </FormControlLabel>
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
              <SelectItem label="Adult" value="adult" />
              <SelectItem label="Pediatric" value="pediatric" />
            </SelectContent>
          </SelectPortal>
        </Select>

        <FormControlLabel>
          <FormControlLabelText>Arm Used</FormControlLabelText>
        </FormControlLabel>
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
              <SelectItem label="Right" value="right" />
              <SelectItem label="Left" value="left" />
            </SelectContent>
          </SelectPortal>
        </Select>

        <FormControlLabel>
          <FormControlLabelText>Please confirm you have not urinated more than 30 minutes prior to taking the measurement.</FormControlLabelText>
        </FormControlLabel>
        <RadioGroup value={urinated} onChange={setUrinated} style={styles.margin}>
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

        <FormControlLabel>
          <FormControlLabelText>Please confirm you have been in sitting position before taking the measurement for at least 5 minutes.</FormControlLabelText>
        </FormControlLabel>
        <RadioGroup value={sitting} onChange={setSitting} style={styles.margin}>
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

        <Button style={styles.margin}>
          <ButtonText>Submit</ButtonText>
        </Button>
        </FormControl>
      </GluestackUIProvider>
    </View>
  );
}

const styles = StyleSheet.create({
  margin: {
    margin: 5,
    marginLeft: 0,
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    paddingTop: Constants.statusBarHeight,
    padding: 8,
    maxWidth: 500,
  },
});
