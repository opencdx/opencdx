import React, { useState } from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import { Button, Text } from 'react-native-paper';
import Checkbox from "react-native-bouncy-checkbox";

const CheckVirtualConsult = ({ navigation }) => {
    const [isChecked, setChecked] = useState(false);
    const [isChecked1, setChecked1] = useState(false);
    const [isChecked2, setChecked2] = useState(false);
    const [isChecked3, setChecked3] = useState(false);
    const [isChecked4, setChecked4] = useState(false);
    const [isChecked5, setChecked5] = useState(false);
    const [isChecked6, setChecked6] = useState(false);
    const [isChecked7, setChecked7] = useState(false);
    const [isChecked8, setChecked8] = useState(false);

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Text style={styles.input} variant='titleMedium'>
                    Which of the following Covid-19 symptoms do you currently have
                </Text>
                <View style={styles.containerCheck}>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked} onValueChange={setChecked} />
                        <Text style={styles.paragraph}>Sore Throat</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked1} onValueChange={setChecked1} />
                        <Text style={styles.paragraph}>Cough</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked2} onValueChange={setChecked2} />
                        <Text style={styles.paragraph}>Fever</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked3} onValueChange={setChecked3} />
                        <Text style={styles.paragraph}>Shortness of Breath</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked4} onValueChange={setChecked4} />
                        <Text style={styles.paragraph}>Loss of Taste or Smell</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked5} onValueChange={setChecked5} />
                        <Text style={styles.paragraph}>Muscle Aches</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked6} onValueChange={setChecked6} />
                        <Text style={styles.paragraph}>Headache</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked7} onValueChange={setChecked7} />
                        <Text style={styles.paragraph}>Chills</Text>
                    </View>
                    <View style={styles.section}>
                        <Checkbox style={styles.checkbox} value={isChecked8} onValueChange={setChecked8} />
                        <Text style={styles.paragraph}>Fatigue</Text>
                    </View>
                </View>
            </View>
            <View style={styles.footer}>
                <Button title="Sign In" mode="contained-tonal"
                 style={styles.button} onPress={() => navigation.navigate('PriorVirtualConsult')}>
                    Continue
                </Button>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: '#000',
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            },
        }),
    },
    body: {
        ...Platform.select({
            web: {
                padding: 24,
            },
            default: {
                flex: 1,
                justifyContent: 'center',
                padding: 24,
            },
        }),
    },
    input: {
        textAlign: 'center',        
    },
    containerCheck: {
        flex: 1,
        marginHorizontal: 16,
        marginVertical: 32,
    },
    section: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    paragraph: {
        fontSize: 15,
    },
    checkbox: {
        margin: 8,
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 4,
        paddingTop: 24,
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
    
});

export default CheckVirtualConsult;