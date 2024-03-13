import React, { useState } from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, Text } from 'react-native-paper';
import Checkbox from "react-native-bouncy-checkbox";

const PriorVirtualConsult = ({ navigation }) => {
    const [isChecked, setChecked] = useState(false);
    const [isChecked1, setChecked1] = useState(false);

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Text style={styles.input} variant='titleMedium'>   
                    Prior to this episode, have you ever tested positive for or been treated for COVID-19?
                </Text>
                <View style={styles.containerCheck}>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked} onValueChange={setChecked} />
                        <Text style={styles.paragraph}>Yes</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked1} onValueChange={setChecked1} />
                        <Text style={styles.paragraph}>No</Text>
                    </View>
                </View>
            </View>
            <View style={styles.footer}>
                <Button title="Sign In" mode="contained-tonal"
                    style={styles.button} onPress={() => navigation.navigate('ConfirmVirtualConsult')}>
                    Continue
                </Button>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        justifyContent: 'space-between',
    },
    input: {
        textAlign: 'center',
    },
    button: {
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,
        ...Platform.select({
            web: {
                width: '90%',
            },
            default: {
                width: '90%',
            },
        }),
    },
    body: {
        flex: 1,
        justifyContent: 'center',
        padding: 24,
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
    containerCheck: {
        marginHorizontal: 16,
        marginVertical: 32,
    },
    section: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 16,
    },
    checkbox: {
        marginRight: 8,
    },
    paragraph: {
        marginLeft: 8,
    },
});
    

export default PriorVirtualConsult;
