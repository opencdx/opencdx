
import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, SafeAreaView , Platform} from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import { MaterialCommunityIcons } from '@expo/vector-icons';
const ListScreen = ({ navigation }) => {
    const currentDate = new Date().toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric',
    });

    const handleVirtual = () => {
        navigation.navigate('StartVirtualConsult');
    };

  

    const renderLinearGradientButton = (title, description, onPress) => (
        <TouchableOpacity onPress={onPress}>
            <LinearGradient
                colors={['lightblue', '#24C6DC']}
                start={[0.0, 0.5]}
                end={[1.0, 0.5]}
                locations={[0.0, 1.0]}
                style={styles.linearGradientButton}
            >
                <Text style={styles.buttonTitle}>{title}</Text>
                <Text style={styles.buttonDescription}>{description}</Text>
            </LinearGradient>
        </TouchableOpacity>
    );

    return (
        <View style={styles.container}>
            <SafeAreaView>
                <View style={styles.body}>
                    <Text style={styles.dateText}>{currentDate}</Text>
                    <Text style={styles.nameText}>Hello, John Smith</Text>
                </View>

                {renderLinearGradientButton(
                    'Not feeling well',
                    'Share details about your symptoms to see if you qualify for FDA approved antiviral treatment.',
                    handleVirtual
                )}

                {renderLinearGradientButton(
                    'Take a Test',
                    "If you don't already have a test kit, you can order one and have it shipped to you.",
                    () => navigation.navigate('GetTested')
                )}

                <LinearGradient
                    colors={['#90EE90', '#90EE90']}
                    start={[0.0, 0.5]}
                    end={[1.0, 0.5]}
                    locations={[0.0, 1.0]}
                    style={styles.passportContainer}
                >
                    <View style={styles.leftContainer}>
                        <Text style={styles.passportTitle}>My Passport</Text>
                        <Text style={styles.passportText}>Name: John Smith</Text>
                        <Text style={styles.passportText}>Last Assessment: June 15, 2022</Text>
                        <Text style={styles.passportText}>Date: July 20, 2022</Text>
                    </View>
                    <View style={styles.rightContainer}>
                        <MaterialCommunityIcons name="qrcode-scan" size={80} color="black" />
                        <Text>Sample QR code</Text>
                    </View>
                </LinearGradient>

                {renderLinearGradientButton(
                    'Text History',
                    'See your test history.',
                    () => navigation.navigate('TestHistory')
                )}

                {renderLinearGradientButton(
                    'Vaccine History',
                    'See your vaccine history.',
                    () => navigation.navigate('VaccineHistory')
                )}

                {renderLinearGradientButton(
                    'Take a specific questionnaire',
                    '',
                    () => navigation.navigate('User')
                )}
            </SafeAreaView>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 8,
        shadowColor: '#000',
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                margin: 20,
                shadowColor: '#000',
            },
        })),
    },
    body: {
        paddingBottom: 20,
        
        },
    linearGradientButton: {
        cursor: 'pointer',
        borderRadius: 10,
        borderColor: 'lightblue',
        borderWidth: 1,
        padding: 10,
        marginBottom: 20,
        shadowColor: 'green',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
    },
    buttonTitle: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    buttonDescription: {
        marginTop: 5,
    },
    passportContainer: {
        cursor: 'pointer',
        borderRadius: 10,
        borderColor: 'lightblue',
        borderWidth: 1,
        padding: 10,
        marginBottom: 20,
        shadowColor: 'green',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.25,
        shadowRadius: 4,
        elevation: 5,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderRadius: 10,
    },
    leftContainer: {
        flex: 1,
        marginRight: 10,
    },
    rightContainer: {
        flex: 1,
        alignItems: 'center',
    },
    passportTitle: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    passportText: {
        marginBottom: 5,
    },
    dateText: {
        fontSize: 18,
        color: 'gray',
    },
    nameText: {
        fontSize: 22,
        fontWeight: 'bold',
    },
});

export default ListScreen;
