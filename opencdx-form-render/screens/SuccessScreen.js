import React from 'react';
import { StyleSheet, View, Platform } from 'react-native';
import { Button, ButtonText } from '@gluestack-ui/themed';
import { Text } from 'react-native';
import Ionicons from '@expo/vector-icons/Ionicons';


const SuccessScreen = ({ navigation }) => {
    return (
        <View style={styles.container}>
            <Ionicons name="md-checkmark-circle" size={120} color="green" />
            <Text style={styles.textTitle}> Success</Text>
            <Text style={styles.text}> Questionnaire Data Saved</Text>
            <Button
                style={styles.input}
                onPress={() => navigation.navigate('List')}
                size="md"
                variant='outlined'
                action="primary"
                isDisabled={false}
            >
                <ButtonText> Go Home</ButtonText>
            </Button>

        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 8,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                shadowColor: "#000",
            },
        })),
    },
    input: {
        textAlign: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 10,
        width: '30%',
    },
    successIcon: {
        color: 'green',
        justifyContent: 'center',
        alignItems: 'center',
    },
    text: {
        fontSize: 14,
        marginBottom: 20,
        textAlign: 'center',
    },
    textTitle: {
        fontSize: 36,
        fontWeight: 'bold',
    },

});

export default SuccessScreen;