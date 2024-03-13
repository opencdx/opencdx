import React from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';

import { Button, Text } from 'react-native-paper';
const TestRecord = ({ navigation }) => {
    return (
        <SafeAreaView style={styles.container}>
            <View >
                <View style={styles.section}>
                    <Text variant='titleMedium' style={styles.paragraph}>Test Name: COVID-19 PCR Test</Text>
                    <Text variant='titleMedium' style={styles.paragraph}>Manufacturer: Abbott</Text>
                    <Text variant='titleMedium' style={styles.paragraph}>Collection Date: 2024-03-03</Text>
                    <Text variant='titleMedium' style={styles.paragraph}>Status: Completed</Text>
                    <Text variant='titleMedium' style={styles.paragraph}>Result: Negative</Text>
                    <Text variant='titleMedium' style={styles.paragraph}>Result Summary: SarsCoV2 Not Detected</Text>

                </View>
                <View style={styles.inter}>
                </View>


            </View>

            {/* Add missing styles */}
            <View style={styles.footer}>
                <Button mode="contained-tonal" width='100%' title="Sign In" style={styles.button} onPress={() => navigation.navigate('List')}>
                    Back               </Button>



            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    inter: {
        padding: 10,
        backgroundColor: 'white',

    },
    paragraph: {
        padding: 10,


    },
    section: {
        padding: 10,
        backgroundColor: 'white',
    },
    title: {
        marginTop: 10,
        marginBottom: 10,
        color: 'black',
        fontWeight: 'bold',
        fontSize: 18,
        marginLeft: 10,

    },
    switchWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
    },
    switch: {
        flexDirection: 'row',
    },
    center: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        paddingTop: 24,
    },
    centerText: {
        marginRight: 5,
    },
    // Add missing styles
    footer: {
        padding: 10,
        backgroundColor: 'white',
    },
    button: {
    },

});
export default TestRecord;
