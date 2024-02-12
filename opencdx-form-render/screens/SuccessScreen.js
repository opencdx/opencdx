import React from 'react';
import { StyleSheet, View, Platform } from 'react-native';


const SuccessScreen = ({}) => {
    return (
        <View style={styles.container}>
            Questionnaire Data Saved
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 8,
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                margin:20,
                shadowColor: "#000",   
            },
        })),
    },
});

export default SuccessScreen;