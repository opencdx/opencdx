import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Platform } from 'react-native';
import axios from '../utils/axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, Heading, ButtonText,ButtonIcon, ArrowRightIcon } from '@gluestack-ui/themed';

const ListScreen = ({ navigation }) => {
    const [buttonTitles, setButtonTitles] = useState([]);

    useEffect(() => {
        const fetchQuestionnaireList = async () => {
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');

                const response = await axios.post(
                    '/questionnaire/questionnaire/list',
                    {
                        pagination: {
                            pageSize: 30,
                            sortAscending: true,
                        },
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${jwtToken}`,
                        },
                    }
                );
                const { questionnaires } = response.data;
                setButtonTitles(questionnaires);
                
            } catch (error) {
                console.error(error);
            }
        };

        fetchQuestionnaireList();
    }, []);

    return (
        <View style={styles.container}>
            <View style={styles.body}>
                <text>Mar 2, 2024</text>
                <text>Hello, John Smith</text>
            </View>
            <View
                style={{ cursor: 'pointer', borderRadius: 10, borderColor: 'black', borderWidth: '1px', padding: 10, backgroundColor: 'lightgray', marginBottom: 20 }}>
                <Heading size="md">Not feeling well</Heading>
                <text>
                    Share details about your symptoms to see if you qualify for FDA approved antiviral treatment.
                </text>
            </View>
            <View
                style={{ cursor: 'pointer', borderRadius: 10, borderColor: 'black', borderWidth: '1px', padding: 10, backgroundColor: 'lightgray', marginBottom: 20 }}>
                <Heading size="md">Take a Test</Heading>
                <text>
                    if you don't already have a test kit, you can order one and have it shipped to you.
                </text>
            </View>
            <View
                style={{ cursor: 'pointer', borderRadius: 10, borderColor: 'black', borderWidth: '1px', padding: 10, backgroundColor: 'lightgray', marginBottom: 20 }}>
                <Heading size="md">Take a specific questionnaire</Heading>
                {buttonTitles.map((questionnaire, index) => (
                    <Button
                        style={styles.input}
                        key={index}
                        title={questionnaire.title}
                        onPress={() => navigation.navigate('Home', { questionnaire })}
                        size="md"
                        variant="contained"
                        action="primary"
                        isDisabled={false}
                    >
                        <ButtonText>{questionnaire.title}</ButtonText>
                        <ButtonIcon as={ArrowRightIcon} color="primary" size="md" />
                    </Button>
                ))}
            </View>
            <View
                style={{ cursor: 'pointer', borderRadius: 10, borderColor: 'black', borderWidth: '1px', padding: 10, backgroundColor: 'lightgray', marginBottom: 20 }}>
                <Heading size="md">Current Status</Heading>
                <text>
                    bar code
                </text>
            </View>
            <View
                style={{ cursor: 'pointer', borderRadius: 10, borderColor: 'black', borderWidth: '1px', padding: 10, backgroundColor: 'lightgray', marginBottom: 20 }}>
                <Heading size="md">Text History</Heading>
                <text>
                    See your test history.
                </text>
            </View>            
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
    input: {
        margin: 5,
        textAlign: 'right',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderRadius: 68,

        
    },
});

export default ListScreen;
