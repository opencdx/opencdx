import React, { useState } from 'react';
import { RadioGroup, Radio, RadioIndicator, RadioIcon, RadioLabel } from '@gluestack-ui/themed';
import { View, Text, StyleSheet, Platform, SafeAreaView } from 'react-native'; // Added import statement for StyleSheet module
import { useNavigation } from '@react-navigation/native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { Ionicons } from '@expo/vector-icons';



const PaymentMethodScreen = () => {
    const navigation = useNavigation();
    const handleBack = () => {
        navigation.goBack();
    };
    return (
        <View style={styles.container}>
            <SafeAreaView style={styles.container}>
            <View style={styles.searchContainer}>
                    <TouchableOpacity onPress={handleBack}>
                        <Ionicons name="arrow-back" size={24} color="black" />
                    </TouchableOpacity>
                    <Text style={styles.testDescription}>Select Payment Method</Text>

                </View>
            <View style={styles.body}>
                <RadioGroup>
                    <Radio value="change" size="md" isInvalid={false} isDisabled={false}>
                        <RadioIndicator mr="$2">
                            <RadioIcon strokeWidth={1} />
                        </RadioIndicator>
                        <RadioLabel>Cash On Delivery</RadioLabel>
                    </Radio>
                    <Radio value="change" size="md" isInvalid={false} isDisabled={false}>
                        <RadioIndicator mr="$2">
                            <RadioIcon strokeWidth={1} />
                        </RadioIndicator>
                        <RadioLabel>Credit Card</RadioLabel>
                    </Radio>

                    <Radio value="change" size="md" isInvalid={false} isDisabled={false}>
                        <RadioIndicator mr="$2">
                            <RadioIcon strokeWidth={1} />
                        </RadioIndicator>

                        <RadioLabel>Apple Pay </RadioLabel>
                    </Radio>
                    <Radio value="change" size="md" isInvalid={false} isDisabled={false}>
                        <RadioIndicator mr="$2">
                            <RadioIcon strokeWidth={1} />
                        </RadioIndicator>

                        <RadioLabel>Use my insurance</RadioLabel>
                    </Radio>
                </RadioGroup>
            </View>
            </SafeAreaView>

        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        padding: 10,
        ...Platform.select({
            web: {
                width: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                padding: 20,
                }
        })
    },
    testDescription: {
        padding: 10,
        border: '1px solid lightgray',
    },
    searchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,

    },
});


export default PaymentMethodScreen;
