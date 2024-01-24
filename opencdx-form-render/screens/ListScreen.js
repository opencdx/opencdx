import React, { useEffect, useState } from 'react';
import { View, StyleSheet, Platform } from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, Heading, ButtonText } from '@gluestack-ui/themed';

const ListScreen = ({ navigation }) => {
    const [buttonTitles, setButtonTitles] = useState([]);

    useEffect(() => {
        const fetchQuestionnaireList = async () => {
            try {
                const jwtToken = await AsyncStorage.getItem('jwtToken');

                const response = await axios.post(
                    'https://localhost:8080/questionnaire/questionnaire/list',
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
            <Heading>Select a questionnaire:</Heading>
            {buttonTitles.map((questionnaire, index) => (
                <Button
                    style={styles.input}
                    key={index}
                    title={questionnaire.title}
                    onPress={() => navigation.navigate('Home', { questionnaire })}
                >
                    <ButtonText>{questionnaire.title}</ButtonText>
                </Button>
            ))}
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
            default: {},
        })),
    },
    input: {
        margin: 5,
    },
});

export default ListScreen;
