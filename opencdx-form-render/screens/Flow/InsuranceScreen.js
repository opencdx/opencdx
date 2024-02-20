import React from 'react';
import { StyleSheet } from 'react-native';
import { Platform } from 'react-native';
import { Button, ButtonText, ButtonIcon } from '@gluestack-ui/themed';
import { Heading } from '@gluestack-ui/themed';
import { ArrowRightIcon } from '@gluestack-ui/themed';
import { View } from 'react-native';
import { SafeAreaView } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { Ionicons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';




const InsuranceScreen = () => {


    const navigation = useNavigation();
    const handleBack = () => {
        navigation.goBack();
    };

    const insuranceOptions = [
        { id: 1, name: 'Option 1' },
        { id: 2, name: 'Option 2' },
        { id: 3, name: 'Option 3' },
    ];


    return (


        <View style={styles.container}>
            <SafeAreaView >
                <View style={styles.searchContainer}>
                    <TouchableOpacity onPress={handleBack}>
                        <Ionicons name="arrow-back" size={24} color="black" />
                    </TouchableOpacity>

                </View>
                <Heading size="lg" >Select an insurance option:</Heading>
                {insuranceOptions.map((questionnaire, index) => (
                    <Button
                        style={styles.input}
                        key={index}
                        title={questionnaire.name}
                        size="md"
                        variant="contained"
                        action="primary"
                        isDisabled={false}
                    >
                        <ButtonText>{questionnaire.name}</ButtonText>
                        <ButtonIcon as={ArrowRightIcon}
                            color="primary"
                            size="md"
                        />
                    </Button>
                ))}

            </SafeAreaView>
        </View>


    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 8,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                margin: 20,
                shadowColor: "#000",
            },
        })),
    },
    input: {
        margin: 5,
        textAlign: 'right',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderRadius: 68,


    },

    searchContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: 10,
    },


});

export default InsuranceScreen;
